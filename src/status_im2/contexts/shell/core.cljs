(ns status-im2.contexts.shell.core
  (:require [utils.re-frame :as rf]
            [re-frame.core :as re-frame]
            [status-im.constants :as status-constants]
            [status-im2.navigation.events :as navigation]
            [status-im.react-native.resources :as resources]
            [status-im2.contexts.shell.animation :as animation]
            [status-im2.contexts.shell.constants :as constants]))

;; Effects

(re-frame/reg-fx
 :shell/navigate-to-jump-to-fx
 (fn []
   (animation/close-home-stack false)))

(re-frame/reg-fx
 :shell/navigate-from-shell-fx
 (fn [stack-id]
   (js/setTimeout #(animation/bottom-tab-on-press stack-id) 500)))

(re-frame/reg-fx
 :shell/reset-bottom-tabs
 (fn []
   (let [selected-stack-id @animation/selected-stack-id]
     (reset! animation/load-communities-stack? (= selected-stack-id :communities-stack))
     (reset! animation/load-chats-stack? (= selected-stack-id :chats-stack))
     (reset! animation/load-wallet-stack? (= selected-stack-id :wallet-stack))
     (reset! animation/load-browser-stack? (= selected-stack-id :browser-stack)))))

;; Helper Functions

(defn get-card-content [chat]
  (let [last-message (:last-message chat)]
    (case (:content-type last-message)
      status-constants/content-type-text
      {:content-type :text
       :data         (get-in last-message [:content :text])}

      {:content-type :text
       :data         "Todo: Implement"})))

(defn one-to-one-chat-card [contact names chat id]
  (let [images          (:images contact)
        profile-picture (:uri (or (:thumbnail images) (:large images) (first images)))]
    {:title               (first names)
     :avatar-params       {:full-name       (last names)
                           :profile-picture (when profile-picture
                                              (str profile-picture "&addRing=0"))}
     :customization-color (or (:customization-color contact) :primary)
     :on-close            #(re-frame/dispatch [:shell/close-switcher-card id])
     :on-press            #(re-frame/dispatch [:chat.ui/navigate-to-chat-nav2 id true])
     :content             (get-card-content chat)}))

(defn private-group-chat-card [chat id]
  {:title               (:chat-name chat)
   :avatar-params       {}
   :customization-color (or (:customization-color chat) :primary)
   :on-close            #(re-frame/dispatch [:shell/close-switcher-card id])
   :on-press            #(re-frame/dispatch [:chat.ui/navigate-to-chat-nav2 id true])
   :content             (get-card-content chat)})

(defn community-card [community id content]
  (let [images          (:images community)
        profile-picture (if (= id status-constants/status-community-id)
                          (resources/get-image :status-logo)
                          (when images
                            {:uri (:uri (or (:thumbnail images)
                                            (:large images)
                                            (first images)))}))]
    {:title               (:name community)
     :avatar-params       (if profile-picture
                            {:source profile-picture}
                            {:name (:name community)})
     :customization-color (or (:customization-color community) :primary)
     :on-close            #(re-frame/dispatch [:shell/close-switcher-card id])
     :on-press            #(re-frame/dispatch [:navigate-to-nav2 :community
                                               {:community-id id} true])
     :content             (or content {:content-type :community-info
                                       :data         {:type :permission}})}))

(defn community-channel-card [community community-id _ channel-id content]
  (merge
   (community-card community community-id content)
   {:on-press (fn []
                (re-frame/dispatch [:navigate-to :community {:community-id community-id}])
                (js/setTimeout
                 #(re-frame/dispatch [:chat.ui/navigate-to-chat-nav2 channel-id true])
                 100))}))

;; Events

(rf/defn add-switcher-card
  {:events [:shell/add-switcher-card]}
  [{:keys [db now] :as cofx} view-id id]
  (case view-id
    :chat
    (let [chat (get-in db [:chats id])]
      (case (:chat-type chat)
        status-constants/one-to-one-chat-type
        {:shell/navigate-from-shell-fx :chats-stack
         :db                           (assoc-in
                                        db [:shell/switcher-cards id]
                                        {:type  constants/one-to-one-chat-card
                                         :id    id
                                         :clock now})}

        status-constants/private-group-chat-type
        {:shell/navigate-from-shell-fx :chats-stack
         :db                           (assoc-in
                                        db [:shell/switcher-cards id]
                                        {:type  constants/private-group-chat-card
                                         :id    id
                                         :clock now})}

        status-constants/community-chat-type
        {:shell/navigate-from-shell-fx :communities-stack
         :db                           (assoc-in
                                        db [:shell/switcher-cards (:community-id chat)]
                                        {:type    constants/community-card
                                         :id      (:community-id chat)
                                         :clock   now
                                         :content {:content-type :channel
                                                   :data         {:emoji        (:emoji chat)
                                                                  :channel-id   (:chat-id chat)
                                                                  :channel-name (:chat-name chat)}}})}

        nil))

    :community
    {:shell/navigate-from-shell-fx :communities-stack
     :db                           (assoc-in
                                    db [:shell/switcher-cards (:community-id id)]
                                    {:type  constants/community-card
                                     :id    (:community-id id)
                                     :clock now})}

    nil))

(rf/defn close-switcher-card
  {:events [:shell/close-switcher-card]}
  [{:keys [db]} id]
  {:db (update-in db [:shell/switcher-cards] dissoc id)})

(rf/defn navigate-to-jump-to
  {:events [:shell/navigate-to-jump-to]}
  [cofx]
  (rf/merge
   cofx
   {:shell/navigate-to-jump-to-fx nil}
   (navigation/pop-to-root-tab :shell-stack)))
