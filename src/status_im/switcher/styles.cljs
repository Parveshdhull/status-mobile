(ns status-im.switcher.styles
  (:require [quo.theme :as theme]
            [quo2.foundations.colors :as colors]
            [status-im.utils.platform :as platform]
            [status-im.switcher.constants :as constants]))

;; Bottom Tabs
(defn bottom-tabs-container [pass-through?]
  {:background-color   (if pass-through? colors/neutral-100-opa-70 colors/neutral-100)
   :flex               1
   :align-items        :center
   :flex-direction     :column
   :height             (constants/bottom-tabs-container-height)
   :position           :absolute
   :bottom             -1
   :right              0
   :left               0})

(defn bottom-tabs []
  {:flex-direction     :row
   :position           :absolute
   :bottom             8
   :flex               1})

;; Home Stack
(defn home-stack []
  (let [{:keys [width height]} (constants/dimensions)]  
    (print width)
    {:border-bottom-left-radius  20
     :border-bottom-right-radius 20
     :background-color           colors/neutral-5
     :overflow                   :hidden
     :position                   :absolute
     :width                      width
     :height                     (- height (constants/bottom-tabs-container-height))}))
