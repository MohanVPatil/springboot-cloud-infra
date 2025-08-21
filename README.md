# springboot-cloud-infra

A **production-grade monorepo portfolio** demonstrating practical expertise in **Java backend development** and **DevOps automation**. Built using modern technologies like **Spring Boot, Redis, Docker, Terraform, AWS (ECS)**, and **Jenkins**, this repository reflects full-stack engineering capabilities from coding to cloud deployment.

---

## Repository Structure

| Folder/Module                   | Description                                                                 |
|--------------------------------|-----------------------------------------------------------------------------|
| `Redis_with_Java-SpringBoot/`  | Spring Boot microservice with Redis caching, pub-sub messaging, and DB sync |
| `Containerization/`            | Standalone Spring Boot app containerized with Docker & Compose              |
| `AWS_with_Terraform_IAC/`      | Terraform scripts for AWS infra — ECS, ECR, ALB, IAM, CloudWatch, etc.      |
| `aws-automation-scripts/`      | Shell scripts for ECR push, AWS login, tagging automation                   |
| `Jenkins_Script/`              | Declarative Jenkins pipeline to provision and deploy via Terraform & Docker |

---

## Tech Stack & Tools

**Languages & Frameworks**  
- Java 17, Spring Boot, Spring Data JPA, Hibernate, Liquibase  
- Redis (Write-behind caching, Pub/Sub), Maven  

**Infrastructure & Cloud**  
- Terraform (modular IaC), AWS ECS Fargate, ECR, S3, IAM, CloudWatch  
- Docker, Docker Compose  

**CI/CD & DevOps**  
- Jenkins (declarative pipeline), GitHub Actions (optional)  
- AWS CLI, Git, CloudWatch Logs  

**Utilities & IDEs**  
- Postman, IntelliJ IDEA / Spring Tool Suite, Ubuntu Terminal  

---

## Key Highlights

- ✅ Layered Spring Boot architecture (Controller → Service → Repository)
- ✅ Redis-powered caching with asynchronous DB persistence
- ✅ Redis Pub/Sub for decoupled real-time message exchange
- ✅ Modular Terraform IaC for scalable AWS infrastructure provisioning
- ✅ CI/CD pipeline with Docker image build, ECR push, and ECS Fargate deployment
- ✅ Monitoring and alerting with AWS CloudWatch integration
- ✅ Environment variable injection and multi-target ALB setup

---

## Objective

This monorepo serves as a **portfolio-quality showcase** of my:

- Proficiency in **Java-based backend development**
- Experience with **Redis caching**, asynchronous data flows, and pub-sub systems
- Skill in **Infrastructure as Code** using Terraform
- Ability to implement **CI/CD pipelines** for cloud-native deployments
- Focus on production-ready, modular, and maintainable architecture

---

## Usage & Navigation

Each folder is a self-contained module with:

- Setup steps inside its own `README.md`
- Independent configs, build files, and Docker/Terraform pipelines
- Ready-to-run samples for testing and exploration

> Navigate to each folder to explore the respective domain and its use-case implementation.

---
## Local Setup

Example for running Redis-based microservice:

```bash
cd Redis_with_Java-SpringBoot
./mvnw spring-boot:run


For Jenkins Pipelines:

Point Jenkins to Jenkins_Script/Jenkinsfile
Use ACTION parameter to control provision or destroy

## Final Note

This repository is crafted with the intention of helping recruiters, employers, and technical leaders assess my real-world expertise in:

-  Java Backend Engineering
-  AWS Cloud & DevOps Engineering
-  Microservices Architecture & Caching
-  CI/CD Automation and Infrastructure as Code

If you're evaluating my profile, this project demonstrates:

- My ability to build secure, scalable, and cloud-native backend systems
- A strong focus on DevOps automation, clean code, and operational excellence
- Hands-on experience with AWS services like ECS Fargate, ECR, S3, IAM, CloudWatch, and SES
- End-to-end ownership from design and development to infrastructure provisioning and production deployment

Thank you for visiting!  
Feel free to connect, explore the code, or request my resume via the links above.

---
<<<<<<< HEAD
## TODO
=======
## 📝 TODO
>>>>>>> ab7dfa54c49421d5ad9effaab283dfe4649c4c35
The following improvements and feature extensions are planned for future iterations:

- OAuth2 / JWT-based Authentication & Authorization : for securing REST APIs  
- Spring Cloud Gateway : setup for service routing and API gateway layer   
- Auto-scaling ECS services : using AWS Application Auto Scaling policies  
- GitHub Actions CI/CD integration : as an alternative to Jenkins pipelines  
- Helm Charts : for managing Kubernetes-based deployment in future (EKS)

> Have suggestions or feedback? Feel free to drop a message on https://www.linkedin.com/in/999-moharn-paatil


## Connect With Me
- 📧 Email: moharnpaatil999@gmail.com
- 💼 LinkedIn: https://www.linkedin.com/in/999-moharn-paatil
- 🌐 Portfolio Repo: https://github.com/MohanVPatil/springboot-cloud-infra

---
