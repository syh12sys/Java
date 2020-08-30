package com.example.demo.redis;

import com.example.demo.entity.PhoneAddressEntity;
import com.example.demo.service.PhoneAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.Jedis;

import java.util.List;

@Component
public class PhoneAdressLoader {
//    @Autowired
//    private PhoneAddressService phoneAddressService;
//
//    public void loadData() {
//        Jedis client = new Jedis("localhost");
//        List<PhoneAddressEntity> phoneAddressList = phoneAddressService.getAllPhoneAddress();
//        for (PhoneAddressEntity phoneAddressEntity : phoneAddressList) {
//            client.hset(phoneAddressEntity.getPhone(), "province", phoneAddressEntity.getProvince());
//            client.hset(phoneAddressEntity.getPhone(), "city", phoneAddressEntity.getCity());
//        }
//    }
}
