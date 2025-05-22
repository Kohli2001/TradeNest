package com.epam.tradingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeOrder {
    @Id
    @GeneratedValue
    private Long id;
    private String stockSymbol;
    private int quantity;
    private double price;
    private String orderType;    // "BUY" or "SELL"
    private LocalDateTime timestamp;
    @ManyToOne
    private User user;  // owning user
}
