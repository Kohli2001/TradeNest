package com.epam.tradingapp.service;

import com.epam.tradingapp.exception.UsernameNotFoundException;
import com.epam.tradingapp.model.PortfolioEntry;
import com.epam.tradingapp.model.TradeOrder;
import com.epam.tradingapp.model.User;
import com.epam.tradingapp.repository.PortfolioEntryRepository;
import com.epam.tradingapp.repository.TradeOrderRepository;
import com.epam.tradingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioEntryRepository portfolioRepository;
    private final TradeOrderRepository tradeOrderRepository;
    private final UserRepository userRepository;

    public List<PortfolioEntry> getPortfolio(String username) {
        // Load user by username
        User user = userRepository.findByUsername(username);
        if(user==null){throw new UsernameNotFoundException("User not found");}

        // Fetch all trade orders for user
        List<TradeOrder> orders = tradeOrderRepository.findByUser(user);

        // Process orders to build portfolio
        Map<String, Integer> holdings = new HashMap<>();
        for (TradeOrder order : orders) {
            int qty = holdings.getOrDefault(order.getStockSymbol(), 0);
            if (order.getOrderType().equalsIgnoreCase("BUY")) {
                qty += order.getQuantity();
            } else if (order.getOrderType().equalsIgnoreCase("SELL")) {
                qty -= order.getQuantity();
            }
            holdings.put(order.getStockSymbol(), qty);
        }

        // Convert holdings to portfolio entries
        List<PortfolioEntry> portfolio = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            if (entry.getValue() > 0) {
                portfolio.add(new PortfolioEntry(entry.getKey(), entry.getValue()));
            }
        }

        return portfolio;
    }
}

