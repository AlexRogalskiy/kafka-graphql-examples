(ns nl.openweb.graphql-endpoint.server
  (:require [com.stuartsierra.component :as component]
            [com.walmartlabs.lacinia.pedestal :as lp]
            [io.pedestal.http :as http]))

(defrecord Server [schema-provider server]

  component/Lifecycle
  (start [this]
    (assoc this :server (-> schema-provider
                            :schema
                            (lp/service-map {:graphiql      false
                                             :subscriptions true})
                            (assoc :io.pedestal.http/allowed-origins ["http://localhost:4200", "http://localhost:8181", "http://localhost:3449"])
                            http/create-server
                            http/start)))

  (stop [this]
    (http/stop server)
    (assoc this :server nil)))

(defn new-server
  []
  {:server (component/using (map->Server {})
                            [:schema-provider])})


