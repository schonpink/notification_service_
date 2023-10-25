package notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification.dto.UserDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements NotificationService {

    private final JavaMailSender mailSender;

    @Override
    public UserDto.PreferredContact getPreferredContact() {
        return UserDto.PreferredContact.EMAIL;
    }

    @Override
    public void send(UserDto user, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setText(text);
        simpleMailMessage.setSubject("CompanyXXX notification");
        log.info("Sending email: " + text + " to " + user.getEmail());
        mailSender.send(simpleMailMessage);
        log.info("Email sent successfully to " + user.getEmail());
    }
}