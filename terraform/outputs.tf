output "ec2_public_ip" {
  value = aws_eip.visit_record_eip.public_ip
}
