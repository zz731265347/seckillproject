package com.zz.service;

import com.zz.dao.StockDao;
import com.zz.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class OrdServiceImpl  implements  OrderService{
    @Resource
    private StockDao stockDao;
    @Override
    public int killStock(Integer id) {
        //1. 根据ID 查询商品库存
       Stock stock = stockDao.getStock(id);
       if(stock.getCount() == stock.getSale() ){
           return 0;
       }else{
           // 2. 扣除库存
           stock.setSale(stock.getSale()+1);
           stockDao.updateStockSale(stock);
           // 3. 创建订单
           return 123;
       }

    }
}
