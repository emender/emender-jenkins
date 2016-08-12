#!/usr/bin/env bash

#
#  (C) Copyright 2016  Pavel Tisnovsky
#
#  All rights reserved. This program and the accompanying materials
#  are made available under the terms of the Eclipse Public License v1.0
#  which accompanies this distribution, and is available at
#  http://www.eclipse.org/legal/epl-v10.html
#

curl -v -X POST --header "Content-Type: application/json" -d '{"name":"doc-Red_Hat_Certificate_System-10.0-Administration_Guide-en-US (test-stage)"}' localhost:3000/api/enable_job

