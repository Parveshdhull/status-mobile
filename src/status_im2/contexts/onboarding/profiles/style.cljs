(ns status-im2.contexts.onboarding.profiles.style
  (:require [quo2.foundations.colors :as colors]
            [react-native.platform :as platform]))

;; Profiles Section

(defn profiles-profile-card
  [last-item?]
  {:padding-horizontal 20
   :margin-bottom      (when-not last-item? -24)})

(def profiles-container
  {:position    :absolute
   :left        0
   :top         0
   :bottom      0
   :right       0
   :padding-top 112})

(def profiles-header
  {:flex-direction    :row
   :margin-horizontal 20
   :margin-bottom     20})

(def profiles-header-text
  {:color colors/white
   :flex  1})

;; Login Section

(def login-container
  {:position           :absolute
   :left               0
   :top                0
   :right              0
   :bottom             0
   :padding-top        56
   :padding-horizontal 20})

(def multi-profile-button
  {:align-self :flex-end})

(def login-profile-card
  {:margin-vertical 20})

(def keyboard-avoiding-view
  {:flex 1})

(def info-message
  {:margin-top 8})

(defn login-button
  []
  {:margin-top    8
   ;; TODO - Platform based margin-bottom doesn't work always
   ;; If android device uses swipe navigation we should use
   ;; same params as ios - https://github.com/status-im/status-mobile/issues/15381
   :margin-bottom (if platform/android? 20 46)})

