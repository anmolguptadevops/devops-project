module "vpc" {
  source   = "../../modules/vpc"
  vpc_name = var.vpc_name
  region   = var.region
  ip_range = var.vpc_cidr
}

module "registry" {
  source = "../../modules/registry"

  name                   = var.registry_name
  region                 = var.region
  subscription_tier_slug = var.subscription_tier_slug

  depends_on = [module.vpc]
}

module "doks" {
  source       = "../../modules/doks"
  cluster_name = var.cluster_name
  region       = var.region
  k8s_version  = var.k8s_version

  vpc_uuid   = module.vpc.vpc_id
  node_size  = var.node_size
  node_count = var.node_count

  depends_on = [module.vpc, module.registry]
}

resource "local_file" "kubeconfig" {
  content  = module.doks.kubeconfig
  filename = var.kubeconfig_output_path

  depends_on = [module.doks]
}
