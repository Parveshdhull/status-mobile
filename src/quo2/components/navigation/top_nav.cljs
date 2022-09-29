(ns quo2.components.navigation.top-nav
  (:require [quo.theme :as theme]
            [quo.react-native :as rn]
            [quo2.foundations.colors :as colors]
            [quo2.components.buttons.button :as quo2.button]
            [quo2.components.avatars.user-avatar :as user-avatar]))

(defn- base-button [icon on-press accessibility-label]
  [quo2.button/button
   {:icon                true
    :size                32
    :type                :grey
    :style               {:margin-left 12}
    :on-press            on-press
    :accessibility-label accessibility-label}
   icon])

(defn top-nav
  "[top-nav opts]
  opts
  {:type                   :default/:blurred/:shell
   :new-notifications?     true/false
   :notification-indicator :unread-dot/:counter
   :open-profile           fn
   :open-search            fn
   :open-scanner           fn
   :open-activity-center   fn
   :style                  override-style
   :avatar                 user-avatar}
  "
  [{:keys [type new-notifications? notifications-indicator open-profile
           open-search open-scanner show-qr open-activity-center style avatar]}]
  [rn/view {:style (merge
                    {:height 56
                     :flex   1}
                    style)}
   ;; Left Section
   [rn/view {:style {:position :absolute
                     :left     20
                     :top      12}}
    [user-avatar/user-avatar
     (merge
      {:ring?             true
       :status-indicator? true
       :size              :small}
      avatar)]]
   ;; Right Section
   [rn/view {:style {:position :absolute
                     :right    20
                     :top      12
                     :flex-direction :row}}
    [base-button :main-icons2/search open-search :open-search-button]
    [base-button :main-icons2/scan open-scanner :open-scanner-button]
    [base-button :main-icons2/qr-code show-qr :show-qr-button]
    [base-button :main-icons2/activity-center open-activity-center :open-activity-center-button]]])
