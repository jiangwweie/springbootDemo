package com.jiangwei.controller;

import com.jiangwei.dao.redis.RedisDao;
import com.jiangwei.entity.test.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * describe:
 *
 * @author lalio
 * @email jiangwei.wh@outlook.com
 * @date 2018/10/16
 */

@RestController
public class CityController {

    @Autowired
    private RedisDao redisDao;


    //http://localhost:8888/saveCity?cityName=北京&cityIntroduce=中国首都&cityId=1
    @GetMapping(value = "saveCity")
    public String saveCity(int cityId,String cityName,String cityIntroduce){
        City city = new City(cityId,cityName,cityIntroduce);
        redisDao.set(cityId+"",city);
        return "success";
    }


    //http://localhost:8888/getCityById?cityId=1
    //@GetMapping(value = "getCityById")
    @GetMapping(value = "getCityById/{cityId}")
//    http://localhost:8888/getCityById/{cityId}
    public City getCity(@PathVariable(value = "cityId") int cityId){
        System.out.println(redisDao.get(cityId+""));
        City city = (City) redisDao.get(cityId+"");
        return city;
    }
}
