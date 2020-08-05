package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.OrderEntity;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/getOrders", method = RequestMethod.GET)
    String getOrders(@RequestParam(value = "userToken") String userToken) {
        List<OrderEntity> orders = orderService.getOrders(userToken);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (OrderEntity orderEntity : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setCommodityId(orderEntity.getCommodityId());
            orderDTO.setCreateAt(orderEntity.getCreateAt());
            orderDTO.setOrderId(orderEntity.getOrderId());
            orderDTOS.add(orderDTO);
        }
        RestRetValue tmp = new RestRetValue("0", "", orderDTOS.isEmpty() ? "" : orderDTOS);
        return JSON.toJSONString(tmp);
    }
}
