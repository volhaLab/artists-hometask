package com.example.hometask.artists;

import java.util.List;

public interface ArtistsService {

    /**
     * Searching artists by name
     *
     * @param name searching name
     * @return found artists
     */
    List<Artist> searchArtist(String name);

    /**
     * Saving a favorite artist for a user
     *
     * @param artistId artist id
     * @param userId   user id
     */
    void saveFavouriteArtist(Long artistId, Long userId);

    /**
     * Searching top albums of a user's favourite artist
     *
     * @param userId user id
     * @return found albums
     */
    List<Album> searchAlbums(Long userId);
}
