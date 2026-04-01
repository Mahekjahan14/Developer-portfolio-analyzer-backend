const BASE_URL = "https://portfolio-backend-1v4e.onrender.com";

export const parsePortfolioData = (rawData) => {
  const lines = rawData.split("\n").filter(line => line.trim());
  const data = {};
  lines.forEach(line => {
    const [key, value] = line.split(":").map(s => s.trim());
    if (key && value) {
      data[key] = isNaN(value) ? value : parseInt(value);
    }
  });
  return data;
};

export const analyzePortfolio = async (username) => {
  if (!username.trim()) throw new Error("Please enter a GitHub username");
  
  const response = await fetch(`${BASE_URL}/analyze/${username}`);
  if (!response.ok) throw new Error(`Failed to analyze ${username}`);
  
  const data = await response.text();
  return parsePortfolioData(data);
};

export const analyzeMultiple = async (usernames) => {
  const results = {};
  for (const username of usernames) {
    try {
      results[username] = await analyzePortfolio(username);
    } catch (err) {
      results[username] = null;
    }
  }
  return results;
};

export const getPortfolios = async () => {
  try {
    const response = await fetch(`${BASE_URL}/portfolios`);
    if (response.ok) return await response.json();
  } catch (err) {
    console.error("Error fetching portfolios:", err);
  }
  return [];
};

export const getSavePortfolio = async (portfolio) => {
  try {
    const response = await fetch(`${BASE_URL}/save`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(portfolio)
    });
    return response.ok;
  } catch (err) {
    console.error("Error saving portfolio:", err);
    return false;
  }
};
