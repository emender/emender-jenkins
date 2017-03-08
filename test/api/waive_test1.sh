#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d @waive_test1.json localhost:3000/api/waive
echo

