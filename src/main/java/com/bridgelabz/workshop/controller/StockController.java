package com.bridgelabz.workshop.controller;

import com.bridgelabz.workshop.dto.ResponseDto;
import com.bridgelabz.workshop.dto.StockDto;
import com.bridgelabz.workshop.model.Stock;
import com.bridgelabz.workshop.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @PostMapping("/add")
    public ResponseDto addStock(@RequestBody StockDto stockDto){
        return stockService.addStock(stockDto);
    }
    @PutMapping("/update/{id}")
    public Stock updateStock(@PathVariable int id,@RequestBody StockDto stockDto){
        return stockService.updateStock(id,stockDto);
    }

    @GetMapping("/get/{id}")
    public Stock getById(@PathVariable int id){
        return stockService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto deleteById(@PathVariable int id){
        return stockService.deleteById(id);
    }

    @PutMapping("/sell/{id}")
    public String sellStock(@PathVariable int id, @RequestParam int quantity){
        return stockService.sellStocks(id,quantity);
    }
}
