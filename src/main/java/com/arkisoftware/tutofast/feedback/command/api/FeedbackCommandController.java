package com.arkisoftware.tutofast.feedback.command.api;

import com.arkisoftware.tutofast.feedback.api.ApiController;
import com.arkisoftware.tutofast.feedback.application.Notification;
import com.arkisoftware.tutofast.feedback.application.Result;
import com.arkisoftware.tutofast.feedback.command.application.dtos.request.EditFeedbackRequest;
import com.arkisoftware.tutofast.feedback.command.application.dtos.request.RegisterFeedbackRequest;
import com.arkisoftware.tutofast.feedback.command.application.dtos.response.EditFeebackResponse;
import com.arkisoftware.tutofast.feedback.command.application.dtos.response.RegisterfeedbackResponse;
import com.arkisoftware.tutofast.feedback.command.application.services.FeedbackApplicationService;
import io.swagger.annotations.Api;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@Api(tags = "Feedback")
public class FeedbackCommandController {
    private final FeedbackApplicationService feedbackApplicationService;
    private final CommandGateway commandGateway;

    public FeedbackCommandController(FeedbackApplicationService feedbackApplicationService, CommandGateway commandGateway) {
        this.feedbackApplicationService = feedbackApplicationService;
        this.commandGateway = commandGateway;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@RequestBody RegisterFeedbackRequest registerFeedbackRequest) {
        try {
            Result<RegisterfeedbackResponse, Notification> result = feedbackApplicationService.register(registerFeedbackRequest);
            if (result.isSuccess()) {
                return ApiController.created(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<Object> edit(@PathVariable("feedbackId") String feedbackId, @RequestBody EditFeedbackRequest editFeedbackRequest) {
        try {
            editFeedbackRequest.setFeedbackId(feedbackId);
            Result<EditFeebackResponse, Notification> result = feedbackApplicationService.edit(editFeedbackRequest);
            if (result.isSuccess()) {
                return ApiController.ok(result.getSuccess());
            }
            return ApiController.error(result.getFailure().getErrors());
        } catch (AggregateNotFoundException exception) {
            return ApiController.notFound();
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }
}
