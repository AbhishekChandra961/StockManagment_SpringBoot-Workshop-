package com.bridgelabz.workshop.repo;

import com.bridgelabz.workshop.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepo extends JpaRepository<Stock,Integer> {
//    @Query(value = "select stock where email=:emai")

}
