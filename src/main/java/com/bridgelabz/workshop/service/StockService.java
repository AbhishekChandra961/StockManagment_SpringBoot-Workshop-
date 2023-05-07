package com.bridgelabz.workshop.service;

import com.bridgelabz.workshop.dto.ResponseDto;
import com.bridgelabz.workshop.dto.StockDto;
import com.bridgelabz.workshop.model.Stock;

public interface StockService {

    ResponseDto addStock(StockDto stockDto);
    Stock updateStock(int id,StockDto stockDto);
    Stock getById(int id);
    ResponseDto deleteById(int id);
    String sellStocks(int id,int sellQuantity);
}
