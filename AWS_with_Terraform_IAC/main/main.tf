provider "aws" {
  region = "your-aws-region"
}

# VPC
resource "aws_vpc" "main" {
  cidr_block = "*.*.*.*/*"
}

data "aws_iam_role" "ecs_task_execution" {
  name = "ecsTaskExecutionRole"
}

data "aws_iam_policy_document" "ecs_task_assume_role" {
  statement {
    effect = "Allow"
    principals {
      type        = "Service"
      identifiers = ["ecs-tasks.amazonaws.com"]
    }
    actions = ["sts:AssumeRole"]
  }
}

resource "aws_iam_role" "ecs_task_role" {
  name               = "ecsTaskRole"
  assume_role_policy = data.aws_iam_policy_document.ecs_task_assume_role.json
}

# Public Subnets
resource "aws_subnet" "public_subnet1" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.1.0/24"
  availability_zone       = "your-aws-regiona"
  map_public_ip_on_launch = true
}

resource "aws_subnet" "public_subnet2" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = "10.0.2.0/24"
  availability_zone       = "your-aws-regionb"
  map_public_ip_on_launch = true
}

# Private Subnets
resource "aws_subnet" "private_subnet1" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.3.0/24"
  availability_zone = "your-aws-regiona"
}

resource "aws_subnet" "private_subnet2" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.4.0/24"
  availability_zone = "your-aws-regionb"
}

# Internet Gateway and NAT Gateway for Private Subnets
resource "aws_internet_gateway" "main" {
  vpc_id = aws_vpc.main.id
}

resource "aws_eip" "main" {
  domain = "vpc"
}

resource "aws_nat_gateway" "main" {
  allocation_id = aws_eip.main.id
  subnet_id     = aws_subnet.public_subnet1.id
}

# Route Tables
resource "aws_route_table" "public" {
  vpc_id = aws_vpc.main.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.main.id
  }
}

resource "aws_route_table" "private" {
  vpc_id = aws_vpc.main.id

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.main.id
  }
}

# Associate Route Tables with Subnets
resource "aws_route_table_association" "public_subnet1" {
  subnet_id      = aws_subnet.public_subnet1.id
  route_table_id = aws_route_table.public.id
}

resource "aws_route_table_association" "public_subnet2" {
  subnet_id      = aws_subnet.public_subnet2.id
  route_table_id = aws_route_table.public.id
}

resource "aws_route_table_association" "private_subnet1" {
  subnet_id      = aws_subnet.private_subnet1.id
  route_table_id = aws_route_table.private.id
}

resource "aws_route_table_association" "private_subnet2" {
  subnet_id      = aws_subnet.private_subnet2.id
  route_table_id = aws_route_table.private.id
}

# Security Group
resource "aws_security_group" "main" {
  vpc_id = aws_vpc.main.id

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8009
    to_port     = 8009
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8888
    to_port     = 8888
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8959
    to_port     = 8959
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8819
    to_port     = 8819
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8270
    to_port     = 8270
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8199
    to_port     = 8199
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8890
    to_port     = 8890
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 2049
    to_port     = 2049
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

}

# Security Group for Private Load Balancer
resource "aws_security_group" "private_lb_sg" {
  vpc_id = aws_vpc.main.id

  ingress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "private-lb-sg"
  }
}


# ECS Cluster
resource "aws_ecs_cluster" "main" {
  name = "my-cluster"
}

# Load Balancers
resource "aws_lb" "public_lb" {
  name               = "public-lb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.main.id]
  subnets            = [aws_subnet.public_subnet1.id, aws_subnet.public_subnet2.id]
}

resource "aws_lb" "private_lb" {
  name               = "private-lb"
  internal           = true
  load_balancer_type = "application"
  security_groups    = [aws_security_group.private_lb_sg.id]
  subnets            = [aws_subnet.private_subnet1.id, aws_subnet.private_subnet2.id]
}

# Target Groups
resource "aws_lb_target_group" "ap" {
  name        = "ap-tg"
  port        = 80
  protocol    = "HTTP"
  vpc_id      = aws_vpc.main.id
  target_type = "ip"

  health_check {
    path                = "/"
    port                = "80"
    protocol            = "HTTP"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }
}

resource "aws_lb_target_group" "los" {
  name        = "los-tg"
  port        = 8080
  protocol    = "HTTP"
  vpc_id      = aws_vpc.main.id
  target_type = "ip"

  health_check {
    path                = "/"
    port                = "8080"
    protocol            = "HTTP"
    interval            = 60
    timeout             = 30
    healthy_threshold   = 5
    unhealthy_threshold = 5
  }
}

#--for LMS added
resource "aws_lb_target_group" "lms" {
  name        = "lms-tg"
  port        = 8270
  protocol    = "HTTP"
  vpc_id      = aws_vpc.main.id
  target_type = "ip"

  health_check {
    path                = "/"
    port                = "8270"
    protocol            = "HTTP"
    interval            = 60
    timeout             = 30
    healthy_threshold   = 5
    unhealthy_threshold = 5
  }
}

# Target Group for Mob Container
resource "aws_lb_target_group" "mob" {
  name        = "mob-tg"
  port        = 8890
  protocol    = "HTTP"
  vpc_id      = aws_vpc.main.id
  target_type = "ip"

  health_check {
    path                = "/"
    port                = "8890"
    protocol            = "HTTP"
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }
}

# Listener for Public LB for AP container
resource "aws_lb_listener" "public_listener" {
  load_balancer_arn = aws_lb.public_lb.arn
  port              = 80
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.ap.arn
  }
}

# Listener for public LB for los-container			
resource "aws_lb_listener" "public_listener_los" {
  load_balancer_arn = aws_lb.public_lb.arn
  port              = 8080
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.ap.arn
  }
}

#--for LMS added
# Listener for public LB for lms-container			
resource "aws_lb_listener" "public_listener_lms" {
  load_balancer_arn = aws_lb.public_lb.arn
  port              = 8270
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.ap.arn
  }
}

# Listener for Public LB for Mob container
resource "aws_lb_listener" "public_listener_mob" {
  load_balancer_arn = aws_lb.public_lb.arn
  port              = 8890
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.ap.arn
  }
}

# Listener for Private LB for los-container			
resource "aws_lb_listener" "private_listener" {
  load_balancer_arn = aws_lb.private_lb.arn
  port              = 8080
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.los.arn
  }
}

#--for LMS added
# Listener for Private LB for lms-container	
resource "aws_lb_listener" "private_listener_lms" {
  load_balancer_arn = aws_lb.private_lb.arn
  port              = 8270
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.lms.arn
  }
}

# Listener for Public LB for Mob container
resource "aws_lb_listener" "private_listener_mob" {
  load_balancer_arn = aws_lb.private_lb.arn
  port              = 8890
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.mob.arn
  }
}

#--for LMS added

# Additional Listener Rule for /csfweb path on Private Listener
resource "aws_lb_listener_rule" "private_listener_rule" {
  listener_arn = aws_lb_listener.private_listener.arn #change public from private
  priority     = 1

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.los.arn
  }

  condition {
    path_pattern {
      values = ["/csfweb*"]
    }
  }
}

# VPC Endpoints for ECR
resource "aws_vpc_endpoint" "ecr_api" {
  vpc_id             = aws_vpc.main.id
  service_name       = "com.amazonaws.your-aws-region.ecr.api"
  subnet_ids         = [aws_subnet.private_subnet1.id, aws_subnet.private_subnet2.id]
  security_group_ids = [aws_security_group.main.id]
  vpc_endpoint_type  = "Interface"
}

resource "aws_vpc_endpoint" "ecr_dkr" {
  vpc_id             = aws_vpc.main.id
  service_name       = "com.amazonaws.your-aws-region.ecr.dkr"
  subnet_ids         = [aws_subnet.private_subnet1.id, aws_subnet.private_subnet2.id]
  security_group_ids = [aws_security_group.main.id]
  vpc_endpoint_type  = "Interface"
}

# Output the required resources
output "public_subnet_ids" {
  value = [aws_subnet.public_subnet1.id, aws_subnet.public_subnet2.id]
}

output "private_subnet_ids" {
  value = [aws_subnet.private_subnet1.id, aws_subnet.private_subnet2.id]
}

output "security_group_id" {
  value = aws_security_group.main.id
}

output "private_lb_security_group_id" {
  value = aws_security_group.private_lb_sg.id
}

output "cluster_id" {
  value = aws_ecs_cluster.main.id
}

output "ap_target_group_arn" {
  value = aws_lb_target_group.ap.arn
}

output "los_target_group_arn" {
  value = aws_lb_target_group.los.arn
}

output "lms_target_group_arn" {
  value = aws_lb_target_group.lms.arn
}

output "mob_target_group_arn" {
  value = aws_lb_target_group.mob.arn
}

output "public_lb_dns_name" {
  value = aws_lb.public_lb.dns_name
}

output "private_lb_dns_name" {
  value = aws_lb.private_lb.dns_name
}

output "ecs_task_role" {
  value = aws_iam_role.ecs_task_role.arn
}

output "ecs_task_role_arn" {
  value = aws_iam_role.ecs_task_role.arn
}

output "ecs_task_execution_role_arn" {
  value = data.aws_iam_role.ecs_task_execution.arn
}

output "ecs_task_role_assume_role_policy" {
  value = data.aws_iam_policy_document.ecs_task_assume_role.json
}

