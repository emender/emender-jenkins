#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d '{"name":"wrong-name"}' localhost:3000/api/delete_job

