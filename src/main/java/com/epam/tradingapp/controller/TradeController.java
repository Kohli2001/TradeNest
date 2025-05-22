package com.epam.tradingapp.controller;

import com.epam.tradingapp.model.PortfolioEntry;
import com.epam.tradingapp.model.TradeOrder;
import com.epam.tradingapp.repository.TradeOrderRepository;
import com.epam.tradingapp.service.PortfolioService;
import com.epam.tradingapp.service.StockPriceService;
import com.epam.tradingapp.service.TradeService;
import com.epam.tradingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;
    private final UserService userService;
    private final PortfolioService portfolioService;
    private final StockPriceService stockPriceService;
    private final TradeOrderRepository orderRepo;

    //Place trade (BUY/SELL)
    @PostMapping("/trades")
    public TradeOrder placeTrade(@RequestParam String username,
                                 @RequestParam String symbol,
                                 @RequestParam int quantity,
                                 @RequestParam String type) {
        double currentPrice = stockPriceService.getPrice(symbol)/* call stock price service to get price */;
        return tradeService.placeOrder(username, symbol, quantity, type, currentPrice);
    }

    // Fetch portfolio for user
    @GetMapping("/portfolio")
    public List<PortfolioEntry> getPortfolio(@RequestParam String username) {
        return portfolioService.getPortfolio(username);
    }

    // Fetch trade history for user
    @GetMapping("/trades/history")
    public List<TradeOrder> tradeHistory(@RequestParam String username) {
        UserDetails userDetails = userService.loadUserByUsername(username) /* load user by username */;
        return orderRepo.findByUserDetails(userDetails);
    }
}

