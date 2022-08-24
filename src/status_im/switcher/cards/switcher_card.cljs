(ns status-im.switcher.cards.switcher-card
  (:require [status-im.switcher.cards.home-card :as home-card]
            [status-im.switcher.cards.messaging-card :as messaging-card]))

(defn card-view [{:keys [type] :as card}]
  (case type
    :chat      [messaging-card/card card]
    :home-card [home-card/card card]))
            
