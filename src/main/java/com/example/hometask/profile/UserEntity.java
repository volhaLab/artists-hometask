package com.example.hometask.profile;

import java.util.List;
import java.util.Objects;

import com.example.hometask.artists.AlbumEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private Long id;

    @Column(name = "artist_id")
    private Long favouriteArtistId;

    @OneToMany
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    private List<AlbumEntity> topAlbums;

    public UserEntity() {

    }

    public UserEntity(Long id, Long favouriteArtistId) {

        this.id = id;
        this.favouriteArtistId = favouriteArtistId;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getFavouriteArtistId() {

        return favouriteArtistId;
    }

    public void setFavouriteArtistId(Long favouriteArtistId) {

        this.favouriteArtistId = favouriteArtistId;
    }

    public List<AlbumEntity> getTopAlbums() {

        return topAlbums;
    }

    public void setTopAlbums(List<AlbumEntity> topAlbums) {

        this.topAlbums = topAlbums;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(favouriteArtistId, that.favouriteArtistId)
                && Objects.equals(topAlbums, that.topAlbums);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, favouriteArtistId, topAlbums);
    }
}
