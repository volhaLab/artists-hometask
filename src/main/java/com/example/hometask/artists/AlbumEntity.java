package com.example.hometask.artists;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "albums")
@EntityListeners(AuditingEntityListener.class)
public class AlbumEntity {

    @Id
    private Long id;

    @Column(name = "artist_id")
    private Long artistId;

    @Column(name = "name")
    private String name;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public AlbumEntity() {

    }

    public AlbumEntity(Long artistId, String name) {

        this.artistId = artistId;
        this.name = name;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getArtistId() {

        return artistId;
    }

    public void setArtistId(Long artistId) {

        this.artistId = artistId;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public LocalDateTime getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }
}
