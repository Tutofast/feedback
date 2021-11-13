package com.arkisoftware.tutofast.feedback.contracts.events;

import lombok.Value;

import java.time.Instant;

@Value
public class FeedbackRegistered {
    String feedbackId;
    String description;
    String stars;
    Instant occurredOn;
}
