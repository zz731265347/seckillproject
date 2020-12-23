package com.zz.controller;

import com.zz.service.OrderService;
import com.zz.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stock")
@Slf4j
public class StockController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("kill")
    public String kill( Integer id ){
        log.info("抢购商品ID = {}",id);
        //根据商品ID 进行秒杀业务处理
       log.info(DateUtils.yyyymmddHHmmss());
       int ordid =  orderService.killStock( id);
        return "抢购成功 订单ID：" + String.valueOf(ordid) ;

    }
}
