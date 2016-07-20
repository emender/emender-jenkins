#!/bin/sh

pushd .. > /dev/null
lein run -- -c
popd > /dev/null

