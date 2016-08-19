#!/usr/bin/env bash

#
#  (C) Copyright 2016  Pavel Tisnovsky
#
#  All rights reserved. This program and the accompanying materials
#  are made available under the terms of the Eclipse Public License v1.0
#  which accompanies this distribution, and is available at
#  http://www.eclipse.org/legal/epl-v10.html
#

curl -X POST --header "Content-Type: application/json" -d '{"name":"test-Test_Product-1.0-Test_Book-en-US (stage)"}' localhost:3000/api/delete_job

