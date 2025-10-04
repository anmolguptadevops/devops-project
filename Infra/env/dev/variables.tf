variable "region" {
  type        = string
  description = "The region to deploy the resources in."
}

variable "vpc_name" {
  type        = string
  description = "The name of the VPC."
}

variable "vpc_cidr" {
  type        = string
  description = "The CIDR block for the VPC."
}

variable "cluster_name" {
  type        = string
  description = "The name of the DOKS cluster."
}

variable "k8s_version" {
  type        = string
  description = "The Kubernetes version for the DOKS cluster. Run `doctl kubernetes options versions` to get a list of available versions."
}

variable "node_size" {
  type        = string
  description = "The size of the nodes in the DOKS cluster."
}

variable "node_count" {
  type        = number
  description = "The number of nodes in the DOKS cluster."
}

variable "registry_name" {
  type        = string
  description = "The name of the container registry."
}

variable "subscription_tier_slug" {
  description = "The subscription tier for the container registry"
  type        = string
  default     = "basic"
}

variable "kubeconfig_output_path" {
  type        = string
  description = "The path where the kubeconfig file will be saved locally."
  default     = "./kubeconfig"
}