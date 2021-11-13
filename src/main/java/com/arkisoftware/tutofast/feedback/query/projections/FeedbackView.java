package com.arkisoftware.tutofast.feedback.query.projections;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class FeedbackView {
    @Id @Column(length = 36) @Getter @Setter
    private String feedbackId;
    @Column(length = 1052) @Getter @Setter
    private String description;
    @Column(length = 1) @Getter @Setter
    private String stars;
    private Instant createdAt;
    @Column(nullable = true) @Getter @Setter
    private Instant updatedAt;

    public FeedbackView () {}

    public FeedbackView(String feedbackId, String description, String stars, Instant createdAt) {
        this.feedbackId = feedbackId;
        this.description = description;
        this.stars = stars;
        this.createdAt = createdAt;
    }
}
