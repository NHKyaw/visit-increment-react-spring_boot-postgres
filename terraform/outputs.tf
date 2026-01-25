output "ec2_public_ip" {
  value = aws_instance.visit_record_instance.public_ip
}
