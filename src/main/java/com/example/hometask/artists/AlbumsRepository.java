package com.example.hometask.artists;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumsRepository extends JpaRepository<AlbumEntity, Long> {

    @Modifying
    @Query("delete from AlbumEntity a where a.createdAt<:now")
    int deleteOld(@Param("now") LocalDateTime now);

}
