# School Management Project

## Overview

The **School Management Project** is a microservice-based application designed to manage various school-related data such as students, teachers, and administrative tasks. It leverages **Spring Boot** for microservice development, **Spring Cloud** for centralized configuration and service discovery, **Redis** for caching, and **Kafka** for event-driven communication. The system consists of several microservices working together in a cohesive architecture.

### Microservices:
- **Config Service**: Stores configuration-related information.
- **Discovery Service**: Provides service registration and discovery via Eureka.
- **User Management Service**: Manages students and teachers, handles user registration, and triggers Kafka events.
- **Notification Service**: Listens to Kafka messages and processes notifications related to user creation or updates.

## Microservices Overview

### 1. **Config Service**
The **Config Service** is responsible for storing configuration-related information for the entire system. This service is protected with the following credentials:

- **Username**: `root`
- **Password**: `s3cr3t`

Configuration settings can be stored in `application.properties` or `application.yml` files for all services.

```properties
spring.cloud.config.username=root
spring.cloud.config.password=s3cr3t
