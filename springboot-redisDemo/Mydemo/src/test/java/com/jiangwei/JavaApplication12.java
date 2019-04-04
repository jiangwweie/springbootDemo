/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jiangwei;

import com.alibaba.fastjson.JSONObject;
import com.jiangwei.dao.redis.RedisUtil;
import com.jiangwei.util.MathUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jiangwei
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaApplication12 {

    @Resource
    RedisUtil redisUtil;

    @Test
    public  void main() {
        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/storedemo";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "691216";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed()) {
                System.out.println("Succeeded connecting to the Database!");
            }
            //读取每一条登陆退出操作  时间段   uid 机器码 拼接成xml    
            getJSONParam(con);

            con.close();
        } catch (ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            System.out.println("数据库数据成功获取！！");
        }
    }

    public  ResultSet getSqlResult(Connection con, String sql) throws SQLException {
        //2.创建statement类对象，用来执行SQL语句！！
        Statement statement = con.createStatement();
        //要执行的SQL语句
        //3.ResultSet类，用来存放获取的结果集！！
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }

    public  void getJSONParam(Connection con) throws FileNotFoundException, IOException, SQLException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\jiangwei\\Desktop\\login-out.csv"));
        String line = "";
        int i = 1;
        while ((line = br.readLine()) != null) {
            if (i != 1) {
                String[] s = line.split(",");
                int uid = Integer.parseInt(s[0]);
                String machine_code = s[1].trim();
                long logout_time = Long.parseLong(s[2]);
                long login_time = Long.parseLong(s[3]);
                JSONObject dockingJSON = getDockingJSON(login_time, logout_time, uid, machine_code, con);
                dockingJSON.put("machineCode", machine_code);
                redisUtil.lSet("index_"+i,dockingJSON);
            }

            i++;
        }
    }

    public  JSONObject getDockingJSON(long startTime, long endTime, int uid, String machineCode, Connection con) throws SQLException {
        startTime = startTime / 1000;
        endTime = endTime / 1000;
        //降序查出检测数据（时间段，uid，机器码）
        StringBuffer sql = new StringBuffer(" select * from storedemo.t_measure_bloodfat t where t.uid =  ");
        sql.append(uid).append(" and t.machine_code = '");
        sql.append(machineCode).append("' and t.addTime > ");
        sql.append(startTime).append(" and t.addTime < ");
        sql.append(endTime).append(" order by t.addTime desc limit 0,1 ");
        //t_measure
        String bloodFat = sql.toString();//血脂
        String bloodUricacid = bloodFat.replace("bloodfat", "blooduricacid");//血尿酸
        String height = bloodFat.replace("bloodfat", "height");//身高
        String bodyfat = bloodFat.replace("bloodfat", "bodyfat");//体脂
        String hemoglobin = bloodFat.replace("bloodfat", "hemoglobin");//血红蛋白
        String pulse = bloodFat.replace("bloodfat", "pulse");//脉率
        String urinalysis = bloodFat.replace("bloodfat", "urinalysis");//尿常规
        String weight = bloodFat.replace("bloodfat", "weight");//体重
        //t_physiology
        String glucose = bloodFat.replace("t_measure_bloodfat", "t_physiology_glucose").replace("addTime", "clsj");//血糖
        String oxygen = glucose.replace("glucose", "oxygen");//血氧
        String pressure = glucose.replace("glucose", "pressure");//血压
        String rate = glucose.replace("glucose", "rate");//心率
        String temperature = glucose.replace("glucose", "temperature");//体温

//        List list = new ArrayList();
        JSONObject dockingJSON = new JSONObject();

        ResultSet rs1 = getSqlResult(con, bloodFat);
        while (rs1.next()) {
            dockingJSON.put("cholesterol", rs1.getString("cholesterol"));
            dockingJSON.put("tg", rs1.getString("tg"));
            dockingJSON.put("hdlCholesterol", rs1.getString("hdlCholesterol"));
            dockingJSON.put("ldlCholesterol", rs1.getString("ldlCholesterol"));
        }
        rs1.close();

        ResultSet rs2 = getSqlResult(con, bloodUricacid);
        while (rs2.next()) {
            dockingJSON.put("uricAcid", rs2.getString("uricAcid"));
        }
        rs2.close();

        ResultSet rs3 = getSqlResult(con, height);
        while (rs3.next()) {
            dockingJSON.put("height", rs3.getString("height"));
        }
        rs3.close();

        ResultSet rs4 = getSqlResult(con, bodyfat);
        while (rs4.next()) {
            dockingJSON.put("weight", rs4.getString("weight"));
            dockingJSON.put("skeletonMass", rs4.getString("skeletonMass"));
            dockingJSON.put("fatMass", rs4.getString("fatMass"));
            dockingJSON.put("muscleMass", rs4.getString("muscleMass"));
        }
        rs4.close();

        ResultSet rs5 = getSqlResult(con, hemoglobin);
        while (rs5.next()) {
            dockingJSON.put("hb", rs5.getString("hb"));
            dockingJSON.put("hct", rs5.getString("hct"));
        }
        rs5.close();

        ResultSet rs6 = getSqlResult(con, pulse);
        while (rs6.next()) {
            dockingJSON.put("pulse", rs6.getString("pulse"));
        }
        rs6.close();

        ResultSet rs7 = getSqlResult(con, urinalysis);
        while (rs7.next()) {
            dockingJSON.put("sg", rs7.getString("pulse"));
            dockingJSON.put("leu", rs7.getString("pulse"));
            dockingJSON.put("ket", rs7.getString("pulse"));
            dockingJSON.put("nit", rs7.getString("pulse"));
            dockingJSON.put("ubg", rs7.getString("pulse"));
            dockingJSON.put("bil", rs7.getString("pulse"));
            dockingJSON.put("pro", rs7.getString("pulse"));
            dockingJSON.put("glu", rs7.getString("pulse"));
            dockingJSON.put("bld", rs7.getString("pulse"));
            dockingJSON.put("ph", rs7.getString("pulse"));
            dockingJSON.put("vc", rs7.getString("pulse"));
        }
        rs7.close();

        ResultSet rs8 = getSqlResult(con, weight);
        while (rs8.next()) {
            dockingJSON.put("weight", rs8.getString("weight"));
        }
        rs8.close();

        ResultSet rs9 = getSqlResult(con, glucose);
        while (rs9.next()) {
            dockingJSON.put("xuetang", rs9.getString("xuetang"));
        }
        rs9.close();

        ResultSet rs10 = getSqlResult(con, oxygen);
        while (rs10.next()) {
            dockingJSON.put("oxygen", rs10.getString("oxygen"));
        }
        rs10.close();

        ResultSet rs11 = getSqlResult(con, pressure);
        while (rs11.next()) {
            dockingJSON.put("sbp", rs11.getString("up"));
            dockingJSON.put("dbp", rs11.getString("down"));
        }
        rs11.close();

        ResultSet rs12 = getSqlResult(con, rate);
        while (rs12.next()) {
            dockingJSON.put("rate", rs12.getString("rate"));
        }
        rs12.close();

        ResultSet rs13 = getSqlResult(con, temperature);
        while (rs13.next()) {
            dockingJSON.put("temperature", rs13.getString("temperature"));
        }
        rs13.close();

        String sql15 = " select * from storedemo.t_member_basicinfo where uid = " + uid + " ";
        ResultSet rs15 = getSqlResult(con, sql15);
        while (rs15.next()) {
            dockingJSON.put("name", rs15.getString("name"));
            dockingJSON.put("birthday", rs15.getString("birthday"));
            dockingJSON.put("sex", rs15.getString("sex"));
            dockingJSON.put("address", rs15.getString("address"));
        }
        rs15.close();

        String sql14 = " select * from storedemo.t_member_base where uid = " + uid + " ";
        ResultSet rs14 = getSqlResult(con, sql14);
        while (rs14.next()) {
            dockingJSON.put("idcard", rs14.getString("idcard"));
            String idcard = rs14.getString("idcard");
            if (idcard != null && !idcard.equals("")) {
                String birth = MathUtils.getBirthByIdCard(idcard);
                String sex = MathUtils.getSexByIdCard(idcard);
                dockingJSON.put("birthday", birth);
                dockingJSON.put("sex", sex);
            }
        }
        rs14.close();

        return dockingJSON;
    }
}
