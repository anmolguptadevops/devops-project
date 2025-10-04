output "registry_id" {
  description = "The ID of the container registry"
  value       = digitalocean_container_registry.this.id
}

output "registry_name" {
  description = "The name of the container registry"
  value       = digitalocean_container_registry.this.name
}

output "registry_endpoint" {
  description = "The endpoint of the container registry"
  value       = digitalocean_container_registry.this.endpoint
}
