variable "region" {
  default = "ap-southeast-1"
}

variable "visit_increment_key_name" {
  description = "EC2 key pair name"
  default     = "visit_increment"
  type        = string
}

variable "instance_type" {
  description = "EC2 instance type"
  type        = string
  default     = "t2.micro"
}
variable "environment" {
  description = "Deployment environment"
  type        = string
}
