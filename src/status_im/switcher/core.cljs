(ns status-im.switcher.core
  (:require [status-im.utils.fx :as fx]
            [status-im.ethereum.json-rpc :as json-rpc]
            [status-im.switcher.constants :as constants]))

(fx/defn load-switcher-cards [{:keys [db] :as cofx}]
  ;; TODO - Load swithcher cards from database
  {:db (assoc db :navigation2/navigation2-stacks constants/default-cards)})
