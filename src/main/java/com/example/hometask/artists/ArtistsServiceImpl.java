package com.example.hometask.artists;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.example.hometask.configuration.BadRequestException;
import com.example.hometask.profile.UserEntity;
import com.example.hometask.profile.UsersRepository;
import com.example.hometask.remote.ItunesResponse;
import com.example.hometask.remote.ItunesService;

@Service
public class ArtistsServiceImpl implements ArtistsService {

    private final UsersRepository usersRepository;
    private final AlbumsRepository albumsRepository;
    private final ArtistMapper artistMapper;
    private final AlbumMapper albumMapper;
    private final ItunesService itunesService;

    public ArtistsServiceImpl(UsersRepository usersRepository, AlbumsRepository albumsRepository,
            ArtistMapper artistMapper, AlbumMapper albumMapper, ItunesService itunesService) {

        this.usersRepository = usersRepository;
        this.albumsRepository = albumsRepository;
        this.artistMapper = artistMapper;
        this.albumMapper = albumMapper;
        this.itunesService = itunesService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Artist> searchArtist(String name) {

        if (StringUtils.isBlank(name)) {
            return Collections.emptyList();
        }

        var term = name.toLowerCase().trim();
        ItunesResponse itunesResponse = itunesService.searchArtists(term);
        return artistMapper.toRecords(itunesResponse.getResults());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveFavouriteArtist(Long artistId, Long userId) {

        usersRepository.saveAndFlush(new UserEntity(userId, artistId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Album> searchAlbums(Long userId) {

        var userEntity = usersRepository.getReferenceById(userId);
        var favouriteArtistId = userEntity.getFavouriteArtistId();
        if (favouriteArtistId == null) {
            throw new BadRequestException("Please select favourite artist");
        }
        if (CollectionUtils.isEmpty(userEntity.getTopAlbums())) {
            ItunesResponse itunesResponse = itunesService.searchAlbums(favouriteArtistId);
            List<AlbumEntity> albums = albumMapper.toEntities(itunesResponse.getResults());
            albumsRepository.saveAll(albums);
            return albumMapper.toRecords(albums);
        }
        return albumMapper.toRecords(userEntity.getTopAlbums());
    }
}
