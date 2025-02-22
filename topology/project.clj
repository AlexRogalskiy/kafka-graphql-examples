(defproject nl.openweb/topology "0.1.0-SNAPSHOT"
  :plugins [[lein-modules "0.3.11"]]
  :dependencies [[clj-time "0.15.2"]
                 [ch.qos.logback/logback-classic :version]
                 [com.damballa/abracad :version :exclusions [org.apache.avro/avro com.thoughtworks.paranamer/paranamer]]
                 [com.fasterxml.jackson.core/jackson-annotations :version]
                 [com.fasterxml.jackson.core/jackson-core :version]
                 [com.fasterxml.jackson.core/jackson-databind :version]
                 [io.confluent/kafka-avro-serializer :version :exclusions [io.netty/netty org.slf4j/slf4j-log4j12 com.fasterxml.jackson.core/jackson-core]]
                 [org.apache.avro/avro :version]
                 [org.clojure/clojure :version]
                 [org.clojure/tools.logging :version]]
  :java-source-paths ["target/main/java"]
  :javac-options ["-target" "11" "-source" "11"]
  :prep-tasks ["compile" "javac"]
  :aot :all
  :profiles {:provided {:dependencies [[org.apache.avro/avro-compiler "1.9.2"]]
                        :source-paths ["env/dev/clj"]
                        :aot          [nl.openweb.dev.avro-compile]
                        }})
