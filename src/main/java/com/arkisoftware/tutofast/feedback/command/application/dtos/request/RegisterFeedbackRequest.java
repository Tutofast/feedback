package com.arkisoftware.tutofast.feedback.command.application.dtos.request;

import lombok.Value;

@Value
public class RegisterFeedbackRequest {
    private String description;
    private String stars;
}
