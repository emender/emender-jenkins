#!/usr/bin/env bash

#
#  (C) Copyright 2016  Pavel Tisnovsky
#
#  All rights reserved. This program and the accompanying materials
#  are made available under the terms of the Eclipse Public License v1.0
#  which accompanies this distribution, and is available at
#  http://www.eclipse.org/legal/epl-v10.html
#

curl -v -X POST --header "Content-Type: application/json" -d '{"name":"test-Test_Product-1.0-Test_Book-en-US (wrong)", "ssh_url_to_repo":"new-url", "branch":"master"}' localhost:3000/api/update_job

