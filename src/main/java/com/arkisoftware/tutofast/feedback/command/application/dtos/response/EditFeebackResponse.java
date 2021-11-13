package com.arkisoftware.tutofast.feedback.command.application.dtos.response;

import lombok.Value;

@Value
public class EditFeebackResponse {
    private String feedbackId;
    private String description;
    private String stars;
}
