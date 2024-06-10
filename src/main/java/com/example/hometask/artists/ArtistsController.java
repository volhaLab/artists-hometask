package com.example.hometask.artists;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artists")
public class ArtistsController {

    private final ArtistsService artistsService;

    public ArtistsController(ArtistsService artistsService) {

        this.artistsService = artistsService;
    }

    /**
     * Searching an artist by name
     *
     * @param name artist name
     * @return list of found artists
     */
    @GetMapping
    public List<Artist> searchArtist(@RequestParam(value = "name") String name) {

        return artistsService.searchArtist(name);
    }

    /**
     * Searching top 5 albums of user's favourite artist
     *
     * @param userId user id
     * @return found top albums
     */
    @GetMapping("/albums")
    public List<Album> searchTopAlbums(@RequestHeader("userId") Long userId) {

        return artistsService.searchAlbums(userId);
    }
}
