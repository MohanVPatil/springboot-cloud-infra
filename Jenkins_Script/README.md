# Jenkins Terraform CI/CD Pipeline

This repository includes a Jenkins pipeline to automate **Terraform-based AWS infrastructure provisioning and teardown**. It is designed to manage both your **main infrastructure** and **ECS services** using reusable Terraform configuration files.

---

## Project Structure

```bash
.
├── Jenkinsfile                  # Jenkins pipeline to provision/destroy AWS resources
├── terraform/
│   ├── main/                    # Main infrastructure (VPC, IAM, networking etc.)
│   │   └── main.tf              # Terraform code for core AWS infra
│   └── ecs-services/           # ECS services definitions
│       └── ecs-services.tf     # ECS Terraform configuration
```

---

## Jenkins Pipeline Features

- **Terraform format, validate, plan, apply** for both main and ECS modules.
- Parameterized pipeline with options:
  - `TF_PROVISION_ONLY`: Provision infra and ECS only
  - `TF_DESTROY_ONLY`: Destroy infra and ECS only
  - `TF_PROVISION_AND_TF_DESTROY`: Full cycle in a single run
- Uses Terraform outputs for verification.
- Includes confirmation step before `terraform destroy`.

---

## Jenkins Parameters

- `ACTION` – Select from:
  - `TF_PROVISION_ONLY`
  - `TF_DESTROY_ONLY`
  - `TF_PROVISION_AND_TF_DESTROY`

---

## Environment Variables

| Variable      | Description                              |
|---------------|------------------------------------------|
| `TF_MAIN`     | Path to your main Terraform directory     |
| `TF_SERVICE`  | Path to your ECS service Terraform files  |
| `AWS_REGION`  | AWS Region used for deployment            |

Set these appropriately in the `environment` block of your Jenkinsfile.

---

## Prerequisites

- Jenkins instance with pipeline support
- AWS CLI and Terraform installed on Jenkins agent
- Valid AWS credentials configured in Jenkins

---

## How to Use

1. Push this `Jenkinsfile` into your repository.
2. In Jenkins, create a **Pipeline project** pointing to this repo.
3. Run the pipeline and select the desired `ACTION`.

---

## Example Directory Structure

```
project-root/
├── Jenkinsfile
├── terraform/
│   ├── main/
│   │   └── main.tf
│   └── ecs-services/
│       └── ecs-services.tf
```

---

## Notes

- The pipeline uses `bat` commands for Windows-based agents. For Linux agents, replace `bat` with `sh` and adjust the commands accordingly.
- The pipeline includes manual confirmation before destruction to avoid accidental deletion of live resources.

---

## Maintainer

Developed by [Moharn Paatil] - Java + DevOps Engineer

---

## License

This project was created for Educational Purpose Only.