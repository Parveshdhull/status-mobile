(ns status-im2.subs.shell
  (:require [re-frame.core :as re-frame]
            [status-im2.contexts.shell.core :as shell]))

(re-frame/reg-sub
 :shell/sorted-switcher-cards
 :<- [:shell/switcher-cards]
 (fn [stacks]
   (sort-by :clock > (map val stacks))))

(re-frame/reg-sub
 :shell/one-to-one-chat-card
 (fn [[_ id] _]
   [(re-frame/subscribe [:contacts/contact-by-identity id])
    (re-frame/subscribe [:contacts/contact-two-names-by-identity id])
    (re-frame/subscribe [:chats/chat id])])
 (fn [[contact names chat] [_ id]]
   (shell/one-to-one-chat-card contact names chat id)))

(re-frame/reg-sub
 :shell/private-group-chat-card
 (fn [[_ id] _]
   [(re-frame/subscribe [:chats/chat id])])
 (fn [[chat] [_ id]]
   (shell/private-group-chat-card chat id)))

(re-frame/reg-sub
 :shell/community-card
 (fn [[_ id] _]
   [(re-frame/subscribe [:communities/community id])])
 (fn [[community] [_ id]]
   (shell/community-card community id nil)))

(re-frame/reg-sub
 :shell/community-channel-card
 (fn [[_ community-id channel-id _] _]
   [(re-frame/subscribe [:communities/community community-id])
    (re-frame/subscribe [:chats/chat channel-id])])
 (fn [[community channel] [_ community-id channel-id content]]
   (shell/community-channel-card community community-id channel channel-id content)))
