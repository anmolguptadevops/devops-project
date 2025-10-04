output "cluster_id" {
  value = digitalocean_kubernetes_cluster.this.id
}

output "kubeconfig" {
  value     = digitalocean_kubernetes_cluster.this.kube_config[0].raw_config
  sensitive = true
}
