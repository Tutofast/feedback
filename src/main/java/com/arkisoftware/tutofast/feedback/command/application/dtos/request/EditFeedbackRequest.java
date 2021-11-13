package com.arkisoftware.tutofast.feedback.command.application.dtos.request;

import lombok.Getter;
import lombok.Setter;

public class EditFeedbackRequest {
    private @Setter @Getter String feedbackId;
    private @Getter String description;
    private @Getter String stars;
}
