package com.arkisoftware.tutofast.feedback.query.projections;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackHistoryViewRepository extends JpaRepository<FeedbackHistoryView, String> {
    @Query(value = "SELECT * FROM feedback_history_view WHERE feedback_history_id = (SELECT MAX(feedback_history_id) FROM feedback_history_view WHERE feedback_id = :feedbackId)", nativeQuery = true)
    Optional<FeedbackHistoryView> getLastByFeedbackId(String feedbackId);

    @Query(value = "SELECT * FROM feedback_history_view WHERE feedback_id = :feedbackId ORDER BY created_at", nativeQuery = true)
    List<FeedbackHistoryView> getHistoryByFeedbackId(String feedbackId);
}
