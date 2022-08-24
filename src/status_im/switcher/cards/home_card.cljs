(ns status-im.switcher.cards.home-card
  (:require [quo.react-native :as rn]
            [quo2.components.text :as text]
            [status-im.constants :as constants]
            [quo2.components.button :as button]
            [status-im.utils.handlers :refer [>evt <sub]]
            [status-im.switcher.cards.styles :as styles]))


(defn communities-card []
  [rn/touchable-without-feedback {:on-press #(print "pressed")}
   [rn/view {:style (styles/messaging-card-main-container)}
    [text/text (styles/communities-home-card-title-props) "Communities"]
    [text/text (styles/communities-home-card-subtitle-props) "Discover"]]])

(defn card [{:keys [id toggle-switcher-screen]}]
  (case id
    :communities-card [communities-card]))
