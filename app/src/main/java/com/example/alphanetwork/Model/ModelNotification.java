package com.example.alphanetwork.Model;

import com.example.alphanetwork.Notification.Notification;

import java.util.List;

public class ModelNotification {
    private List<ModelNotificationFeed> notifications;

    public List<ModelNotificationFeed> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<ModelNotificationFeed> notifications) {
        this.notifications = notifications;
    }
}

