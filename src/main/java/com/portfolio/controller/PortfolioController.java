package com.portfolio.controller;

import com.portfolio.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "*"})
@RequestMapping("/analyze")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/{username}")
    public String analyze(@PathVariable String username) {
        return portfolioService.analyzeGitHub(username);
    }

    @GetMapping("/demo/{username}")
    public String analyzeDemo(@PathVariable String username) {
        // Demo endpoint for testing without GitHub API token
        return "Username: " + username +
                "\nRepositories: 42" +
                "\nFollowers: 1250" +
                "\nTotal Stars: 8756" +
                "\nTop Language: Java" +
                "\nDeveloper Score: 10048";
    }
}