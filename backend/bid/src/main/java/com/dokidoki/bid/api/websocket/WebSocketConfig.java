package com.dokidoki.bid.api.websocket;

import com.dokidoki.bid.api.websocket.handler.LeaderBoardSocketHandler;
import com.dokidoki.bid.api.websocket.interceptor.LeaderboardInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public HandshakeInterceptor leaderboardInterceptor() {
        return new LeaderboardInterceptor();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new LeaderBoardSocketHandler(), "/auctions/*/leaderboard")
                .setAllowedOrigins("*")
                .addInterceptors(leaderboardInterceptor());

    }

}
