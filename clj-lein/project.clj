(def quasar-version "0.7.3")

(defproject co.paralleluniverse/capsule-1.0-sample "1.0"
  :description "A Pulsar-Capsule Leiningen sample"
  :url "http://github.com/circlespainter/capsule-1.0-sample"
  :min-lein-version "2.5.0"
  :javac-options ["-target" "1.8" "-source" "1.8"]
  :dependencies [[org.clojure/clojure "1.7.0"]
								 [co.paralleluniverse/quasar-core ~quasar-version :classifier "jdk8"]
								 [co.paralleluniverse/pulsar ~quasar-version]
								 [ch.qos.logback/logback-classic "1.1.3"]]
  :java-agents [[co.paralleluniverse/quasar-core ~quasar-version :classifier "jdk8" :options "m"]]
	:main co.paralleluniverse.quasar-app
	:aot [co.paralleluniverse.quasar-app] ; Removes warning about Lein 3.0 not compiling implicitly :main anymore
	:plugins [[lein-capsule "0.2.0-SNAPSHOT"]]
	:capsule {
		:types {
			:thin {}
			:fat { :name "fat-capsule.jar" }
		}
	})
