#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d '{"name":"wrong-job"}' localhost:3000/api/disable_job

