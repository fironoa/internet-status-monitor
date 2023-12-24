package com.gobartsdev.internetmonitor.repository;

import com.gobartsdev.internetmonitor.entity.IcmpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface IcmpRepository extends JpaRepository<IcmpEntity, Long> {

    List<IcmpEntity> findByTimestampBetweenAndClientOrderByTimestampAsc(Instant start, Instant end, String client);

    List<IcmpEntity> findByTimestampBefore(Instant cutoff);

    @Query("SELECT DISTINCT ie.client FROM IcmpEntity ie")
    List<String> findDistinctClients();
}
