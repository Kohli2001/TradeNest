package com.epam.tradingapp.repository;

import com.epam.tradingapp.model.TradeOrder;
import com.epam.tradingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {
     List<TradeOrder> findByUserId(Long userId);
     List<TradeOrder> findByUser(User user);
     List<TradeOrder> findByUserDetails(UserDetails userDetails);
}
