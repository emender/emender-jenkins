#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d '{"name":"doc-Red_Hat_Certificate_System-10.0-Administration_Guide-en-US (test-preview)"}' localhost:3000/api/start_job
