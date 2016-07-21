#!/usr/bin/env bash

pushd .. > /dev/null
lein ring server-headless
popd > /dev/null

