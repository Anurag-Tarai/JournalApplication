package com.anurag.journalapp.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private SendGrid sendGrid;

    public void sendMail(String to, String subject, String body) {
        try {
            // Verified sender
            Email from = new Email("taraianurag2001@gmail.com", "Anurag Tarai - My Project");
            Email recipient = new Email(to);

            // Plain text content (for email clients that do not render HTML)
            Content textContent = new Content("text/plain", subject + "\n\n" + body + "\n\nRegards,\nAnurag Tarai");

            // HTML content
            Content htmlContent = new Content("text/html",
                    "<html>" +
                            "<body>" +
                            "<h3>" + subject + "</h3>" +
                            "<p>" + body + "</p>" +
                            "<p>Regards,<br/>Anurag Tarai</p>" +
                            "</body>" +
                            "</html>"
            );

            // Create Mail object
            Mail mail = new Mail();
            mail.setFrom(from);
            mail.setSubject(subject);
            Personalization personalization = new Personalization();
            personalization.addTo(recipient);
            mail.addPersonalization(personalization);
            mail.addContent(textContent);
            mail.addContent(htmlContent);


            // Reply-To
            mail.setReplyTo(new Email("taraianurag2001@gmail.com", "Anurag Tarai"));

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            log.info("Email sent with status: {}", response.getStatusCode());
            if (response.getStatusCode() >= 400) {
                log.error("Failed to send email. Response: {}", response.getBody());
            }
        } catch (IOException e) {
            log.error("Exception happened during sending mail", e);
        }
    }
}

