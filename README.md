**PROJECT NAME: CLEANHUB CODING CHALLENGE**

**Author**: Monika Kaushal

**Project Description**: Create an HTTP service for getting all orders for active customers and print the top 'X' customers given a specified time period.

**Java Version Used**: Java 17

**Summary**: Call CleanHub Marketplace "/orders" API and fetch the active customers. Now for each customerRoute, call "/orders/logos" API and get "quantity" field value.
To build a history of such quantities against customer and time, fetch via cronjob periodically and updating database.
To calculate the top customers between given dates, against each customer we first find the quantity difference for above dates.
Return the top "k" elements from this list(sortedBy quantity).

**Assumptions**: Assuming that customerOrderQuantity won't update very frequently we are configuring the scheduler to run daily.
To meet the rate limit restrictions in CleanHub Marketplace API, we are keeping tps: 10calls/sec and sleep time = 2s.


## To Set up The Application and run Test Cases:
```bash
mvn clean install
```

## Run locally
To start the service locally at http://localhost:8082:
```bash
./mvnw spring-boot:run
```

To interact with it:
```bash
# interact - GET orders for active customers
curl --location 'localhost:8082/orders'
# interact - get the top 'X' customers for given dates
curl --location 'localhost:8082/orders/history?size=2&startDate=2024-04-02&endDate=2024-04-04'
```
**Areas to improve upon:**
1. We can use PostgreSQL DB instead of in-memory, enabling data persistence and scalability.
2. We can have a cleanup function to move the older data in archival tables for faster retrieval.
4. Implement @EnableWebSecurity to prevent CSRF and XSS attacks.







