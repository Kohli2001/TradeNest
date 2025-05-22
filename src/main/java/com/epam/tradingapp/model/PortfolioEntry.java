package com.epam.tradingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class PortfolioEntry {
    @Id @GeneratedValue
    private Long id;
    private String stockSymbol;
    private int quantity;
    private double averagePrice;
    @ManyToOne
    private User user;  // owning user
}
