(ns status-im.subs.shell
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :shell/switcher-cards
 :<- [:navigation2/navigation2-stacks]
 (fn [stacks]
   (sort-by :clock > (map val stacks))))

(re-frame/reg-sub
 :shell/one-to-one-card
 (fn [[_ identity] _]
   [(re-frame/subscribe [:contacts/contact-by-identity identity])
    (re-frame/subscribe [:contacts/contact-two-names-by-identity identity])
    (re-frame/subscribe [:chats/chat identity])])
 (fn [[contact names chat] [_ identity]]
   (let [images          (:images contact)
         profile-picture (:uri (or (:thumbnail images)
                                   (:large images)
                                   (first images)))]
   {:title               (first names)
    :avatar-params       {:full-name       (last names)
                          :profile-picture (when profile-picture
                                             (str profile-picture "&addRing=0"))}
    :type                :messaging
    :customization-color (or (:customization-color contact) :primary)
    :on-close            #(re-frame/dispatch [:shell/close-switcher-card identity])
    :on-press            #(re-frame/dispatch [:chat.ui/navigate-to-chat-nav2 identity])
    :content             {:content-type :text :data "Hello"}})))
