(ns testing-semantic.prod
  (:require [testing-semantic.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
