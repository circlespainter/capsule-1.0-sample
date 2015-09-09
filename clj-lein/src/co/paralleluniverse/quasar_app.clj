(ns co.paralleluniverse.quasar-app
	(:gen-class)
	(:require
		[co.paralleluniverse.pulsar.core :refer [channel fiber snd rcv join close!]])
	(:import (co.paralleluniverse.fibers Fiber)))

(defn -main
	"Application entry point"
	[& args]
	(let
		[increasing-to-echo (channel)
		 echo-to-increasing (channel)
		 increasing-fiber (fiber
												(loop [i 0
															 curr 0]
													(if (< i 10)
														(do
															(Fiber/sleep 1000)
															(println "INCREASER sending:" curr)
															(snd increasing-to-echo curr)
															(let [curr (rcv echo-to-increasing)]
																(println "INCREASER received:" curr)
																(println "INCREASER now:" (+ curr 1))
																(recur (+ i 1)
																			 (+ curr 1))))
														(do
															(println "INCREASER closing channel and exiting")
															(close! increasing-to-echo)))))
		 echo-fiber (fiber
									(loop []
										(do
											(Fiber/sleep 1000)
											(let [curr (rcv increasing-to-echo)]
												(println "ECHO received:" curr)
												(if curr
													(do
														(println "ECHO sending:" curr)
														(snd echo-to-increasing curr)
														(recur))
													(do
														(println "ECHO detected closed channel, closing and exiting")
														(close! echo-to-increasing)))))))]
		(join increasing-fiber)
		(join echo-fiber)))
