#!/bin/env bash
curl -v -X POST --header "Content-Type: application/json" -d '{"name":"wrong-job"}' localhost:3000/api/enable_job

