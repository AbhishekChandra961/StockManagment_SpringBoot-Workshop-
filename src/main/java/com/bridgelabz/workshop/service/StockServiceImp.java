package com.bridgelabz.workshop.service;

import ch.qos.logback.core.CoreConstants;
import com.bridgelabz.workshop.dto.ResponseDto;
import com.bridgelabz.workshop.dto.StockDto;
import com.bridgelabz.workshop.exception.CustomException;
import com.bridgelabz.workshop.model.Stock;
import com.bridgelabz.workshop.repo.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockServiceImp implements StockService{

    @Autowired
    private StockRepo stockRepo;

    @Override
    public ResponseDto addStock(StockDto stockDto) {
        Stock stock=new Stock(stockDto);
        stockRepo.save(stock);
        return new ResponseDto("Stock Added",stock);
    }

    @Override
    public Stock updateStock(int id, StockDto stockDto) {
        Stock stock=this.getById(id);
        stock.updateData(stockDto);
        return stockRepo.save(stock);
    }

    @Override
    public Stock getById(int id) {
        return stockRepo.findById(id).orElseThrow(()->new CustomException("Data Not Found :" +id));
    }

    @Override
    public ResponseDto deleteById(int id) {
        Stock stock=this.getById(id);
        stockRepo.deleteById(id);
        return new ResponseDto("Stock deleted",id);
    }

    @Override
    public String sellStocks(int id,int sellQuantity) {
        Optional<Stock> stockData=stockRepo.findById(id);
        if(stockData.isEmpty()){
            if(sellQuantity<=stockData.get().getQuantity()){
                int prevQuantity=stockData.get().getQuantity();
                int currQuantity=prevQuantity-sellQuantity;
                System.out.println(currQuantity);
                stockData.get().setQuantity(currQuantity);
                stockRepo.save(stockData.get());
                int totalPrice = sellQuantity*(stockData.get().getPrice());
                return "Stock sold ,total price is : "+totalPrice;
            }else{
                return "Not have enough Quantity";
            }
        }else{
            return "Stock not present";
        }
    }
}
