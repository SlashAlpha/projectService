package slash.code.game.config.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public static final String POKER_GAME = "poker-game";
    public static final String BLACKJACK_GAME = "black-jack";
    public final static String NEW_PLAYER = "new-player";
    public final static String BLIND = "blind";
    public final static String POKER_AUTHENTICATION = "poker-authentication";
    public final static String DEALER_AUTHENTICATION = "dealer-authentication";


    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
