package ru.geekbrains.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.logging.Logger;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration  implements WebSocketMessageBrokerConfigurer {

    private Logger logger = Logger.getLogger(String.valueOf(WebSocketConfiguration.class));

    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/cart", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        logger.info("MessageBroker config");
    }

    public void registerStompEndpoints(StompEndpointRegistry register){
        register.addEndpoint("/cart-messaging").addInterceptors(new HttpHandShakeInterceptor()).withSockJS();
        logger.info("StompEndpoints registry");
    }
    private static class HttpHandShakeInterceptor implements HandshakeInterceptor{


        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
                                       Map<String, Object> map) throws Exception {
            if(request instanceof ServletServerHttpRequest){
                ServletServerHttpRequest sRequest = (ServletServerHttpRequest) request;
                HttpSession session = sRequest.getServletRequest().getSession();
                map.put("HTTP_SESSION", session);
                map.put("sessionId", session.getId());
            }
            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Exception e) {

        }
    }
}
