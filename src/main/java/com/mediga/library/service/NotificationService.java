package com.mediga.library.service;

import com.mediga.library.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private SubscriptionService subscriptionService;

    public void notifySubscribedUsers(long bookId) {
        List<Subscription> subscriptions = subscriptionService.findByBookId(bookId);
        if(subscriptions != null) {
            subscriptions.stream().forEach((subscription) -> {
                System.out.println("Notified user :  " + subscription.getUser().getName());
                subscriptionService.deleteSubscription(subscription);
            });
        }
    }

    @Autowired
    public void setSubscriptionService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
}
