(ns status-im.switcher.shell-stack
  (:require [quo.react-native :as rn]
            [quo2.reanimated :as reanimated]
            [status-im.switcher.shell :as shell]
            [quo2.foundations.colors :as colors]
            [status-im.switcher.animation :as animation]
            [status-im.switcher.constants :as constants]
            [status-im.switcher.home-stack :as home-stack]
            [status-im.switcher.bottom-tabs :as bottom-tabs]))

(defn shell-stack []
  [:f>
   (fn []
     (let [shared-values (animation/get-shared-values)]
       [:<>
        [shell/shell]
        [bottom-tabs/bottom-tabs shared-values]
        [home-stack/home-stack shared-values]]))])
