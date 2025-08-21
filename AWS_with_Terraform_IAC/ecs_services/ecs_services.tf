data "terraform_remote_state" "main" {
  backend = "local"
  config = {
    path = "../main/terraform.tfstate"
  }
}

resource "aws_iam_policy" "ecs_task_execution_policy" {
  name        = "ecs-task-execution-policy"
  description = "Policy for ECS tasks to write logs, access EFS, and execute commands"
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ],
        Effect   = "Allow",
        Resource = "arn:aws:logs:*:*:*"
      },
      {
        Effect = "Allow",
        Action = [
          "ssm:UpdateInstanceInformation",
          "ssmmessages:CreateControlChannel",
          "ssmmessages:CreateDataChannel",
          "ssmmessages:OpenControlChannel",
          "ssmmessages:OpenDataChannel"
        ],
        Resource = "*"
      },
      {
        Effect = "Allow",
        Action = [
          "ecs:ExecuteCommand"
        ],
        Resource = "*"
      }
    ]
  })
}

# ECS Task Definitions for AP Container
resource "aws_ecs_task_definition" "ap" {
  family                   = "ap-container"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  execution_role_arn       = data.terraform_remote_state.main.outputs.ecs_task_execution_role_arn
  task_role_arn            = data.terraform_remote_state.main.outputs.ecs_task_role_arn
  cpu                      = "256"
  memory                   = "512"

  container_definitions = jsonencode([{
    name      = "ap-container"
    image     = "your-aws-account-id.dkr.ecr.your-aws-region.amazonaws.com/ap-container-repo:latest"
    essential = true
    portMappings = [
      {
        containerPort = 80
        hostPort      = 80
      }
    ]
    environment = [
      {
        name  = "HOSTNAME"
        value = data.terraform_remote_state.main.outputs.private_lb_dns_name
      },
      {
        name  = "ALB_DNS_NAME"
        value = data.terraform_remote_state.main.outputs.private_lb_dns_name
      }
    ]
    logConfiguration = {
      logDriver = "awslogs"
      options = {
        "awslogs-group"         = "/ecs/ap-container"
        "awslogs-region"        = "your-aws-region"
        "awslogs-stream-prefix" = "ap-container"
      }
    }
  }])
}

# ECS Task Definitions for LOS Container
resource "aws_ecs_task_definition" "los" {
  family                   = "los-container"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  execution_role_arn       = data.terraform_remote_state.main.outputs.ecs_task_execution_role_arn
  task_role_arn            = data.terraform_remote_state.main.outputs.ecs_task_role_arn
  cpu                      = "4096"
  memory                   = "8192"

  container_definitions = jsonencode([{
    name      = "los-container"
    image     = "your-aws-account-id.dkr.ecr.your-aws-region.amazonaws.com/los-container-repo:latest"
    essential = true
    portMappings = [
      {
        containerPort = 8080
        hostPort      = 8080
      },
      {
        containerPort = 8009
        hostPort      = 8009
      }
    ]
    environment = [
      {
        name  = "HOSTNAME"
        value = data.terraform_remote_state.main.outputs.private_lb_dns_name
      },
      {
        name  = "ALB_DNS_NAME"
        value = data.terraform_remote_state.main.outputs.private_lb_dns_name
      }
    ]
    logConfiguration = {
      logDriver = "awslogs"
      options = {
        "awslogs-group"         = "/ecs/los-container"
        "awslogs-region"        = "your-aws-region"
        "awslogs-stream-prefix" = "los-container"
      }
    }
  }])

}

#--for LMS added
# ECS Task Definitions for LMS Container
resource "aws_ecs_task_definition" "lms" {
  family                   = "lms-container"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  execution_role_arn       = data.terraform_remote_state.main.outputs.ecs_task_execution_role_arn
  task_role_arn            = data.terraform_remote_state.main.outputs.ecs_task_role_arn
  cpu                      = "4096"
  memory                   = "8192"

  container_definitions = jsonencode([{
    name      = "lms-container"
    image     = "your-aws-account-id.dkr.ecr.your-aws-region.amazonaws.com/lms-container-repo:latest"
    essential = true
    portMappings = [
      {
        containerPort = 8270
        hostPort      = 8270
      },
      {
        containerPort = 8199
        hostPort      = 8199
      }
    ]
    environment = [
      {
        name  = "HOSTNAME"
        value = data.terraform_remote_state.main.outputs.private_lb_dns_name
      },
      {
        name  = "ALB_DNS_NAME"
        value = data.terraform_remote_state.main.outputs.private_lb_dns_name
      }
    ]
    logConfiguration = {
      logDriver = "awslogs"
      options = {
        "awslogs-group"         = "/ecs/lms-container"
        "awslogs-region"        = "your-aws-region"
        "awslogs-stream-prefix" = "lms-container"
      }
    }
  }])
}

resource "aws_ecs_task_definition" "mob" {
  family                   = "mob-container"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  execution_role_arn       = data.terraform_remote_state.main.outputs.ecs_task_execution_role_arn
  task_role_arn            = data.terraform_remote_state.main.outputs.ecs_task_role_arn
  cpu                      = "2048"
  memory                   = "8192"

  container_definitions = jsonencode([{
    name      = "mob-container"
    image     = "your-aws-account-id.dkr.ecr.your-aws-region.amazonaws.com/mob-container-repo:latest"
    essential = true
    portMappings = [
      {
        containerPort = 8890
        hostPort      = 8890
      },
      {
        containerPort = 8819
        hostPort      = 8819
      }
    ]
    environment = [
      {
        name  = "HOSTNAME"
        value = data.terraform_remote_state.main.outputs.private_lb_dns_name
      },
      {
        name  = "ALB_DNS_NAME"
        value = data.terraform_remote_state.main.outputs.private_lb_dns_name
      }
    ]
    logConfiguration = {
      logDriver = "awslogs"
      options = {
        "awslogs-group"         = "/ecs/mob-container"
        "awslogs-region"        = "your-aws-region"
        "awslogs-stream-prefix" = "mob-container"
      }
    }
  }])
}



# ECS Services for AP Container
resource "aws_ecs_service" "ap" {
  name                   = "ap-service"
  cluster                = data.terraform_remote_state.main.outputs.cluster_id
  task_definition        = aws_ecs_task_definition.ap.arn
  desired_count          = 1
  enable_execute_command = true

  network_configuration {
    subnets          = data.terraform_remote_state.main.outputs.public_subnet_ids
    security_groups  = [data.terraform_remote_state.main.outputs.security_group_id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = data.terraform_remote_state.main.outputs.ap_target_group_arn
    container_name   = "ap-container"
    container_port   = 80
  }

  launch_type = "FARGATE"
}

# ECS Services for LOS Container
resource "aws_ecs_service" "los" {
  name                   = "los-service"
  cluster                = data.terraform_remote_state.main.outputs.cluster_id
  task_definition        = aws_ecs_task_definition.los.arn
  desired_count          = 1
  enable_execute_command = true

  network_configuration {
    subnets          = data.terraform_remote_state.main.outputs.private_subnet_ids
    security_groups  = [data.terraform_remote_state.main.outputs.security_group_id]
    assign_public_ip = false
  }

  load_balancer {
    target_group_arn = data.terraform_remote_state.main.outputs.los_target_group_arn
    container_name   = "los-container"
    container_port   = 8080
  }

  launch_type = "FARGATE"
}

#--for LMS added
# ECS Services for LMS Container
resource "aws_ecs_service" "lms" {
  name                   = "lms-service"
  cluster                = data.terraform_remote_state.main.outputs.cluster_id
  task_definition        = aws_ecs_task_definition.lms.arn
  desired_count          = 1
  enable_execute_command = true

  network_configuration {
    subnets          = data.terraform_remote_state.main.outputs.private_subnet_ids
    security_groups  = [data.terraform_remote_state.main.outputs.security_group_id]
    assign_public_ip = false
  }

  load_balancer {
    target_group_arn = data.terraform_remote_state.main.outputs.lms_target_group_arn
    container_name   = "lms-container"
    container_port   = 8270
  }

  launch_type = "FARGATE"
}

resource "aws_ecs_service" "mob" {
  name                   = "mob-service"
  cluster                = data.terraform_remote_state.main.outputs.cluster_id
  task_definition        = aws_ecs_task_definition.mob.arn
  desired_count          = 1
  enable_execute_command = true

  network_configuration {
    subnets          = data.terraform_remote_state.main.outputs.private_subnet_ids
    security_groups  = [data.terraform_remote_state.main.outputs.security_group_id]
    assign_public_ip = false
  }

  load_balancer {
    target_group_arn = data.terraform_remote_state.main.outputs.mob_target_group_arn
    container_name   = "mob-container"
    container_port   = 8890
  }

  launch_type = "FARGATE"
}


resource "aws_cloudwatch_log_group" "lms" {
  name              = "/ecs/lms-container"
  retention_in_days = 7
}

resource "aws_cloudwatch_log_group" "los" {
  name              = "/ecs/los-container"
  retention_in_days = 7
}

resource "aws_cloudwatch_log_group" "ap" {
  name              = "/ecs/ap-container"
  retention_in_days = 7
}

resource "aws_cloudwatch_log_group" "mob" {
  name              = "/ecs/mob-container"
  retention_in_days = 7
}
