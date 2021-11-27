package com.arkisoftware.tutofast.feedback.cucumber;

import com.arkisoftware.tutofast.feedback.contracts.commands.RegisterFeedback;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegisterFeedbackSteps {
    @LocalServerPort
    private RestTemplate restTemplate = new RestTemplate();
    private String postUrl = "https://feedback-4widerrmra-uc.a.run.app/swagger-ui/index.html#";
    private String error = null;
    String feedbackId = randomString();
    Long number = randomLong();
    @Given("I can register a Feedback")
    public void i_can_register_a_feedback(){
        String url = postUrl + "/Feedback/";
        String allRegisterFeedback = restTemplate.getForObject(url, String.class);
        log.info(allRegisterFeedback);
        assertTrue(!allRegisterFeedback.isEmpty());
    }
    public String randomString(){
        byte[] array = new byte[8];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return generatedString;
    }

    public Long randomLong(){
        long generatedLong = new Random().nextLong();
        return generatedLong;
    }

    @Given("I register a repeated feedbackId")
    public void register_a_repeated_feedback(){
        String url = postUrl + "/Feedback/";
        RegisterFeedback newRegisterFeedback = new RegisterFeedback(feedbackId, "description", "stars");
        log.info(newRegisterFeedback);
        try
        {
            RegisterFeedback registerFeedback = restTemplate.postForObject(url, newRegisterFeedback, RegisterFeedback.class);
            log.info(registerFeedback);
        }catch(RestClientException e){
            error = "El numero de feedback no esta disponible";
        }
        assertEquals(error, "El numero de feedback no esta disponible");
    }
    @Then("I should see a error message {string}")
    public void i_should_see_a_error_message(String string) {assertEquals(string, error);}

    @Given("I register a number of stars")
    public void i_register_a_number_of_stars(){
        String url = postUrl + "/Feedback/";
        RegisterFeedback newRegisterFeedback = new RegisterFeedback(feedbackId, "description", "stars");
        log.info(newRegisterFeedback);
        try
        {
            RegisterFeedback registerFeedback = restTemplate.postForObject(url, newRegisterFeedback, RegisterFeedback.class);
            log.info(registerFeedback);
        }catch(RestClientException e){
            error = "Error en el numero de estrellas registradas";
        }
        assertEquals(error, "Error en el numero de estrellas registradas");
    }
}
