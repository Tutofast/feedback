package com.arkisoftware.tutofast.feedback.config;

import com.arkisoftware.tutofast.feedback.command.domain.Feedback;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
    @Bean
    public Repository<Feedback> eventSourcingRepository(EventStore eventStore){
        return EventSourcingRepository.builder(Feedback.class)
                .eventStore(eventStore)
                .build();
    }
}
