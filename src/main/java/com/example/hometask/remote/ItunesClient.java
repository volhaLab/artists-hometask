package com.example.hometask.remote;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.hometask.configuration.RemoteServiceIntegrationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A client to communicate with ITunes API
 */
@Component
public class ItunesClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    public ItunesClient() {

        this.restTemplate = new RestTemplateBuilder()
                .rootUri("https://itunes.apple.com")
                .build();
        this.mapper = Jackson2ObjectMapperBuilder.json().build();
    }

    /**
     * Searching artists by name
     *
     * @param term normalized search term
     * @return parsed ITunes response
     */
    public ItunesResponse searchArtist(String term) {

        var responseAsString = restTemplate.postForObject(
                "/search?entity=allArtist&limit=10&term={term}", null, String.class,
                term);
        return readToItunesResponse(responseAsString);
    }

    /**
     * Searching top 5 albums of an artist
     *
     * @param amgArtistId Itunes amg artist id
     * @return parsed ITunes response
     */
    public ItunesResponse searchTopAlbums(Long amgArtistId) {

        var responseAsString = restTemplate.postForObject(
                "/lookup?amgArtistId={artistId}&entity=album&limit=5", null, String.class, amgArtistId);
        return readToItunesResponse(responseAsString);
    }

    private ItunesResponse readToItunesResponse(String responseAsString) {

        try {
            return mapper.readValue(responseAsString, ItunesResponse.class);
        } catch (JsonProcessingException e) {
            throw new RemoteServiceIntegrationException(e);
        }
    }
}
