package com.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portfolio.entity.DeveloperPortfolio;

public interface PortfolioRepository extends JpaRepository<DeveloperPortfolio, String> {

}