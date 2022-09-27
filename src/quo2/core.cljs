(ns quo2.core
  (:require [quo2.components.avatars.channel_avatar :as channel_avatar]
            [quo2.components.avatars.group_avatar :as group_avatar]
            [quo2.components.avatars.icon_avatar :as icon_avatar]
            [quo2.components.avatars.user_avatar :as user_avatar]
            [quo2.components.avatars.wallet_user_avatar :as wallet_user_avatar]
            [quo2.components.buttons.button :as button]
            [quo2.components.community.community_card_view :as community_card_view]
            [quo2.components.counter.counter :as counter]
            [quo2.components.dividers.divider_label :as divider_label]
            [quo2.components.dividers.new_messages :as new_messages]
            [quo2.components.dropdowns.dropdown :as dropdown]
            [quo2.components.info.info_message :as info_message]
            [quo2.components.info.information_box :as information_box]
            [quo2.components.list_items.channel :as channel]
            [quo2.components.list_items.menu_item :as menu_item]
            [quo2.components.list_items.preview_list :as preview_list]
            [quo2.components.markdown.text :as text]
            [quo2.components.messages.gap :as gap]
            [quo2.components.navigation.bottom_nav_tab :as bottom_nav_tab]
            [quo2.components.notifications.activity_logs :as activity_logs]
            [quo2.components.reactions.reaction :as reaction]
            [quo2.components.switcher.switcher_cards :as switcher_cards]
            [quo2.components.tabs.account_selector :as account_selector]
            [quo2.components.tabs.segmented_tab :as segmented_tab]
            [quo2.components.tabs.tabs :as tabs]
            [quo2.components.tags.context_tags :as context_tags]
            [quo2.components.tags.filter_tag :as filter_tag]
            [quo2.components.tags.filter_tags :as filter_tags]
            [quo2.components.tags.permission_tag :as permission_tag]
            [quo2.components.tags.status_tags :as status_tags]
            [quo2.components.tags.token_tag :as token_tag]
            [quo2.components.wallet.lowest_price :as lowest_price]
            [quo2.components.wallet.token_overview :as token_overview]))

;; Avatars
(def channel_avatar channel_avatar/channel_avatar)
(def group_avatar group_avatar/group_avatar)
(def icon_avatar icon_avatar/icon_avatar)
(def user_avatar user_avatar/user_avatar)
(def wallet_user_avatar wallet_user_avatar/wallet_user_avatar)

;; Buttons
(def button button/button)

;; Community
(def community_card_view community_card_view/community_card_view)

;; Counter
(def counter counter/counter)

;; Dividers
(def divider_label divider_label/divider_label)
(def new_messages new_messages/new_messages)

;; Dropdowns
(def dropdown dropdown/dropdown)

;; Info
(def info_message info_message/info_message)
(def information_box information_box/information_box)

;; List Items
(def channel channel/channel)
(def menu_item menu_item/menu_item)
(def preview_list preview_list/preview_list)

;; Foundations
(def text text/text)

;; Messages
(def gap gap/gap)

;; Navigation
(def bottom_nav_tab bottom_nav_tab/bottom_nav_tab)

;; Notifications
(def activity_logs activity_logs/activity_logs)

;; Reactions
(def reaction reaction/reaction)

;; Switcher
(def switcher_cards switcher_cards/switcher_cards)

;; Tabs
(def account_selector account_selector/account_selector)
(def segmented_tab segmented_tab/segmented_tab)
(def tabs tabs/tabs)

;; Tags
(def context_tags context_tags/context_tags)
(def filter_tag filter_tag/filter_tag)
(def filter_tags filter_tags/filter_tags)
(def permission_tag permission_tag/permission_tag)
(def status_tags status_tags/status_tags)
(def token_tag token_tag/token_tag)

;; Wallet
(def lowest_price lowest_price/lowest_price)
(def token_overview token_overview/token_overview)
