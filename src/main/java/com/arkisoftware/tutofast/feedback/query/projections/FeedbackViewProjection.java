package com.arkisoftware.tutofast.feedback.query.projections;

import com.arkisoftware.tutofast.feedback.contracts.events.FeedbackEdited;
import com.arkisoftware.tutofast.feedback.contracts.events.FeedbackRegistered;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class FeedbackViewProjection {
    private final FeedbackViewRepository feedbackViewRepository;

    public FeedbackViewProjection(FeedbackViewRepository feedbackViewRepository) {
        this.feedbackViewRepository = feedbackViewRepository;
    }

    @EventHandler
    public void on(FeedbackRegistered event, @Timestamp Instant timestamp) {
        FeedbackView feedbackView = new FeedbackView(event.getFeedbackId(), event.getDescription(), event.getStars(), event.getOccurredOn());
        feedbackViewRepository.save(feedbackView);
    }

    @EventHandler
    public void on(FeedbackEdited event, @Timestamp Instant timestamp) {
        Optional<FeedbackView> feedbackViewOptional = feedbackViewRepository.findById(event.getFeedbackId().toString());
        if (feedbackViewOptional.isPresent()) {
            FeedbackView feedbackView = feedbackViewOptional.get();
            feedbackView.setDescription(event.getDescription());
            feedbackView.setStars(event.getStars());
            feedbackView.setUpdatedAt(event.getOccurredOn());
            feedbackViewRepository.save(feedbackView);
        }
    }
}
