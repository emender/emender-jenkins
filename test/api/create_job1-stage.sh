#!/bin/env bash
curl -v -X POST --header "Content-Type: application/json" -d '{"name":"doc-Test_Product-1.0-Test_Book-en-US (test-stage)", "url_to_repo":"https://gitlab.cee.redhat.com/red-hat-certificate-system-documentation/doc-Red_Hat_Certificate_System-Administration_Guide.git", "branch":"master"}' localhost:3000/api/create_job

