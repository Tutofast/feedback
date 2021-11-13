package com.arkisoftware.tutofast.feedback.query.api;

import com.arkisoftware.tutofast.feedback.query.projections.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.arkisoftware.tutofast.common.api.ApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/feedback")
@Api(tags = "Feedback")
public class FeedbackQueryController {
    private final FeedbackViewRepository feedbackViewRepository;
    private final FeedbackHistoryViewRepository feedbackHistoryViewRepository;

    public FeedbackQueryController(FeedbackViewRepository feedbackViewRepository, FeedbackHistoryViewRepository feedbackHistoryViewRepository) {
        this.feedbackViewRepository = feedbackViewRepository;
        this.feedbackHistoryViewRepository = feedbackHistoryViewRepository;
    }

    @GetMapping("")
    @ApiOperation(value = "Get all feedbacks", response = List.class)
    public ResponseEntity<Object> getAll() {
        try {
            return ApiController.ok(feedbackViewRepository.findAll());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get feedback by id", response = FeedbackView.class)
    public ResponseEntity<Object> getById(@PathVariable("id") String id) {
        try {
            Optional<FeedbackView> feedbackViewOptional = feedbackViewRepository.findById(id);
            if (feedbackViewOptional.isPresent()) {
                return ApiController.ok(feedbackViewOptional.get());
            }
            return ApiController.notFound();
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }

    @GetMapping("/history/{id}")
    @ApiOperation(value = "Get feedback history", response = List.class)
    public ResponseEntity<Object> getHistorybyId(@PathVariable("id") String id){
        try {
            List<FeedbackHistoryView> feedbacks = feedbackHistoryViewRepository.getHistoryByFeedbackId(id);
            return ApiController.ok(feedbacks);
        } catch (Exception e) {
            return ApiController.serverError();
        }
    }

}
