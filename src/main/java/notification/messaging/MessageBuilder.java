package notification.messaging;

import java.util.Locale;

public interface MessageBuilder <T> {
    Class<T> getInstanceClass();

    String buildMessage(T event, Locale locale);
}