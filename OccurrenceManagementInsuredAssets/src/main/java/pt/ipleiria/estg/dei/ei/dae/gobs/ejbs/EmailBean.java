package pt.ipleiria.estg.dei.ei.dae.gobs.ejbs;

import pt.ipleiria.estg.dei.ei.dae.gobs.dtos.EmailDTO;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class EmailBean {
    @Inject
    private Logger logger;
    @Resource(name = "java:/jboss/mail/fakeSMTP")
    private Session mailSession;

    public void send(String to, EmailDTO dto) {
        Thread emailThread = new Thread(() -> {
            Message message = new MimeMessage(mailSession);
            try {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
                message.setSubject(dto.getSubject());
                message.setText(dto.getMessage());

                Date timeStamp = new Date();
                message.setSentDate(timeStamp);

                Transport.send(message);
            } catch (MessagingException ex) {
                logger.log(Level.SEVERE, "Fail to process email request!", ex);
            }
        });
        emailThread.start();

    }
}
