package com.anurag.journalapp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final String fromEmail;

    public MailService(JavaMailSender mailSender,
                       @Value("${mail.from}") String fromEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    /**
     * Sends a simple plain text email.
     */
    public void sendTextMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    /**
     * Sends an HTML email (for verification, templates, etc.)
     */
    public void sendHtmlMail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }
    /**
     * Sends a standard email verification message.
     */
    public void sendVerificationMail(String to, String token, String domain) throws MessagingException {
        // Ensure the domain doesn’t accidentally have trailing slashes or missing protocol
        String cleanDomain = domain.trim();
        if (!cleanDomain.startsWith("http")) {
            cleanDomain = "https://" + cleanDomain;
        }

        String verificationUrl = cleanDomain + "/verify?token=" + token;

        String html = """
            <div style="font-family: Arial, sans-serif; line-height: 1.6; background: #f9f9f9; padding: 20px;">
                <div style="max-width: 600px; background: #fff; border-radius: 8px; padding: 20px; margin: auto;">
                    <h2 style="color: #333;">Email Verification</h2>
                    <p>Hello 👋,</p>
                    <p>Thank you for registering with us. Please verify your email by clicking the button below:</p>
                    <p style="text-align: center; margin: 20px 0;">
                        <a href="%s"
                           style="background-color: #4CAF50; color: white; padding: 12px 25px; text-decoration: none; border-radius: 6px; font-weight: bold;">
                           Verify Email
                        </a>
                    </p>
                    <p>If you didn’t request this, you can safely ignore this message.</p>
                    <p style="color: #555;">— The Journal Team</p>
                </div>
            </div>
            """.formatted(verificationUrl);

        sendHtmlMail(to, "Verify your email address", html);
    }
}
