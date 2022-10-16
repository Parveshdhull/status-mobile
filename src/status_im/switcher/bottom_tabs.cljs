(ns status-im.switcher.bottom-tabs
  (:require [quo.react-native :as rn]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [quo2.reanimated :as reanimated]
            [status-im.switcher.styles :as styles]
            [status-im.switcher.constants :as constants]
            [status-im.switcher.animation :as animation]
            [status-im.ui.components.icons.icons :as icons]
            [quo2.components.navigation.bottom-nav-tab :as bottom-nav-tab]))

;; Reagent atoms used for lazily loading home screen tabs
(def load-communities-tab? (reagent/atom false))
(def load-chats-tab? (reagent/atom false))
(def load-wallet-tab? (reagent/atom false))
(def load-browser-tab? (reagent/atom false))

(re-frame/reg-fx
 :new-ui/reset-bottom-tabs
 (fn []
   (reset! animation/selected-stack-id nil)
   (reset! load-communities-tab? false)
   (reset! load-chats-tab? false)
   (reset! load-wallet-tab? false)
   (reset! load-browser-tab? false)))

(defn bottom-tab-on-press [shared-values stack-id]
    (case stack-id
      :communities-stack (reset! load-communities-tab? true)
      :chats-stack       (reset! load-chats-tab? true)
      :wallet-stack      (reset! load-wallet-tab? true)
      :browser-stack     (reset! load-browser-tab? true))
    (animation/bottom-tab-on-press shared-values stack-id))

(defn bottom-tab [icon stack-id shared-values]
  [bottom-nav-tab/bottom-nav-tab
   {:icon             icon
    :icon-color-anim  (get
                       shared-values
                       (get constants/tabs-icon-color-keywords stack-id))
    :on-press         #(bottom-tab-on-press shared-values stack-id)}])

(defn bottom-tabs [shared-values]
  [rn/view {:style (styles/bottom-tabs-container false)}
   [rn/view {:style (styles/bottom-tabs)}
    [bottom-tab :main-icons2/communities :communities-stack shared-values]
    [bottom-tab :main-icons2/messages :chats-stack shared-values]
    [bottom-tab :main-icons2/wallet :wallet-stack shared-values]
    [bottom-tab :main-icons2/browser :browser-stack  shared-values]]])
