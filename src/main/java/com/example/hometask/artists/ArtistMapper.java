package com.example.hometask.artists;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import com.example.hometask.remote.ItunesItem;

/**
 * Mapper for all Artist representations
 */
@Mapper(componentModel = "spring")
public interface ArtistMapper {

    @Mapping(target = "artistId", source = "amgArtistId")
    @Mapping(target = "name", source = "artistName")
    Artist toRecord(ItunesItem item);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<Artist> toRecords(List<ItunesItem> entities);
}
