package com.example.test_app.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HtmlEmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    SpringTemplateEngine templateEngine;

    @Async
    public void sendHtmlEmail() {
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "Olojede Christopher");
            attributes.put("siblings", List.of(
                    "Olojede Timothy",
                    "Olojede Christiana",
                    "Olojede Richard"
            ));

            Context context = new Context();
            context.setVariables(attributes);

            String templateName = "email.html";

            String processedHtml = templateEngine.process(templateName, context);

            mimeMessageHelper.setText(processedHtml, true);
            mimeMessageHelper.setSubject("Account created Successfully");
            mimeMessageHelper.setFrom("olojedechristopher24@gmail.com");
            mimeMessageHelper.setTo("drealcrystal24@gmail.com");

            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException ex) {
            System.out.println("Could not send message!");
        }
        catch (Exception ex) {
            System.out.println("Error sending message!");
        }
    }
}
