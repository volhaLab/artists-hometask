package com.example.hometask.artists;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import com.example.hometask.remote.ItunesItem;

/**
 * Mapper for all Album representations
 */
@Mapper(componentModel = "spring")
public interface AlbumMapper {


    Album toRecord(AlbumEntity entity);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<Album> toRecords(List<AlbumEntity> entities);

    @Mapping(target = "id", source = "collectionId")
    @Mapping(target = "artistId", source = "amgArtistId")
    @Mapping(target = "name", source = "collectionName")
    @Mapping(target = "createdAt", ignore = true)
    AlbumEntity toEntity(ItunesItem item);

    default List<AlbumEntity> toEntities(List<ItunesItem> items) {

        return items.stream()
                .filter(item -> "collection".equals(item.getWrapperType()))
                .map(this::toEntity)
                .toList();
    }
}
