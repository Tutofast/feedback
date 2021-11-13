package com.arkisoftware.tutofast.feedback.command.application.validators;

import com.arkisoftware.tutofast.feedback.application.Notification;
import com.arkisoftware.tutofast.feedback.command.application.dtos.request.EditFeedbackRequest;
import com.arkisoftware.tutofast.feedback.command.domain.Feedback;
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.modelling.command.Repository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EditFeedbackValidator {
    private final Repository<Feedback> feedbackRepository;

    public EditFeedbackValidator(Repository<Feedback> feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Notification validate(EditFeedbackRequest editFeedbackRequest) {
        Notification notification = new Notification();
        String feedbackId = editFeedbackRequest.getFeedbackId().trim();
        String description = editFeedbackRequest.getDescription().trim();
        String stars = editFeedbackRequest.getStars().trim();
        if (feedbackId.isEmpty()) {
            notification.addError("Feedback ID is required");
        }
        loadCustomAggregate(feedbackId);
        if (description.isEmpty()) {
            notification.addError("Description is required");
        }
        if (stars.isEmpty()) {
            notification.addError("Stars are required");
        }
        return notification;
    }

    private void loadCustomAggregate(String feedbackId) {
        UnitOfWork unitOfWork = null;
        try {
            unitOfWork = DefaultUnitOfWork.startAndGet(null);
            feedbackRepository.load(feedbackId);
            unitOfWork.commit();
        } catch (AggregateNotFoundException ex) {
            unitOfWork.commit();
            throw ex;
        } catch (Exception ex) {
            if (unitOfWork != null) unitOfWork.rollback();
        }
    }
}
