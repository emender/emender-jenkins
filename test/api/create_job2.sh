#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d '{"url_to_repo":"https://gitlab.cee.redhat.com/red-hat-certificate-system-documentation/doc-Red_Hat_Certificate_System-Administration_Guide.git", "branch":"master"}' localhost:3000/api/create_job
