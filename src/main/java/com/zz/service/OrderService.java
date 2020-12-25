package com.zz.service;


public interface OrderService {

    int killStock(Integer id);

    int pessimistickillStock(Integer id);
}
