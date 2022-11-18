(ns status-im.switcher.shell
  (:require [quo.react-native :as rn]
            [status-im.utils.fx :as fx]
            [re-frame.core :as re-frame]
            [status-im.i18n.i18n :as i18n]
            [quo2.foundations.colors :as colors]
            [quo2.components.markdown.text :as text]
            [quo.components.safe-area :as safe-area]
            [quo2.components.navigation.top-nav :as top-nav]
            [status-im.switcher.switcher-cards.switcher-cards :as switcher-cards]))

(fx/defn close-switcher-card
  {:events [:shell/close-switcher-card]}
  [{:keys [db]} id]
  {:db (update-in db [:navigation2/navigation2-stacks] dissoc id)})

(defn placeholder []
  [rn/view {:style {:position            :absolute
                    :top                 0
                    :left                0
                    :right               0
                    :bottom              -1
                    :justify-content     :center
                    :align-items         :center
                    :accessibility-label :shell-placeholder-view}}
   [rn/view {:style {:margin-top       12
                     :width            80
                     :height           80
                     :border-radius    16
                     :background-color colors/neutral-90}}]
   [text/text {:size   :heading-2
               :weight :semi-bold
               :style  {:margin-top 20
                        :color      colors/white}}
    (i18n/label :t/shell-placeholder-title)]
   [text/text {:size   :paragraph-1
               :weight :regular
               :align  :center
               :style  {:margin-top 8
                        :color      colors/white}}
    (i18n/label :t/shell-placeholder-subtitle)]])

(defn jump-to-text []
  [text/text {:size   :heading-1
              :weight :semi-bold
              :style  {:color         colors/white
                       :margin-top    (+ 68 (.-currentHeight ^js rn/status-bar))
                       :margin-bottom 20
                       :margin-left   20}}
   (i18n/label :t/jump-to)])

(defn render-card [{:keys [id type]}]
  (let [card (case type
               :chat @(re-frame/subscribe [:shell/one-to-one-card id]))]
    [switcher-cards/card card]))

(defn jump-to-list [switcher-cards shell-margin]
  (if (seq switcher-cards)
    [rn/flat-list
     {:data                 switcher-cards
      :render-fn            render-card
      :key-fn               :id
      :header               (jump-to-text)
      :num-columns          2
      :column-wrapper-style {:margin-horizontal shell-margin
                             :justify-content   :space-between
                             :margin-bottom     16}
      :style                {:top      0
                             :left     0
                             :right    0
                             :bottom   -1
                             :position :absolute}}]
    [placeholder]))

(defn shell []
  (let [switcher-cards @(re-frame/subscribe [:shell/switcher-cards])
        width          @(re-frame/subscribe [:dimensions/window-width])
        shell-margin   (/ (- width 320) 3)] ;; 320 - two cards width
    [safe-area/consumer
     (fn [insets]
       [rn/view {:style {:top              0
                         :left             0
                         :right            0
                         :bottom           -1
                         :position         :absolute
                         :background-color colors/neutral-100}}
        [top-nav/top-nav {:type  :shell
                          :style {:margin-top (:top insets)}}]
        [jump-to-list switcher-cards shell-margin]])]))

