#!/bin/env bash
curl -v -X POST --header "Content-Type: application/json" -d '{"name":"unknown", "url_to_repo":"new-url", "branch":"other_branch"}' localhost:3000/api/update_job

