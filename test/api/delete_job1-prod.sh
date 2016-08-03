#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d '{"name":"doc-Test_Product-1.0-Test_Book-en-US (test-prod)"}' localhost:3000/api/delete_job

