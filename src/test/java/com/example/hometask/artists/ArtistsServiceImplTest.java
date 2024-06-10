package com.example.hometask.artists;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.hometask.configuration.BadRequestException;
import com.example.hometask.profile.UserEntity;
import com.example.hometask.profile.UsersRepository;
import com.example.hometask.remote.ItunesResponse;
import com.example.hometask.remote.ItunesService;

@ExtendWith(MockitoExtension.class)
class ArtistsServiceImplTest {

    private static final String NAME_SEARCH = " NAme ";
    private static final String NORMALIZED_NAME = "name";
    private static final long ARTIST_ID = 12345L;
    private static final long USER_ID = 4256L;
    private static final String ALBUM_NAME = "album name";

    @Mock
    private UsersRepository mockUsersRepository;
    @Mock
    private AlbumsRepository mockAlbumsRepository;
    @Mock
    private ArtistMapper mockArtistMapper;
    @Mock
    private AlbumMapper mockAlbumMapper;
    @Mock
    private ItunesService mockItunesService;

    private ArtistsServiceImpl artistsService;

    @BeforeEach
    void setUp() {

        artistsService = new ArtistsServiceImpl(mockUsersRepository, mockAlbumsRepository,
                mockArtistMapper, mockAlbumMapper, mockItunesService);
    }

    @Test
    void testSearchArtistShouldReturnEmptyListIfBlankName() {
        // When
        List<Artist> result = artistsService.searchArtist("   ");

        // Then
        assertThat(result).isEmpty();
        verifyNoInteractions(mockItunesService, mockArtistMapper);
    }

    @Test
    void testSearchArtist() {
        // Given
        var itunesResponse = new ItunesResponse(0, emptyList());
        var mappedArtists = singletonList(new Artist(ARTIST_ID, NORMALIZED_NAME));
        when(mockItunesService.searchArtists(anyString())).thenReturn(itunesResponse);
        when(mockArtistMapper.toRecords(anyList())).thenReturn(mappedArtists);

        // When
        List<Artist> result = artistsService.searchArtist(NAME_SEARCH);

        // Then
        assertThat(result).isEqualTo(mappedArtists);
        verify(mockItunesService).searchArtists(NORMALIZED_NAME);
        verify(mockArtistMapper).toRecords(itunesResponse.getResults());
    }

    @Test
    void testSaveFavouriteArtist() {
        // When
        artistsService.saveFavouriteArtist(ARTIST_ID, USER_ID);

        // Then
        verify(mockUsersRepository).saveAndFlush(new UserEntity(USER_ID, ARTIST_ID));
    }

    @Test
    void testSearchAlbumsShouldThrowExceptionIfNoFavoriteArtist() {
        // Given
        when(mockUsersRepository.getReferenceById(USER_ID)).thenReturn(new UserEntity());

        // When
        assertThrows(BadRequestException.class, () -> artistsService.searchAlbums(USER_ID));

        // Then
        verifyNoInteractions(mockItunesService, mockAlbumMapper, mockAlbumsRepository);
    }

    @Test
    void testSearchAlbumsShouldReturnFromRepository() {
        // Given
        List<Album> expectedResult = List.of(new Album(ARTIST_ID, "name"));
        UserEntity user = new UserEntity(USER_ID, ARTIST_ID);
        user.setTopAlbums(List.of(new AlbumEntity(ARTIST_ID, ALBUM_NAME)));
        when(mockUsersRepository.getReferenceById(USER_ID)).thenReturn(user);
        when(mockAlbumMapper.toRecords(anyList())).thenReturn(expectedResult);

        // When
        List<Album> result = artistsService.searchAlbums(USER_ID);

        // Then
        assertThat(result).isEqualTo(expectedResult);
        verifyNoInteractions(mockItunesService, mockAlbumsRepository);
    }

    @Test
    void testSearchAlbumsShouldReturnFromApi() {
        // Given
        List<Album> expectedResult = List.of(new Album(ARTIST_ID, ALBUM_NAME));
        when(mockUsersRepository.getReferenceById(USER_ID)).thenReturn(new UserEntity(USER_ID, ARTIST_ID));
        when(mockItunesService.searchAlbums(ARTIST_ID)).thenReturn(new ItunesResponse(0, emptyList()));
        List<AlbumEntity> mappedAlbumEntities = List.of(new AlbumEntity(ARTIST_ID, ALBUM_NAME));
        when(mockAlbumMapper.toEntities(anyList())).thenReturn(mappedAlbumEntities);
        when(mockAlbumsRepository.saveAll(anyList())).thenReturn(mappedAlbumEntities);
        when(mockAlbumMapper.toRecords(anyList())).thenReturn(expectedResult);

        // When
        List<Album> result = artistsService.searchAlbums(USER_ID);

        // Then
        assertThat(result).isEqualTo(expectedResult);

        verify(mockAlbumsRepository).saveAll(mappedAlbumEntities);
        verify(mockAlbumMapper).toRecords(mappedAlbumEntities);
    }

}
