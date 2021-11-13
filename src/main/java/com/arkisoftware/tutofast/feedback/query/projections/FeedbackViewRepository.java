package com.arkisoftware.tutofast.feedback.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackViewRepository extends JpaRepository<FeedbackView, String> {

    @Query(value = "SELECT * FROM feedback_view WHERE feedback_id <> :feedbackId", nativeQuery = true)
    Optional<FeedbackView> getByFeedbackId(String feedbackId);
}
