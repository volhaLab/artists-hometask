package com.example.hometask.remote;

import static java.util.Objects.nonNull;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.hometask.configuration.RemoteServiceIntegrationException;

/**
 * Cacheable Itunes API communication service.
 * Firstly, the service checks Redis cache to return data before making a real call to Itunes API
 */
@Service
public class ItunesServiceCacheableImpl implements ItunesService {

    private static final String RATE_LIMIT_KEY = "itunes-rate-limit";
    private static final Integer RATE_LIMIT_VALUE = 100;
    private final RedisTemplate<String, ItunesResponse> redisItunesTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private final ItunesClient itunesClient;

    public ItunesServiceCacheableImpl(
            @Qualifier("redisItunesTemplate") RedisTemplate<String, ItunesResponse> redisItunesTemplate,
            @Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate,
            ItunesClient itunesClient) {

        this.redisItunesTemplate = redisItunesTemplate;
        this.redisTemplate = redisTemplate;
        this.itunesClient = itunesClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItunesResponse searchArtists(String term) {

        ValueOperations<String, ItunesResponse> opsForValue = redisItunesTemplate.opsForValue();
        var cachedResponse = opsForValue.get(term);
        if (cachedResponse == null) {
            if (isApiCallAllowed()) {
                ItunesResponse itunesResponse = itunesClient.searchArtist(term);
                opsForValue.set(term, itunesResponse, 1, TimeUnit.DAYS);
                return itunesResponse;
            } else {
                throw new RemoteServiceIntegrationException("Rate limit has been exceeded. Please try again later");
            }
        }
        return cachedResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItunesResponse searchAlbums(Long artistId) {

        ValueOperations<String, ItunesResponse> opsForValue = redisItunesTemplate.opsForValue();
        var cachedResponse = opsForValue.get(artistId);
        if (cachedResponse == null) {
            if (isApiCallAllowed()) {
                ItunesResponse itunesResponse = itunesClient.searchTopAlbums(artistId);
                opsForValue.set(artistId.toString(), itunesResponse, 1, TimeUnit.DAYS);
                return itunesResponse;
            } else {
                throw new RemoteServiceIntegrationException("Rate limit has been exceeded. Please try again later");
            }
        }
        return cachedResponse;
    }

    /**
     * Validates if API calls limit is not exceeded
     *
     * @return true if rate limit is not exceeded, false otherwise
     */
    private boolean isApiCallAllowed() {

        var opsForValue = redisTemplate.opsForValue();
        if (Boolean.FALSE.equals(redisTemplate.hasKey(RATE_LIMIT_KEY))) {
            opsForValue.set(RATE_LIMIT_KEY, "1", 1, TimeUnit.HOURS);
        }
        Long count = opsForValue.increment(RATE_LIMIT_KEY);
        return nonNull(count) && count <= RATE_LIMIT_VALUE;
    }
}
