package com.dokidoki.notice.api.response;

import com.dokidoki.notice.db.entity.AuctionRealtime;
import com.dokidoki.notice.kafka.dto.KafkaAuctionEndDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeSuccessResp implements NoticeResp {
    private NoticeType type;
    private long noticeId;
    private long productId;
    private String productName;
    private long auctionId;
    private int finalPrice;
    private boolean isRead;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timeStamp;

    public static NoticeSuccessResp of(KafkaAuctionEndDTO dto) {
        NoticeSuccessResp resp = NoticeSuccessResp.builder()
                .type(NoticeType.PURCHASE_SUCCESS)
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .auctionId(dto.getAuctionId())
                .finalPrice(dto.getFinalPrice())
                .timeStamp(dto.getEndTime())
                .build();
        return resp;
    }

    @Override
    public NoticeType typeIs() {
        return type;
    }

    @Override
    public void read() {
        this.isRead = true;
    }

    @Override
    public void unRead() {
        this.isRead = false;
    }

}
