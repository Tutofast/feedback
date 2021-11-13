package com.arkisoftware.tutofast.feedback.command.application.validators;

import com.arkisoftware.tutofast.feedback.application.Notification;
import com.arkisoftware.tutofast.feedback.command.application.dtos.request.RegisterFeedbackRequest;
import org.springframework.stereotype.Component;

@Component
public class RegisterFeedbackValidator {
    public Notification validate(RegisterFeedbackRequest registerFeedbackRequest) {
        Notification notification = new Notification();
        String description = registerFeedbackRequest.getDescription().trim();
        String stars = registerFeedbackRequest.getStars().trim();

        if (description.isEmpty()) {
            notification.addError("Description is required");
        }
        if (stars.isEmpty()) {
            notification.addError("Stars are required");
        }
        if (notification.hasErrors()) {
            return notification;
        }
        return notification;
    }
}
