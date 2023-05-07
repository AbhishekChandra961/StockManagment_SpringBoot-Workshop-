package com.bridgelabz.workshop.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

//id: A unique identifier for the product.
//        name: The name of the product.
//        description: A description of the product.
//        price: The price of the product.
//        quantity: The quantity of the product that is currently in stock.
@Data
public class StockDto {
    private int id;
    private String name;
    private String description;
    private int price;
    private int quantity;

}
