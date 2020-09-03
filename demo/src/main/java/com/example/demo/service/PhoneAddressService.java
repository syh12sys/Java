package com.example.demo.service;

import com.example.demo.entity.PhoneAddressEntity;
import com.example.demo.mapper.PhoneAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

@Service
public class PhoneAddressService {
    @Autowired
    private PhoneAddressMapper phoneAddressMapper;

    public List<PhoneAddressEntity> getAllPhoneAddress() {
        return phoneAddressMapper.selectAll();
    }

    public PhoneAddressEntity getPhoneAddress(String phone) {
        Jedis jedis = new Jedis("localhost");
        Map<String, String> address = jedis.hgetAll(phone);
        if (address == null) {
            return null;
        }
        PhoneAddressEntity phoneAddressEntity = new PhoneAddressEntity();
        phoneAddressEntity.setProvince(address.get("province"));
        phoneAddressEntity.setCity(address.get("city"));
        return phoneAddressEntity;
    }
}
