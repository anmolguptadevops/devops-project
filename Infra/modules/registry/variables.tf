variable "name" {
  description = "Name of the container registry"
  type        = string
}

variable "region" {
  description = "Region where the registry will be created"
  type        = string
  default     = "blr1"
}

variable "subscription_tier_slug" {
  description = "The subscription tier for the container registry"
  type        = string
  default     = "basic"
}
