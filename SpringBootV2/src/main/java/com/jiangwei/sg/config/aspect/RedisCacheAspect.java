package com.jiangwei.sg.config.aspect;//package com.jiangwei.config.aspect;
//
import com.jiangwei.sg.util.redis.RedisUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * Created by mark on 16/11/29.
 */
@Aspect
@EnableAspectJAutoProxy
@Component
public class RedisCacheAspect {

    private static final Logger logger = Logger.getLogger(RedisCacheAspect.class);
    /**
     * 分隔符 生成key 格式为 类全类名|方法名|参数所属类全类名
     **/
    private static final String DELIMITER = "|";


    @Autowired
    RedisUtil redisUtil;

    /**
     * Service层切点 使用到了我们定义的 AddCache 作为切点表达式。
     * 而且我们可以看出此表达式基于 annotation。
     * 并且用于内建属性为查询的方法之上
     */
    @Pointcut("@annotation(com.jiangwei.sg.config.aspect.AddCache)")
    public void queryCache() {
    }

    /**
     * Service层切点 使用到了我们定义的 RedisEvict 作为切点表达式。
     * 而且我们可以看出此表达式是基于 annotation 的。
     * 并且用于内建属性为非查询的方法之上，用于更新表
     */
    @Pointcut("@annotation(com.jiangwei.sg.config.aspect.ClearRedis)")
    public void ClearCache() {
    }


    @Around("queryCache()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;

        Method method = getMethod(pjp);
        AddCache cacheable = method.getAnnotation(AddCache.class);
        //key的value
        String fieldKey = parseKey(cacheable.fieldKey(), method, pjp.getArgs());

        //获取方法的返回类型,让缓存可以返回正确的类型
        Class returnType = ((MethodSignature) pjp.getSignature()).getReturnType();
        System.out.println(cacheable.key()+"----"+fieldKey);
        //使用redis 的hash进行存取，易于管理
        result = redisUtil.hget(cacheable.key(), fieldKey);
        if (result != null) {
            System.out.println("从redis缓存获取");
            return result;
        } else {
            try {
                result = pjp.proceed();
                System.out.println("从db获取");
                Assert.notNull(fieldKey);
                redisUtil.hset(cacheable.key(), fieldKey, result,cacheable.expireTime());
                return result;
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    /*** 定义清除缓存逻辑*/
    @Around(value = "ClearCache()")
    public Object evict(ProceedingJoinPoint pjp) {
        Object result = null;

        Method method = getMethod(pjp);
        ClearRedis cacheable = method.getAnnotation(ClearRedis.class);
        String fieldKey = parseKey(cacheable.fieldKey(), method, pjp.getArgs());

        //获取方法的返回类型,让缓存可以返回正确的类型
        Class returnType = ((MethodSignature) pjp.getSignature()).getReturnType();
        //使用redis 的hash进行存取，易于管理
        result = redisUtil.hget(cacheable.key(), fieldKey);
        if (result != null) {
            redisUtil.hdel(cacheable.key(), fieldKey);
            //执行真正的删除，先更新数据库在更新缓存是最好的方案
            try {
                result = pjp.proceed();
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取被拦截方法对象
     * <p>
     * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象
     * 而缓存的注解在实现类的方法上
     * 所以应该使用反射获取当前对象的方法对象
     */
    public Method getMethod(ProceedingJoinPoint pjp) {
        //获取参数的类型
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[pjp.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = null;
        try {

            Object target = pjp.getTarget();
            Class<?> aClass = target.getClass();
            Signature signature = pjp.getSignature();
            method = aClass.getMethod(signature.getName(), argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return method;

    }

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     *
     * @param
     * @return
     */
    private String parseKey(String key, Method method, Object[] args) {

        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
            sb.append(paraNameArr[i]).append(":").append(args[i]).append(DELIMITER);
        }
        String substring = sb.substring(0, sb.length() - 1);
        System.out.println(substring);
        return String.valueOf(substring.hashCode());
    }


}