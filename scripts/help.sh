#!/usr/bin/env bash

pushd .. > /dev/null
lein run -- -h
popd > /dev/null

