(ns nl.openweb.graphql-endpoint.viz
  (:require [nl.openweb.graphql-endpoint.system :as system]
            [com.walmartlabs.system-viz :refer [visualize-system]]))

(defn -main
  []
  (visualize-system (system/new-system) {:format  :png})
  (System/exit 0))
