# Weather-Monitoring-Real-Time-Data-Processing_System
This project is a Real-Time Data Processing System for weather monitoring. It retrieves real-time weather data from the OpenWeatherMap API for various cities in India and processes it to provide daily weather summaries, handle alerting thresholds, and visualize historical weather trends.

Features
Real-time weather data collection: Continuously fetches weather data for major metros in India every 5 minutes.
Daily Weather Summary: Calculates daily aggregates such as average, maximum, and minimum temperatures. It also identifies the dominant weather condition for the day.
Alerting System: Triggers alerts if predefined thresholds are breached (e.g., temperature exceeds 35°C for two consecutive updates).
Historical Data Storage: Weather summaries are stored in PostgreSQL for historical data analysis.
API Access: Expose REST endpoints to fetch daily summaries.
Technologies Used
Java: Backend processing with Spring Boot.
Spring Boot: Framework used to build the backend API and services.
PostgreSQL: Database for persisting weather summaries and historical data.
OpenWeatherMap API: For real-time weather data.
How to Run the Project Locally
Prerequisites
Java 17 or later installed on your system.
Maven installed.
PostgreSQL installed and running.
Project Setup
Clone the repository:

bash
Copy code
git clone https://github.com/your-username/weather-monitoring-system.git
cd weather-monitoring-system
Set up PostgreSQL:

Create a PostgreSQL database, e.g., weather_db.
Set the username and password for your PostgreSQL in the application.properties file.
properties
Copy code
spring.datasource.url=jdbc:postgresql://localhost:5432/weather_db
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
Set up API keys:

Sign up for a free API key on the OpenWeatherMap API website.
Add the API key in the application.properties file.
properties
Copy code
openweathermap.api.key=your-api-key
Run the application:

Use Maven to build and run the project.
bash
Copy code
mvn clean install
mvn spring-boot:run
API Endpoints
1. Get Daily Weather Summary
Endpoint: /weather/daily-summary
Method: GET
Query Params: city (required)
Example:

bash
Copy code
curl "http://localhost:8080/weather/daily-summary?city=Delhi"
Response:
json
Copy code
[
  {
    "city": "Delhi",
    "date": "2024-10-19",
    "averageTemp": 30.5,
    "maxTemp": 35,
    "minTemp": 25,
    "dominantWeather": "Clear"
  }
]
2. Add New Weather Summary (automatically adds weather data)
Data is automatically retrieved every 5 minutes and persisted in the database.
3. Alerting System
Alerts are triggered when the temperature exceeds 35°C for two consecutive updates.
You can define custom thresholds in the application.properties file.
Project Structure
/src/main/java/com/example/weather/: Contains all the Java source files for the project.

WeatherApplication.java: Main entry point for the Spring Boot application.
WeatherService.java: Core service for fetching, processing, and storing weather data.
WeatherController.java: REST controller for exposing API endpoints.
WeatherRepository.java: Repository interface for accessing PostgreSQL.
/src/main/resources/application.properties: Configuration file for the application. Contains database settings, API keys, and thresholds.

Key Functionalities
1. Real-Time Weather Data Collection
The system makes continuous calls to the OpenWeatherMap API every 5 minutes to collect weather data for major Indian cities: Delhi, Mumbai, Chennai, Bangalore, Kolkata, and Hyderabad.

2. Daily Weather Summary
The system aggregates daily weather data and stores it. For each city, it calculates:

Average Temperature
Maximum Temperature
Minimum Temperature
Dominant Weather Condition
The dominant weather condition is determined based on the most frequent weather condition for the day.

3. Alerting Thresholds
Define user-configurable thresholds for temperature (e.g., temperature exceeding 35°C).
If the temperature exceeds the threshold for two consecutive intervals, an alert is triggered.
Alerts can be logged in the console, or an email system can be integrated (not included in this version).
