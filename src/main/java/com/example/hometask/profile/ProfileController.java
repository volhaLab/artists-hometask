package com.example.hometask.profile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hometask.artists.ArtistsService;

@RestController
@RequestMapping("/users")
public class ProfileController {

    private final ArtistsService artistsService;

    public ProfileController(ArtistsService artistsService) {

        this.artistsService = artistsService;
    }

    /**
     * Saving a favourite artists for a user
     *
     * @param artistId artist id
     * @param userId   user id
     */
    @PostMapping("/artists")
    public void saveFavouriteArtist(@RequestParam("artistId") Long artistId,
            @RequestHeader("userId") Long userId) {

        artistsService.saveFavouriteArtist(artistId, userId);
    }
}
