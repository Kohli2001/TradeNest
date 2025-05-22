package com.epam.tradingapp.service;

import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StockPriceService {
    private Map<String, Double> priceMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // Initialize some stock symbols and prices
        priceMap.put("INFY", 1500.0);
        priceMap.put("TCS", 3500.0);
        priceMap.put("RELIANCE", 2400.0);
    }

    @Scheduled(fixedRate = 5000)  // update every 5 seconds
    public void updatePrices() {
        // Randomly fluctuate each price
        priceMap.forEach((symbol, oldPrice) -> {
            double change = (Math.random() - 0.5) * 10; // random change [-5, +5]
            priceMap.put(symbol, Math.max(1.0, oldPrice + change));
        });
        // After updating prices, broadcast via WebSocket (see next section)
        // socketMessagingTemplate.convertAndSend("/topic/prices", priceMap);
    }

    public double getPrice(String symbol) {
        return priceMap.getOrDefault(symbol, 0.0);
    }

    public Map<String, Double> getAllPrices() {
        return new HashMap<>(priceMap);
    }
}

