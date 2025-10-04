terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "~> 2.0"
    }
  }
}

terraform {
  backend "s3" {
    bucket = "algotest-tf"
    key    = "digitalocean/k8s-cluster/terraform.tfstate"
    region = "us-east-1"
    endpoints = {
      s3 = "https://blr1.digitaloceanspaces.com"
    }
    skip_credentials_validation = true
    skip_metadata_api_check     = true
    skip_requesting_account_id  = true
    acl                         = "private"
  }
}