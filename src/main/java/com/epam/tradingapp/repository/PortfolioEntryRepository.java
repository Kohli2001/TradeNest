package com.epam.tradingapp.repository;

import com.epam.tradingapp.model.PortfolioEntry;
import com.epam.tradingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioEntryRepository extends JpaRepository<PortfolioEntry,Long> {
    List<PortfolioEntry> findByUser(User user);
}
