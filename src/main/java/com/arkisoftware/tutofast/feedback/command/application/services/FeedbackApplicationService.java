package com.arkisoftware.tutofast.feedback.command.application.services;

import com.arkisoftware.tutofast.feedback.command.application.dtos.request.EditFeedbackRequest;
import com.arkisoftware.tutofast.feedback.command.application.dtos.request.RegisterFeedbackRequest;
import com.arkisoftware.tutofast.feedback.command.application.dtos.response.EditFeebackResponse;
import com.arkisoftware.tutofast.feedback.command.application.dtos.response.RegisterfeedbackResponse;
import com.arkisoftware.tutofast.feedback.command.application.validators.EditFeedbackValidator;
import com.arkisoftware.tutofast.feedback.command.application.validators.RegisterFeedbackValidator;
import com.arkisoftware.tutofast.feedback.contracts.commands.EditFeedback;
import com.arkisoftware.tutofast.feedback.contracts.commands.RegisterFeedback;
import org.arkisoftware.tutofast.common.application.Notification;
import org.arkisoftware.tutofast.common.application.Result;
import org.arkisoftware.tutofast.common.application.ResultType;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class FeedbackApplicationService {
    private final RegisterFeedbackValidator registerFeedbackValidator;
    private final EditFeedbackValidator editFeedbackValidator;
    private final CommandGateway commandGateway;

    public FeedbackApplicationService(RegisterFeedbackValidator registerFeedbackValidator, EditFeedbackValidator editFeedbackValidator, CommandGateway commandGateway) {
        this.registerFeedbackValidator = registerFeedbackValidator;
        this.editFeedbackValidator = editFeedbackValidator;
        this.commandGateway = commandGateway;
    }

    public Result<RegisterfeedbackResponse, Notification> register(RegisterFeedbackRequest registerFeedbackRequest) throws Exception {
        Notification notification = this.registerFeedbackValidator.validate(registerFeedbackRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        String feedbackId = UUID.randomUUID().toString();
        RegisterFeedback registerFeedback = new RegisterFeedback(
                feedbackId,
                registerFeedbackRequest.getDescription().trim(),
                registerFeedbackRequest.getStars().trim()
        );
        CompletableFuture<Object> future = commandGateway.send(registerFeedback);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        RegisterfeedbackResponse registerfeedbackResponse = new RegisterfeedbackResponse(
                registerFeedback.getFeedbackId(),
                registerFeedback.getDescription(),
                registerFeedback.getStars()
        );
        return Result.success(registerfeedbackResponse);
    }

    public Result<EditFeebackResponse, Notification> edit(EditFeedbackRequest editFeedbackRequest) throws Exception {
        Notification notification = this.editFeedbackValidator.validate(editFeedbackRequest);
        if (notification.hasErrors()) {
            return Result.failure(notification);
        }
        EditFeedback editFeedback = new EditFeedback(
                editFeedbackRequest.getFeedbackId().trim(),
                editFeedbackRequest.getDescription().trim(),
                editFeedbackRequest.getStars().trim()
        );
        CompletableFuture<Object> future = commandGateway.send(editFeedback);
        CompletableFuture<ResultType> futureResult = future.handle((ok, ex) -> (ex != null) ? ResultType.FAILURE : ResultType.SUCCESS);
        ResultType resultType = futureResult.get();
        if (resultType == ResultType.FAILURE) {
            throw new Exception();
        }
        EditFeebackResponse editFeebackResponse = new EditFeebackResponse(
                editFeedback.getFeedbackId(),
                editFeedback.getDescription(),
                editFeedback.getStars()
        );
        return Result.success(editFeebackResponse);
    }
}
