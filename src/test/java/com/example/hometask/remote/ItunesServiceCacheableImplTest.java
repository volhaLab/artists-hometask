package com.example.hometask.remote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.example.hometask.configuration.RemoteServiceIntegrationException;

@ExtendWith(MockitoExtension.class)
class ItunesServiceCacheableImplTest {

    private static final String TERM = "term";
    private static final String RATE_LIMIT_KEY = "itunes-rate-limit";
    private static final long ARTIST_ID = 12345L;
    private static final long ALLOWED_RATE_LIMIT_VALUE = 1L;
    private static final long EXCEEDED_RATE_LIMIT_VALUE = 101L;
    @Mock
    private RedisTemplate<String, ItunesResponse> mockRedisItunesTemplate;
    @Mock
    private RedisTemplate<String, String> mockRedisRateLimitTemplate;
    @Mock
    private ItunesClient mockItunesClient;
    @Mock
    private ValueOperations<String, ItunesResponse> mockItunesOps;
    @Mock
    private ValueOperations<String, String> mockRateLimitOps;

    private ItunesServiceCacheableImpl itunesService;

    @BeforeEach
    void setUp() {

        itunesService = new ItunesServiceCacheableImpl(mockRedisItunesTemplate, mockRedisRateLimitTemplate,
                mockItunesClient);
    }

    @Test
    void testSearchArtistsShouldReturnResponseFromCache() {
        // Given
        var cachedResponse = new ItunesResponse();
        when(mockRedisItunesTemplate.opsForValue()).thenReturn(mockItunesOps);
        when(mockItunesOps.get(TERM)).thenReturn(cachedResponse);

        // When
        ItunesResponse result = itunesService.searchArtists(TERM);

        // Then
        assertEquals(cachedResponse, result);
        verifyNoInteractions(mockRedisRateLimitTemplate, mockItunesClient);
    }

    @Test
    void testSearchArtistsShouldReturnResponseFromApi() {
        // Given
        var expectedResponse = new ItunesResponse();
        when(mockRedisItunesTemplate.opsForValue()).thenReturn(mockItunesOps);
        when(mockItunesOps.get(TERM)).thenReturn(null);
        when(mockRedisRateLimitTemplate.opsForValue()).thenReturn(mockRateLimitOps);
        when(mockRedisRateLimitTemplate.hasKey(RATE_LIMIT_KEY)).thenReturn(true);
        when(mockRateLimitOps.increment(RATE_LIMIT_KEY)).thenReturn(ALLOWED_RATE_LIMIT_VALUE);
        when(mockItunesClient.searchArtist(TERM)).thenReturn(expectedResponse);

        // When
        var result = itunesService.searchArtists(TERM);

        // Then
        assertEquals(expectedResponse, result);
    }

    @Test
    void testSearchArtistsShouldReturnException() {
        // Given
        when(mockRedisItunesTemplate.opsForValue()).thenReturn(mockItunesOps);
        when(mockItunesOps.get(TERM)).thenReturn(null);
        when(mockRedisRateLimitTemplate.opsForValue()).thenReturn(mockRateLimitOps);
        when(mockRedisRateLimitTemplate.hasKey(RATE_LIMIT_KEY)).thenReturn(true);
        when(mockRateLimitOps.increment(RATE_LIMIT_KEY)).thenReturn(EXCEEDED_RATE_LIMIT_VALUE);

        // When
        assertThrows(RemoteServiceIntegrationException.class, () -> itunesService.searchArtists(TERM));

        // Then
        verifyNoInteractions(mockItunesClient);
    }

    @Test
    void testSearchAlbumsShouldReturnResponseFromCache() {
        // Given
        var cachedResponse = new ItunesResponse();
        when(mockRedisItunesTemplate.opsForValue()).thenReturn(mockItunesOps);
        when(mockItunesOps.get(ARTIST_ID)).thenReturn(cachedResponse);

        // When
        ItunesResponse result = itunesService.searchAlbums(ARTIST_ID);

        // Then
        assertEquals(cachedResponse, result);
    }

    @Test
    void testSearchAlbumsShouldReturnResponseFromApi() {
        // Given
        var expectedResponse = new ItunesResponse();
        when(mockRedisItunesTemplate.opsForValue()).thenReturn(mockItunesOps);
        when(mockItunesOps.get(ARTIST_ID)).thenReturn(null);
        when(mockRedisRateLimitTemplate.opsForValue()).thenReturn(mockRateLimitOps);
        when(mockRedisRateLimitTemplate.hasKey(RATE_LIMIT_KEY)).thenReturn(true);
        when(mockRateLimitOps.increment(RATE_LIMIT_KEY)).thenReturn(ALLOWED_RATE_LIMIT_VALUE);
        when(mockItunesClient.searchTopAlbums(ARTIST_ID)).thenReturn(expectedResponse);

        // When
        var result = itunesService.searchAlbums(ARTIST_ID);

        // Then
        assertEquals(expectedResponse, result);
    }

    @Test
    void testSearchAlbumsShouldReturnException() {
        // Given
        when(mockRedisItunesTemplate.opsForValue()).thenReturn(mockItunesOps);
        when(mockItunesOps.get(ARTIST_ID)).thenReturn(null);
        when(mockRedisRateLimitTemplate.opsForValue()).thenReturn(mockRateLimitOps);
        when(mockRedisRateLimitTemplate.hasKey(RATE_LIMIT_KEY)).thenReturn(true);
        when(mockRateLimitOps.increment(RATE_LIMIT_KEY)).thenReturn(EXCEEDED_RATE_LIMIT_VALUE);

        // When
        assertThrows(RemoteServiceIntegrationException.class, () -> itunesService.searchAlbums(ARTIST_ID));

        // Then
        verifyNoInteractions(mockItunesClient);
    }
}
