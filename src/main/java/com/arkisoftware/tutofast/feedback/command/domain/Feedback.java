package com.arkisoftware.tutofast.feedback.command.domain;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import com.arkisoftware.tutofast.feedback.contracts.commands.EditFeedback;
import com.arkisoftware.tutofast.feedback.contracts.commands.RegisterFeedback;
import com.arkisoftware.tutofast.feedback.contracts.events.FeedbackEdited;
import com.arkisoftware.tutofast.feedback.contracts.events.FeedbackRegistered;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;

@Aggregate
public class Feedback {
    @AggregateIdentifier
    private String feedbackId;
    private String description;
    private String stars;

    public Feedback() {}

    @CommandHandler
    public Feedback(RegisterFeedback command) {
        Instant now = Instant.now();
        apply(
                new FeedbackRegistered(
                        command.getFeedbackId(),
                        command.getDescription(),
                        command.getStars(),
                        now
                )
        );
    }

    @CommandHandler
    public void handle(EditFeedback command) {
        Instant now = Instant.now();
        apply(
                new FeedbackEdited(
                        command.getFeedbackId(),
                        command.getDescription(),
                        command.getStars(),
                        now
                )
        );
    }

    @EventSourcingHandler
    protected void on(FeedbackRegistered event) {
        feedbackId = event.getFeedbackId();
        description = event.getDescription();
        stars = event.getStars();
    }

    @EventSourcingHandler
    protected void on(FeedbackEdited event) {
        description = event.getDescription();
        stars = event.getStars();
    }
}
