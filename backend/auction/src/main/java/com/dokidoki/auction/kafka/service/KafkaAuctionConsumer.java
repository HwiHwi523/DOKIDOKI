package com.dokidoki.auction.kafka.service;

import com.dokidoki.auction.kafka.dto.KafkaAuctionEndDTO;
import com.dokidoki.auction.kafka.dto.KafkaBidDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaAuctionConsumer {

    @KafkaListener(topics = "${spring.kafka.auctionEndConfig.topic}", containerFactory = "auctionEndKafkaListenerContainerFactory")
    public void auctionEndListener(
            @Payload KafkaAuctionEndDTO auction,
            @Headers MessageHeaders headers) {
        System.out.println("Received auction end message : " + auction);
        System.out.print("header | ");
        headers.keySet().forEach(key -> {
//            log.debug("header | key: [{}] value: [{}]", key, headers.get(key));
            System.out.print("{" + key + ", " + headers.get(key) + "} ");
        });
        System.out.println("");

        // auction : memberid, name, email .....
        // websocket method_bid_success(memberid, name, email)

    }

    @KafkaListener(topics = "${spring.kafka.bidConfig.topic}", containerFactory = "bidKafkaListenerContainerFactory")
    public void bidListener(
            @Payload KafkaBidDTO bid,
            @Headers MessageHeaders headers) {
        System.out.println("Received bid message : " + bid);
        System.out.print("header | ");
        headers.keySet().forEach(key -> {
//            log.debug("header | key: [{}] value: [{}]", key, headers.get(key));
            System.out.print("{" + key + ", " + headers.get(key) + "} ");
        });
        System.out.println("");
    }
}
