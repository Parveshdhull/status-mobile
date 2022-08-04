(ns quo2.components.information-box
  (:require [quo.theme :as theme]
            [quo.react-native :as rn]
            [reagent.core :as reagent]
            [clojure.string :as string]
            [quo2.foundations.colors :as colors]
            [quo2.components.icon :as quo2.icons]
            [quo2.components.button :as quo2.button]
            [quo2.components.info-message :as info-message]
            [status-im.async-storage.core :as async-storage]))

(def themes
  {:light {:default-bg         colors/white
           :default-border     colors/neutral-20
           :default-icon       colors/neutral-50
           :default-text       colors/black
           :informative-bg     colors/primary-50-opa-5
           :informative-border colors/primary-50-opa-10
           :informative-icon   colors/primary-50
           :informative-text   colors/black
           :error-bg           colors/danger-50-opa-5
           :error-border       colors/danger-50-opa-10
           :error-icon         colors/danger-50
           :error-text         colors/danger-50
           :close-button       colors/black}
   :dark  {:default-bg         colors/neutral-90
           :default-border     colors/neutral-70
           :default-icon       colors/neutral-40
           :default-text       colors/white
           :informative-bg     colors/primary-50-opa-5
           :informative-border colors/primary-50-opa-10
           :informative-icon   colors/white
           :informative-text   colors/white
           :error-bg           colors/danger-50-opa-5
           :error-border       colors/danger-50-opa-10
           :error-icon         colors/danger-50
           :error-text         colors/danger-50
           :close-button       colors/white}})

(defn get-color [key]
  (get-in themes [(theme/get-theme) key]))

(defn get-color-by-suffix [type suffix]
  (get-color (keyword (str (name type) "-" suffix))))

(defn get-information-box-id-hash [id]
  (hash id))

(defn close-information-box [id closed? on-close]
  (reset! closed? true)
  (async-storage/set-item! (get-information-box-id-hash id) true)
  (when on-close (on-close)))

(defn information-box [{:keys [type icon closable? style button-label on-button-press on-close id]} message]
  (let [closed? (reagent/atom true)]
    (if closable?
      (async-storage/get-item (get-information-box-id-hash id) #(reset! closed? %))
      (reset! closed? false))
    (fn []
      (when-not @closed?
        (let [background-color (get-color-by-suffix type "bg")
              border-color     (get-color-by-suffix type "border")
              icon-color       (get-color-by-suffix type "icon")
              text-color       (get-color-by-suffix type "text")
              include-button?  (not (string/blank? button-label))]
          [rn/view {:style (merge {:background-color   background-color
                                   :border-color       border-color
                                   :border-width       1
                                   :border-radius      12
                                   :padding-vertical   (if include-button? 10 11)
                                   :padding-horizontal 16} style)}
           [rn/view {:style {:flex-direction :row
                             :align-self     :flex-start}}
            [info-message/info-message {:size        :default
                                        :icon        icon
                                        :text-color  text-color
                                        :icon-color  icon-color} message]
            (when closable?
              [rn/touchable-opacity
               {:on-press #(close-information-box id closed? on-close)}
               [quo2.icons/icon :main-icons2/close {:size            12
                                                    :color           (get-color :close-button)
                                                    :container-style {:margin-top  3
                                                                      :margin-left 8}}]])]
           (when include-button?
             [quo2.button/button {:type     :primary
                                  :size     24
                                  :on-press on-button-press
                                  :style    {:margin-left 20
                                             :margin-top  8
                                             :align-self :flex-start}} button-label])])))))
