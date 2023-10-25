package notification.messaging;

import lombok.RequiredArgsConstructor;
import notification.dto.SkillOfferDto;
import notification.client.UserServiceClient;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class SkillOfferedMessageBuilder implements MessageBuilder<SkillOfferDto> {
    private final MessageSource messageSource;
    private final UserServiceClient userServiceClient;

    @Override
    public Class<SkillOfferDto> getInstanceClass() {
        return SkillOfferDto.class;
    }

    @Override
    public String buildMessage(SkillOfferDto event, Locale locale) {
        return messageSource.getMessage("skill.offered.message",
                new Object[]{userServiceClient.getUser(event.getAuthorId()).getUsername(), event.getSkill()}, locale
        );
    }
}