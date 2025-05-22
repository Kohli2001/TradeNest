package com.epam.tradingapp.service;

import com.epam.tradingapp.model.PortfolioEntry;
import com.epam.tradingapp.model.TradeOrder;
import com.epam.tradingapp.model.User;
import com.epam.tradingapp.repository.PortfolioEntryRepository;
import com.epam.tradingapp.repository.TradeOrderRepository;
import com.epam.tradingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final TradeOrderRepository orderRepo;
    private final PortfolioEntryRepository portfolioRepo;
    private final UserRepository userRepo;

    public TradeOrder placeOrder(String username, String symbol, int qty, String type, double price) {
        User user = userRepo.findByUsername(username);
        TradeOrder order = new TradeOrder();
        order.setStockSymbol(symbol);
        order.setQuantity(qty);
        order.setPrice(price);
        order.setOrderType(type);
        order.setTimestamp(LocalDateTime.now());
        order.setUser(user);
        orderRepo.save(order);
        // Update portfolio: add/subtract quantity, update average price...
        // (For simplicity, assume buy only)
        PortfolioEntry entry = portfolioRepo.findByUser(user).stream()
                .filter(e -> e.getStockSymbol().equals(symbol))
                .findFirst().orElse(new PortfolioEntry(null, symbol, 0, 0.0, user));
        int newQty = entry.getQuantity() + (type.equals("BUY") ? qty : -qty);
        double newAvg = (entry.getAveragePrice()*entry.getQuantity() + price*qty) / newQty;
        entry.setQuantity(newQty);
        entry.setAveragePrice(newAvg);
        portfolioRepo.save(entry);
        return order;
    }
}

