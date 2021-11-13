package com.arkisoftware.tutofast.feedback.query.projections;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class FeedbackHistoryView {
    @Id @GeneratedValue @Getter @Setter
    private Long feedbackHistoryId;
    @Column(length = 36) @Getter @Setter
    private String feedbackId;
    @Column(length = 1052) @Getter @Setter
    private String description;
    @Column(length = 1) @Getter @Setter
    private String stars;
    @Getter @Setter
    private Instant createdAt;

    public FeedbackHistoryView () {}

    public FeedbackHistoryView(String feedbackId, String description, String stars, Instant createdAt) {
        this.feedbackId = feedbackId;
        this.description = description;
        this.stars = stars;
        this.createdAt = createdAt;
    }

    public FeedbackHistoryView(FeedbackHistoryView feedbackHistoryView) {
        this.feedbackId = feedbackHistoryView.getFeedbackId();
        this.description = feedbackHistoryView.getDescription();
        this.stars = feedbackHistoryView.getStars();
        this.createdAt = feedbackHistoryView.getCreatedAt();
    }
}
