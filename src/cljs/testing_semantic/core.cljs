(ns testing-semantic.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]
            [semantic-ui.core :refer [$]]))

;; -------------------------
;; Views

(enable-console-print!)

(defn home-page []
  [:div
   [:h1 "Home page"]
   [:> ($ :Button) {:href "/elements"} "Elements"]
   [:> ($ :Button) {:class "myButton"
                    :primary true
                    :on-click (print "yay printing")} "Molecules"]
   [:> ($ :Card.Group)
    [:> ($ :Card)
     [:> ($ :Card.Content)
      [:> ($ :Card.Header) "I am a header"]
      [:> ($ :Card.Meta) "Card meta stuff blah"]
      [:> ($ :Card.Description) "Description and other text goes here blah blah blah"]]]]])

(defn about-page []
  [:div [:h2 "About testing-semantic"]
   [:div [:a {:href "/"} "go to the home page"]]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
