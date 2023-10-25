package notification.service;

import notification.dto.UserDto;

public interface NotificationService {
    UserDto.PreferredContact getPreferredContact();

    void send(UserDto user, String text);
}