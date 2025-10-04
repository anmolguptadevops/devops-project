output "dev_vpc_id" {
  value = module.vpc.vpc_id
}

output "dev_cluster_id" {
  value = module.doks.cluster_id
}



output "dev_registry_endpoint" {
  value = module.registry.registry_endpoint
}
