package notification.config;

import notification.listener.SkillOfferedEventListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    @Value("${spring.data.redis.channels.skill_channel.name}")
    private String skillOfferedChannel;

    @Bean(name = "skillOfferedListenerAdapter")
    public MessageListenerAdapter skillOfferedMessageListener(SkillOfferedEventListener skillOfferedEventListener) {
        return new MessageListenerAdapter(skillOfferedEventListener);
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        log.info("Crated redis connection factory with host: {}, port: {}", host, port);
        return new JedisConnectionFactory();
    }

    @Bean
    ChannelTopic skillOfferedTopic() {
        return new ChannelTopic(skillOfferedChannel);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(
            @Qualifier("skillOfferedListenerAdapter") MessageListenerAdapter skillOfferedEventListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());

        container.addMessageListener(skillOfferedEventListener, skillOfferedTopic());
        return container;
    }
}