package com.example.demo.service;

import com.example.demo.entity.OrderEntity;
import com.example.demo.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public List<OrderEntity> getOrders(String userToken) {
        if (userToken == null || userToken.isEmpty()) {
            return null;
        }
        return orderMapper.selectByUserToken(userToken);
    }
}
