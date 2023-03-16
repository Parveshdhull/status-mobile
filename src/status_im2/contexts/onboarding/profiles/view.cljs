(ns status-im2.contexts.onboarding.profiles.view
  (:require [quo2.core :as quo]
            [utils.i18n :as i18n]
            [utils.re-frame :as rf]
            [reagent.core :as reagent]
            [react-native.core :as rn]
            [utils.security.core :as security]
            [quo2.foundations.colors :as colors]
            [status-im2.contexts.onboarding.profiles.style :as style]
            [status-im2.contexts.onboarding.common.background :as background]))

(def show-profiles? (reagent/atom false))

(defn login-multiaccount
  []
  (rf/dispatch [:multiaccounts.login.ui/password-input-submitted]))

(defn profile-card
  [{:keys [name key-uid customization-color keycard-pairing last-index]} index]
  (let [last-item? (= last-index index)]
    [quo/profile-card
     {:name                 name
      :login-card?          true
      :last-item?           (= last-index index)
      :customization-color  (or customization-color (rand-nth (keys colors/customization)))
      :keycard-account?     keycard-pairing
      :show-options-button? true
      :card-style           (style/profiles-profile-card last-item?)
      :on-options-press     #(print "options")
      :on-card-press        #(do
                               (rf/dispatch
                                [:multiaccounts.login.ui/multiaccount-selected key-uid])
                               (reset! show-profiles? false))}]))

(defn profiles-section
  []
  (let [multiaccounts (vals (rf/sub [:multiaccounts/multiaccounts]))
        multiaccounts (map #(assoc % :last-index (- (count multiaccounts) 1)) multiaccounts)]
    [rn/view
     {:style style/profiles-container}
     [rn/view
      {:style style/profiles-header}
      [quo/text
       {:size   :heading-1
        :weight :semi-bold
        :style  style/profiles-header-text}
       (i18n/label :t/profiles-on-device)]
      [quo/button
       {:type           :primary
        :size           32
        :icon           true
        :override-theme :dark}
       :main-icons/add]]
     [rn/flat-list
      {:data      multiaccounts
       :key-fn    :key-uid
       :render-fn profile-card}]]))

(defn login-section
  []
  (let [{:keys [name public-key customization-color
                error processing]
         :as   multiaccount} (rf/sub [:multiaccounts/login])
        password-text-input  (atom nil)
        sign-in-enabled?     (rf/sub [:sign-in-enabled?])]
    [rn/view
     {:style style/login-container}
     [quo/button
      {:size           32
       :type           :blur-bg
       :icon           true
       :on-press       #(do
                          (print @show-profiles?)
                          (reset! show-profiles? true)
                          (print @show-profiles?))
       :override-theme :dark
       :width          32
       :style          style/multi-profile-button}
      :i/multi-profile]
     [quo/profile-card
      {:name                name
       :customization-color (or customization-color :primary)
       :card-style          style/login-profile-card}]
     [quo/input
      {:type                :password
       :variant             :dark-blur
       :placeholder         (i18n/label :t/type-your-password)
       :accessibility-label :password-input
       :auto-focus          true
       :error               (when (not-empty error) error)
       :label               (i18n/label :t/profile-password)
       :secure-text-entry   true
       :on-change-text      (fn [password]
                              (rf/dispatch [:set-in [:multiaccounts/login :password]
                                            (security/mask-data password)])
                              (rf/dispatch [:set-in [:multiaccounts/login :error] ""]))
       :on-submit-editing   (when sign-in-enabled? login-multiaccount)}]
     (when (not-empty error)
       [quo/info-message
        {:type  :error
         :size  :default
         :icon  :i/info
         :style style/info-message}
        (i18n/label :t/ops-wrong-password)])
     [rn/keyboard-avoiding-view
      {:style    style/keyboard-avoiding-view
       :behavior :height}
      [rn/view {:flex 1}]
      [quo/button
       {:size           40
        :type           :ghost
        :before         :i/info
        :override-theme :dark}
       (i18n/label :t/forget-password)]
      [quo/button
       {:size                40
        :type                :primary
        :customization-color (or :primary customization-color)
        :override-theme      :dark
        :before              :i/unlocked
        :disabled            (or (not sign-in-enabled?) processing)
        :on-press            login-multiaccount
        :style               (style/login-button)}
       (i18n/label :t/log-in)]]]))

(defn views
  []
  (reset! show-profiles? false)
  (fn []
    [:<>
     [background/view true]
     (if @show-profiles?
       [profiles-section]
       [login-section show-profiles?])]))
