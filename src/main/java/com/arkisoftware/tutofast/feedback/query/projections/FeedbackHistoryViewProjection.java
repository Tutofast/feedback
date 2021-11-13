package com.arkisoftware.tutofast.feedback.query.projections;

import com.arkisoftware.tutofast.feedback.contracts.events.*;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class FeedbackHistoryViewProjection {
    private final FeedbackHistoryViewRepository feedbackHistoryViewRepository;

    public FeedbackHistoryViewProjection(FeedbackHistoryViewRepository feedbackHistoryViewRepository) {
        this.feedbackHistoryViewRepository = feedbackHistoryViewRepository;
    }

    @EventHandler
    public void on(FeedbackRegistered event, @Timestamp Instant timestamp) {
        FeedbackHistoryView feedbackHistoryView = new FeedbackHistoryView(event.getFeedbackId(), event.getDescription(), event.getStars(), event.getOccurredOn());
        feedbackHistoryViewRepository.save(feedbackHistoryView);
    }

    @EventHandler
    public void on(FeedbackEdited event, @Timestamp Instant timestamp) {
        Optional<FeedbackHistoryView> feedbackHistoryViewOptional = feedbackHistoryViewRepository.getLastByFeedbackId(event.getFeedbackId());
        if(feedbackHistoryViewOptional.isPresent()) {
            FeedbackHistoryView feedbackHistoryView = feedbackHistoryViewOptional.get();
            feedbackHistoryView = new FeedbackHistoryView(feedbackHistoryView);
            feedbackHistoryView.setDescription(event.getDescription());
            feedbackHistoryView.setStars(event.getStars());
            feedbackHistoryView.setCreatedAt(event.getOccurredOn());
            feedbackHistoryViewRepository.save(feedbackHistoryView);
        }
    }
}
