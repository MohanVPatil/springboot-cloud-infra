# AWS ECS Fargate Deployment using Terraform

This repository contains **Terraform scripts** to deploy a full infrastructure on AWS, including ECS Fargate services, ALBs (Application Load Balancers), VPC networking components, IAM roles, CloudWatch logging, and VPC endpoints for ECR.

---

## Infrastructure Overview

- **VPC** with public and private subnets
- **Internet Gateway** and **NAT Gateway**
- **Security Groups** for ECS and Load Balancers
- **Application Load Balancers**: Public and Private
- **Target Groups & Listeners** for AP, LOS, LMS, and MOB services
- **ECS Cluster** for running services on AWS Fargate
- **ECS Task Definitions and Services** for AP, LOS, LMS, and MOB
- **IAM Roles and Policies** for ECS execution and tasks
- **CloudWatch Logs** for container log management
- **VPC Endpoints** for private communication with ECR

---

## Modules

- `main.tf` – Defines VPC, subnets, IGW, NAT, route tables, security groups, ALBs, listeners, target groups, endpoints, and output values.
- `ecs-services.tf` – Uses remote state data from main infra and defines ECS task definitions, services, log groups, and container configurations for each service.

---

## Prerequisites

- AWS CLI configured (`aws configure`)
- Terraform installed ([Install Guide](https://developer.hashicorp.com/terraform/downloads))
- IAM permissions to provision AWS infrastructure

---

## Provisioning

To deploy the infrastructure using Jenkins or CLI:

```bash
# Move to main folder for VPC and infra provisioning
cd main

terraform init
terraform plan
terraform apply -auto-approve

# Move to ecs-services folder for ECS services deployment
cd ../ecs-services

terraform init
terraform plan
terraform apply -auto-approve
```

---

## Notes

- Subnets are separated for public and private use.
- Load Balancers and Target Groups are created for each container service.
- CloudWatch is used for logging ECS container logs.
- ECS services are deployed on Fargate with `awsvpc` networking mode.
- Sensitive values like `AWS region`, `account IDs`, `image URIs`, etc. should be updated before provisioning.

---

## Destroying Infrastructure

```bash
# Destroy ECS services first
cd ecs-services
terraform destroy -auto-approve

# Destroy main infrastructure
cd ../main
terraform destroy -auto-approve
```

---

## License

This project is Created for Educational Purpose Only.