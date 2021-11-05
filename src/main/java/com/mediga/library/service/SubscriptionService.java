package com.mediga.library.service;

import com.mediga.library.entity.Subscription;
import com.mediga.library.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    public Subscription subscribe(long userId, long bookId) {
        Subscription subscription = new Subscription();
        subscription.setUserId(userId);
        subscription.setBookId(bookId);
        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> findByBookId(long id) {
        return subscriptionRepository.findAllByBookId(id);
    }

    public void deleteSubscription(Subscription subscription) {
        subscriptionRepository.delete(subscription);
    }

    @Autowired
    public void setSubscriptionRepository(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }
}
