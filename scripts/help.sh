#!/bin/sh

pushd .. > /dev/null
lein run -- -h
popd > /dev/null

