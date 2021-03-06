package com.zz.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.zz.service.OrderService;
import com.zz.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("stock")
@Slf4j
public class StockController {

    @Autowired
    private OrderService orderService;

    private  RateLimiter  rateLimiter= RateLimiter.create(10);

    @RequestMapping("kill")
    //悲观锁的实现解决超买
    public String kill( Integer id ){
        log.info("悲观锁抢购商品ID = {}",id);
        //根据商品ID 进行秒杀业务处理
       log.info(DateUtils.yyyymmddHHmmss());
        int ordid = 0;
        synchronized (this){
            ordid = orderService.killStock( id);
       }
       if(ordid == 0)
           return "抢购失败";
        return "抢购成功 订单ID：" + String.valueOf(ordid) ;

    }

    //乐观锁的方案解决超买的实现
    //原理类似CAS原理
    @RequestMapping("pessimistickill")
    //悲观锁的实现解决超买
    public String pessimistickill( Integer id ){
        log.info(" 乐观锁抢购商品ID = {}",id);
        //根据商品ID 进行秒杀业务处理
        log.info(DateUtils.yyyymmddHHmmss());
        Boolean flag = rateLimiter.tryAcquire(3, TimeUnit.MICROSECONDS);
        if(!flag){
            return "当前活动太火爆了，请稍后重试~";
        }
        int ordid   = orderService.pessimistickillStock( id);
        if(ordid == 0)
            return "抢购失败";

        return "抢购成功 订单ID：" + String.valueOf(ordid) ;

    }
}
