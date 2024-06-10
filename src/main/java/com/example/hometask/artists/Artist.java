package com.example.hometask.artists;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Represents Artist API response
 */
@JsonInclude(Include.NON_NULL)
public record Artist(Long artistId, String name) {

}
