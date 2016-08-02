#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d '{"name":"doc-Test_Product-1.0-Test_Book-en-US (test-preview)", "url_to_repo":"new-url", "branch":"master"}' localhost:3000/api/update_job

