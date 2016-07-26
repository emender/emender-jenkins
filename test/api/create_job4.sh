#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d '{"name":"doc-Test_Product-1.0-Test_Book_3-en-US (test)", "branch":"master"}' localhost:3000/api/create_job

