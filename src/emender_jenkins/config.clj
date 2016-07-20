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

(ns emender-jenkins.config
    "Module that contains all functions required to to load configuration from the INI file.")

(require '[clojure.pprint :as pprint])

(require '[emender-jenkins.config-loader :as config-loader])

(def default-port
    "Default port used when no -p or --port CLI option is specified."
    "3000")

(defn load-configuration-from-ini
    [filename]
    (config-loader/load-configuration-file filename))

