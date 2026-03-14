package com.portfolio.service;

import com.portfolio.model.DeveloperProfile;
import com.portfolio.model.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;

@Service
public class PortfolioService {

    @Value("${github.api.token:}")
    private String githubToken;

    public String analyzeGitHub(String username) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            
            // Add GitHub authentication token if available
            if (githubToken != null && !githubToken.isEmpty()) {
                headers.set("Authorization", "token " + githubToken);
            }

            // Fetch user profile
            String userUrl = "https://api.github.com/users/" + username;
            HttpEntity<String> entity = new HttpEntity<>(headers);
            DeveloperProfile profile =
                    restTemplate.exchange(userUrl, HttpMethod.GET, entity, DeveloperProfile.class).getBody();

            // Fetch repositories
            String repoUrl = "https://api.github.com/users/" + username + "/repos";
            Repository[] repos =
                    restTemplate.exchange(repoUrl, HttpMethod.GET, entity, Repository[].class).getBody();

            int totalStars = 0;

            Map<String, Integer> languageCount = new HashMap<>();

            for (Repository repo : repos) {

                totalStars += repo.getStargazers_count();

                String lang = repo.getLanguage();

                if (lang != null) {
                    languageCount.put(lang, languageCount.getOrDefault(lang, 0) + 1);
                }
            }

            String topLanguage = "Unknown";
            int max = 0;

            for (Map.Entry<String, Integer> entry : languageCount.entrySet()) {

                if (entry.getValue() > max) {
                    max = entry.getValue();
                    topLanguage = entry.getKey();
                }
            }

            int score = profile.getFollowers() + profile.getPublic_repos() + totalStars;

            return "Username: " + profile.getLogin() +
                    "\nRepositories: " + profile.getPublic_repos() +
                    "\nFollowers: " + profile.getFollowers() +
                    "\nTotal Stars: " + totalStars +
                    "\nTop Language: " + topLanguage +
                    "\nDeveloper Score: " + score;
        } catch (RestClientException e) {
            // Return demo data if API fails (e.g., rate limit exceeded)
            return "Username: " + username +
                    "\nRepositories: 42" +
                    "\nFollowers: 1250" +
                    "\nTotal Stars: 8756" +
                    "\nTop Language: Java" +
                    "\nDeveloper Score: 10048" +
                    "\n\n(Demo data - Add a GitHub token to application.properties for real data)";
        }
    }
}