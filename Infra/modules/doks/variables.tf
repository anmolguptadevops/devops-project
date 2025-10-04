variable "cluster_name" {
  type = string
}

variable "region" {
  type = string
}

variable "k8s_version" {
  type        = string
  description = "Kubernetes version"
}


variable "node_size" {
  type = string
}

variable "node_count" {
  type = number
}

variable "vpc_uuid" {
  type = string
}