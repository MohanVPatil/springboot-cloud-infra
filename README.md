# springboot-cloud-infra

A production-grade, monorepo portfolio that showcases real-world **Java backend development** combined with **DevOps automation**, built using **Spring Boot, Redis, Docker, Terraform, AWS (ECS), and Jenkins**.

This repository is designed to demonstrate **end-to-end delivery pipelines**, **microservice containerization**, **infrastructure as code**, and **cloud-native deployments** — reflecting full-stack ownership from code to cloud.

---

## Repository Structure

| Folder/Module                | Description                                                                 |
|-----------------------------|-----------------------------------------------------------------------------|
| `redis-user-cache-sync/`    | Spring Boot microservice with Redis-based caching, pub-sub messaging, and DB sync logic |
| `docker-springboot/`        | Standalone Spring Boot application containerized with Docker and Docker Compose |
| `terraform-aws-infra/`      | Terraform scripts for provisioning core AWS infrastructure — VPC, ECS, ECR, ALB, IAM, CloudWatch, etc. |
| `aws-automation-scripts/`   | Shell scripts to automate AWS resource management (e.g., login, tagging, ECR push) |
| `jenkins-pipeline-scripts/` | Declarative Jenkins pipeline for provisioning, deploying, and tearing down infra with Terraform and Docker |

---

## Tech Stack & Tools

**Languages & Frameworks**  
- Java 17, Spring Boot, Spring Data JPA, Hibernate, Liquibase  
- Redis (Caching, Pub/Sub), Maven  

**Infrastructure & Cloud**  
- Terraform (modular IaC), AWS ECS Fargate, ECR, S3, CloudWatch, IAM, VPC  
- Docker, Docker Compose  

**CI/CD & DevOps**  
- Jenkins (Declarative Pipeline), GitHub Actions (optional extension)  
- AWS CLI, CloudWatch Logs, Git  

**Utilities & IDEs**  
- Postman, IntelliJ IDEA / Spring Tool Suite (STS), Ubuntu Terminal  

---

## Key Highlights

✅ **Spring Boot Microservice** with clean layered architecture (Controller → Service → Repository)  
✅ **Redis-backed write-behind caching** mechanism with async DB persistence  
✅ **Redis Pub/Sub** for decoupled communication across distributed services  
✅ **Infrastructure as Code (IaC)** using Terraform with modular, reusable structure  
✅ **End-to-end CI/CD** using Jenkins to build Docker images, push to ECR, and deploy on ECS Fargate  
✅ **Automated logging and monitoring** using CloudWatch for ECS task containers  
✅ **Support for private and public ALBs**, multiple target groups, and environment variables injected into containers

---

## Objective

This monorepo serves as a **portfolio-quality showcase** demonstrating:

- Backend API development using Java + Spring Boot  
- Advanced caching, messaging, and async data flows  
- DevOps maturity through Infrastructure as Code and CI/CD pipelines  
- Real-world cloud-native deployments using AWS ECS, ECR, and Terraform  
- Practical scenarios like microservice communication, container orchestration, and log aggregation

---

## Usage & Navigation

Each folder is a self-contained module with:

- Project-specific `README.md` containing build/run instructions  
- Modular code and configuration files  
- Linked pipelines, Dockerfiles, or Terraform modules for easy integration

> Navigate to each folder to explore the detailed use case it solves, and understand the best practices adopted for scalable deployments.

---

## Test & Run Locally

For services like `redis-user-cache-sync` or `docker-springboot`:

```bash
cd redis-user-cache-sync
./mvnw spring-boot:run


## Connect With Me

- 📧 **Email**: [moharnpaatil999@gmail.com](mailto:moharnpaatil999@gmail.com)
- 💼 **LinkedIn**: [linkedin.com/in/999-moharn-paatil](https://www.linkedin.com/in/999-moharn-paatil)
- 📄 **Resume**: [Request via Gmail](https://mail.google.com/mail/?view=cm&fs=1&to=moharnpaatil999@gmail.com&su=Request%20for%20Resume%20via%20GitHub&body=Hi%20Moharn%2C%0A%0AI%20found%20your%20GitHub%20project%20really%20impressive%20and%20would%20love%20to%20connect.%20Could%20you%20please%20share%20your%20resume%3F%0A%0ARegards%2C
- 🌐 **Portfolio Repo**: [github.com/MohanVPatil/springboot-cloud-infra](https://github.com/MohanVPatil/springboot-cloud-infra)


## Final Note

This repository is thoughtfully crafted for **employers, recruiters, and technical decision-makers** who are evaluating my capabilities in:

- **Backend Java Development**
- **AWS Cloud & DevOps Engineering**
- **Microservices Design & Architecture**
- **CI/CD Automation and Infrastructure as Code**

If you're reviewing my profile, this project is intended to:

- ✅ Demonstrate my ability to deliver **production-grade, scalable, and cloud-native** solutions  
- ✅ Reflect my commitment to **clean code, modular design, and DevOps best practices**  
- ✅ Showcase a strong **automation-first mindset** and hands-on expertise in **infrastructure provisioning, container orchestration, and cloud deployments**

I invite you to explore this repository as a **comprehensive representation of my skills, experience, and engineering mindset**.


