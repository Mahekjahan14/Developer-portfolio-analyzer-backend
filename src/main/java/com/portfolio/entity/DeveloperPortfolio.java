package com.portfolio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DeveloperPortfolio {

    @Id
    private String githubUsername;

    private int publicRepos;
    private int followers;
    private int following;
    private int portfolioScore;

    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(int publicRepos) {
        this.publicRepos = publicRepos;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getPortfolioScore() {
        return portfolioScore;
    }

    public void setPortfolioScore(int portfolioScore) {
        this.portfolioScore = portfolioScore;
    }
}