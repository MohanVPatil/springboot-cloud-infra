# **Redis User Profile Caching Service (Java \+ Spring Boot)**

A **Spring Boot microservice** for **User Profile Caching with Redis** featuring **cache synchronization**, **pub-sub messaging**, and **event-driven design**. The project integrates **PostgreSQL** as the persistent database and demonstrates best practices in caching, key expiration handling, and clean architecture.

## **Project Overview**

This project demonstrates:

* Managing user profiles via RESTful APIs.

* Redis caching for performance optimization.

* **Pub/Sub messaging with Redis** for real-time updates.

* Event-driven architecture with **publishers and listeners**.

* Automatic cache-to-DB synchronization after key expiry.

* Clean layered Spring Boot architecture (Controller → Service → Repository).

##  **Project Structure**

`src/`  
 `└── main/`  
     `└── java/`  
         `└── com.coder.learn/`  
             `├── config/                # Configuration (Redis, Jackson)`  
             `├── controller/            # REST APIs for User management`  
             `├── dto/                   # Data Transfer Objects`  
             `├── entity/            # JPA Entities (User, UserAccess, Logs)`  
             `├── event/                 # Domain Events`  
             `├── listener/         # Redis Key Expiration, Pub/Sub Consumer`  
             `├── publisher/             # Redis Pub/Sub Publisher`  
             `├── repository/            # Spring Data JPA Repositories`  
             `└── scheduler/             # Cache Refresh Schedulers`  
 `└── resources/`  
     `└── application.yml               # DB & Redis configuration`

## **Technologies Used**

* Java 17

* Spring Boot

* Spring Data JPA (Hibernate)

* PostgreSQL

* Redis (Cache & Pub/Sub Messaging)

* Event Listeners & Publishers

* GitHub

## **Key Features**

- **Create, Update, Delete User via REST APIs**  
- **Redis Cache synchronization with PostgreSQL**  
- **Pub/Sub messaging pattern with Redis channels**  
- **Redis Key Expiration Listener for cache invalidation & DB sync**  
-  **Clean code with DTO mapping and layered exception handling**  
- **Spring Data JPA for DB operations**

## **API Endpoints**

|  |  |  |
| :---- | :---- | :---- |
| **Endpoint** | **Method** | **Description** |
| `/users/{id}` | GET | Fetch user details by user ID |
| `/users/addUser` | POST | Add a new user to the database |
| `/users/editUser/{id}` | PUT | Update user details by ID |
| `/users/deleteUser/{id}` | DELETE | Delete a user by ID |
| `/users/addUser/full-info` | POST | Save full user info (User, Access, ActivityLog) into DB and Redis cache |
| `/users/addOrUpdateUser` | POST | Sync user from Redis cache to DB or insert if not present |

## **How to Run Locally**

### **1\. Prerequisites:**

* Java 17

* PostgreSQL (local or remote)

* Redis server (local or remote)

### **2\. Clone the Repository**

`git clone https://github.com/MohanVPatil/springboot-cloud-infra.git`

`cd springboot-cloud-infra`

### **3\. Configure `application.yml`**

Update:

* PostgreSQL DB URL, username, password

* Redis host & port

### **4\. Run the Application**

`./mvnw spring-boot:run`

### **5\. Test APIs**

* Use Postman or Curl to test API endpoints.

* Redis Insight tool to monitor cache entries & pub-sub channels.

## 

## **Core Functionalities Explained**

* **Redis Cache Integration**:

  * User data is cached for faster retrieval.

  * Cache keys have TTL (time to live) to ensure data consistency.

* **Key Expiration Listener**:

  * When cache keys expire, listener updates data into PostgreSQL automatically.

* **Pub/Sub Messaging**:

  * Publisher sends cache update messages.

  * Subscriber listens and performs actions (invalidate, refresh, etc.).

* **Layered Spring Boot Architecture**:

  * Controller → Service → Repository → DB

  * DTO mapping for cleaner request/response.

## **License**

Open-source for learning & demonstration purposes.

[^1]

[^1]:  **Redis User Profile Caching Service-By-Moharn\_Paatil**