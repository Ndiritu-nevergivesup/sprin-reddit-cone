package com.ndirituedwin.Service;

import com.ndirituedwin.Exceptions.SpringRedditException;
import com.ndirituedwin.Model.NotificationEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    public MailService(JavaMailSender javaMailSender, MailContentBuilder mailContentBuilder) {
        this.javaMailSender = javaMailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    @Async
    public void sendMail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparator=mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("ndiritu.edwin018@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {

             javaMailSender.send(messagePreparator);
             log.info("Activation email sent to ",notificationEmail.getRecipient()," !");
        }catch (Exception e){
            throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient()+" " +e.getMessage());        }

    }
}
