#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d '{"name":"doc-Test_Product-1.0-Test_Book-en-US (test)", "url_to_repo":"new-url", "branch":"other_branch"}' localhost:3000/api/update_job

