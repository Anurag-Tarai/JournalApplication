package com.anurag.journalapp.controller;

import com.anurag.journalapp.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = "*") // optional: allows requests from frontend if needed
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * Send a plain text email.
     * Example:
     * POST /api/mail/text?to=user@example.com&subject=Hello&body=Welcome
     */
    @PostMapping("/text")
    public ResponseEntity<String> sendTextMail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body) {
        mailService.sendTextMail(to, subject, body);
        return ResponseEntity.ok("✅ Text email sent successfully to: " + to);
    }

    /**
     * Send an HTML email (formatted or styled content).
     * Example:
     * POST /api/mail/html?to=user@example.com&subject=Test
     * Body (raw HTML) in request body.
     */
    @PostMapping(value = "/html", consumes = "text/html")
    public ResponseEntity<String> sendHtmlMail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestBody String html) throws MessagingException {
        mailService.sendHtmlMail(to, subject, html);
        return ResponseEntity.ok("✅ HTML email sent successfully to: " + to);
    }

    /**
     * Send an email verification mail.
     * Example:
     * POST /api/mail/verify?to=user@example.com&token=abc123
     */
    @PostMapping("/verify")
    public ResponseEntity<String> sendVerificationMail(
            @RequestParam String to,
            @RequestParam String token,
            @RequestParam String domain // 👈 New: domain passed dynamically
    ) throws MessagingException {
        mailService.sendVerificationMail(to, token, domain);
        return ResponseEntity.ok("✅ Verification email sent successfully to: " + to);
    }


}
