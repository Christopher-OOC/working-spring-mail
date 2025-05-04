package com.example.test_app.controller;

import com.example.test_app.service.EmailService;
import com.example.test_app.service.HtmlEmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/mail")
public class EmailSendController {

    private EmailService emailService;
    private HtmlEmailService htmlEmailService;

    public  EmailSendController(
            EmailService emailService,
            HtmlEmailService htmlEmailService
    ) {
        this.emailService = emailService;
        this.htmlEmailService = htmlEmailService;
    }

    @PostMapping(value = "/send")
    public String sendMail(
            @RequestParam(value = "file", required = false) MultipartFile[] file,
            String to,
            String[] cc,
            String subject,
            String body
            ) {
        htmlEmailService.sendHtmlEmail();
        return emailService.sendMail(file, to, cc, subject, body);
    }

    @PostMapping(value = "/send-html")
    public String sendMail(
    ) {
        htmlEmailService.sendHtmlEmail();
        return "Email sent!";
    }

}
