package com.instagram.clone.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailService {
    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    private Map<String, String> verificationCodes = new HashMap<>();

    public void sendVerificationCode(String email) {
        String code = generateRandomCode();
        verificationCodes.put(email, code);

        Email from = new Email("clonealceuinsta@bol.com.br");
        String subject = "Código de Verificação";
        Email to = new Email(email);
        Content content = new Content("text/plain", "Seu código de verificação é: " + code);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
        } catch (IOException ex) {
            throw new RuntimeException("Error sending email", ex);
        }
    }

    public boolean verifyCode(String email, String code) {
        return code.equals(verificationCodes.get(email));
    }

    public void removeVerificationCode(String email) {
        verificationCodes.remove(email);
    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
