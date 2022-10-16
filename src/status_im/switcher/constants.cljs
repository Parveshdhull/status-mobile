(ns status-im.switcher.constants
  (:require [quo.react-native :as rn]
            [reagent.core :as reagent]
            [status-im.utils.handlers :refer [<sub]]
            [status-im.utils.platform :as platform]))

;; For translucent status bar(android), dimensions/window also includes status bar's height,
;; this offset is used for correctly calculating switcher position
(def switcher-height-offset
  (if platform/android? (max (:status-bar-height @rn/navigation-const) 24) 0))
;; todo (maybe change into) (max switcher-height-offset 24)

(print switcher-height-offset)

(defn bottom-tabs-container-height []
  (if platform/android? 57 82))

(defn bottom-tabs-extended-container-height []
  (if platform/android? 90 120))

(defn dimensions []
  (let [{:keys [width height]} (<sub [:dimensions/window])]
    {:width  width
     :height height}))

(def stacks-ids [:communities-stack :chats-stack :wallet-stack :browser-stack])

(def stacks-opacity-keywords
  {:communities-stack :communities-stack-opacity
   :chats-stack       :chats-stack-opacity
   :wallet-stack      :wallet-stack-opacity
   :browser-stack     :browser-stack-opacity})

(def stacks-pointer-keywords
  {:communities-stack :communities-stack-pointer
   :chats-stack       :chats-stack-pointer
   :wallet-stack      :wallet-stack-pointer
   :browser-stack     :browser-stack-pointer})

(def tabs-icon-color-keywords
  {:communities-stack :communities-tab-icon-color
   :chats-stack       :chats-tab-icon-opacity
   :wallet-stack      :wallet-tab-icon-opacity
   :browser-stack     :browser-tab-icon-opacity})

(def pass-through? (reagent/atom false))
