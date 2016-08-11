#!/bin/env bash
curl -v -X POST --header "Content-Type: application/json" -d '{"name":"doc-Red_Hat_Certificate_System-10.0-Administration_Guide-en-US (test-stage)"}' localhost:3000/api/disable_job

