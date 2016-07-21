#!/usr/bin/env bash

pushd .. > /dev/null
lein run -- $@
popd > /dev/null

