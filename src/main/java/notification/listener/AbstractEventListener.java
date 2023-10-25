package notification.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification.dto.UserDto;
import notification.exception.BuilderNotFoundException;
import notification.exception.MessageReadException;
import notification.messaging.MessageBuilder;
import notification.service.NotificationService;
import notification.client.UserServiceClient;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractEventListener <T> implements MessageListener {
    private final ObjectMapper objectMapper;
    protected final UserServiceClient userServiceClient;
    private final List<MessageBuilder<T>> messageBuilders;
    private final List<NotificationService> notificationServices;

    protected T readValue(byte[] json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("Failed to parse json", e);
            throw new MessageReadException(e);
        }
    }

    protected String getMessage(T event, Locale locale) {
        return messageBuilders.stream()
                .filter(builder -> builder.getInstance().equals(event.getClass()))
                .findFirst()
                .map(messageBuilder -> messageBuilder.buildMessage(event, locale))
                .orElseThrow(() -> new BuilderNotFoundException("No message builder found for " + event.getClass().getName()));
    }

    protected void sendNotification(UserDto user, String text) {
        notificationServices.stream()
                .filter(service -> service.getPreferredContact().equals(user.getPreference()))
                .findFirst()
                .ifPresent(service -> service.send(user, text));
    }
}