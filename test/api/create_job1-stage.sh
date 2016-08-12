#!/usr/bin/env bash

#
#  (C) Copyright 2016  Pavel Tisnovsky
#
#  All rights reserved. This program and the accompanying materials
#  are made available under the terms of the Eclipse Public License v1.0
#  which accompanies this distribution, and is available at
#  http://www.eclipse.org/legal/epl-v10.html
#

curl -v -X POST --header "Content-Type: application/json" -d '{"name":"doc-Test_Product-1.0-Test_Book-en-US (test-stage)", "ssh_url_to_repo":"https://gitlab.cee.redhat.com/red-hat-certificate-system-documentation/doc-Red_Hat_Certificate_System-Administration_Guide.git", "branch":"master"}' localhost:3000/api/create_job

