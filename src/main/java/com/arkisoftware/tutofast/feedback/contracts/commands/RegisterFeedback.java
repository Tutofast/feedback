package com.arkisoftware.tutofast.feedback.contracts.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class RegisterFeedback {
    @TargetAggregateIdentifier
    String feedbackId;
    String description;
    String stars;
}