package com.zz.dao;


import com.zz.entity.Stock;
import org.apache.ibatis.annotations.Param;

public interface StockDao {
      Stock getStock(@Param("id") Integer id);
}
