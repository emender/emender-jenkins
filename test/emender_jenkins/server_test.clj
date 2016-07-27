;
;  (C) Copyright 2016  Pavel Tisnovsky
;
;  All rights reserved. This program and the accompanying materials
;  are made available under the terms of the Eclipse Public License v1.0
;  which accompanies this distribution, and is available at
;  http://www.eclipse.org/legal/epl-v10.html
;
;  Contributors:
;      Pavel Tisnovsky
;

(ns emender-jenkins.core-test
  (:require [clojure.test :refer :all]
            [emender-jenkins.server :refer :all]))

;
; Common functions used by tests.
;

(defn callable?
    "Test if given function-name is bound to the real function."
    [function-name]
    (clojure.test/function? function-name))

;
; Tests for various function definitions
;



;(get-api-command nil "/api")

;(get-api-command "/" "/api")
;(get-api-command "xyzzy/" "/api")
;(get-api-command "xyzzy/xyzzy" "/api")

;(get-api-command "/api" "/api")

;(get-api-command "/api/create_job", "/api")

;(get-api-command "/api/system/banners" "/api")


