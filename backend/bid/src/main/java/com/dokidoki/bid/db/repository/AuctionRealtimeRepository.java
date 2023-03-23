package com.dokidoki.bid.db.repository;

import com.dokidoki.bid.db.entity.AuctionRealtime;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface AuctionRealtimeRepository {

    Optional<AuctionRealtime> findById(long auctionId);

    void save(AuctionRealtime auctionRealtime);

    void save(AuctionRealtime auctionRealtime, long ttl, TimeUnit timeUnit);

    boolean deleteAll();

}
