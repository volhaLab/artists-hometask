package com.example.hometask.remote;

public interface ItunesService {

    /**
     * Searching artists by term
     *
     * @param term normalized search term
     * @return ITunes response with artists
     */
    ItunesResponse searchArtists(String term);

    /**
     * Searching top albums by artist id
     *
     * @param artistId Itunes amg artist id
     * @return ITunes response with albums
     */
    ItunesResponse searchAlbums(Long artistId);
}
