package com.mediga.library.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Subscription {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime subscriptionTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getSubscriptionTime() {
        return subscriptionTime;
    }

    public void setSubscriptionTime(LocalDateTime subscriptionTime) {
        this.subscriptionTime = subscriptionTime;
    }
}
