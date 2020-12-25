package com.zz.service;

import com.zz.dao.OrderDao;
import com.zz.dao.StockDao;
import com.zz.entity.Order;
import com.zz.entity.Stock;
import com.zz.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class OrdServiceImpl  implements  OrderService{
    @Resource
    private StockDao stockDao;
    @Resource
    private OrderDao orderDao;
    @Override
    /*
        这个地方通过悲观锁的问题 进行了锁处理
        但是因为transactional的关系事务统一提交 不能在此方案上加synchronized
        因为锁释放后事务才会提交，会导致 数据库查询延后，也会出现超买
        所有synchronized 应该放在方法调用之前
        */
    public   int  killStock(Integer id) {
        //1. 根据ID 查询商品库存
       Stock stock = stockDao.getStock(id);
       if(stock == null || stock.getCount() == stock.getSale()  ){
           return 0;
       }else{
           // 2. 扣除库存
           stock.setSale(stock.getSale()+1);
           stockDao.updateStockSale(stock);
           // 3. 创建订单
           Order   order = new Order();
           order.setSid(id ).setName("Iphone 7 抢购订单").
                   setCreateTime(DateUtils.yyyymmddHHmmss());
           orderDao.createOrder(order);
           return order.getId();
       }

    }

    //乐观锁的思想代码实现
    // 1、查询商品  得到version 版本号
    //     简单判断商品ID
    // 2、直接数据库修改库存 、版本号  根据查询的版本号+shangp
    // 3、 2步骤的更新结果 如果为0（更新数据为0） 标识抢购失败

    // 实际是依赖于数据库事务实现 乐观锁。

    // 问题：
    // 1、 所有压力到了接口、数据库 ，容易把数据库搞崩
    // 2、 一起抢的时候明明还有库存的时候只是并发了但是还是会抢不了

    @Override
    public int pessimistickillStock(Integer id) {
        //1. 根据ID 查询商品库存
        Stock stock = stockDao.getStock(id);
        if(stock == null || stock.getCount() == stock.getSale()  ){
            return 0;
        }else{
            // 2. 扣除库存 （方法改造,数据库加1 操作）
           int updateNum = stockDao.updateStockSaleVesion(stock);
           if(updateNum == 0){
               return 0 ;
           }

            // 3. 创建订单
            Order   order = new Order();
            order.setSid(id ).setName("Iphone 7 抢购订单").
                    setCreateTime(DateUtils.yyyymmddHHmmss());
            orderDao.createOrder(order);
            return order.getId();
        }
    }

}
