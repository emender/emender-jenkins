#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d "bad*input" localhost:3000/api/waive
echo

