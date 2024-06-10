package com.example.hometask.remote;

import java.io.Serializable;

public class ItunesItem implements Serializable {

    private String wrapperType;
    private Long amgArtistId;
    private String artistName;
    private String collectionName;
    private Long collectionId;

    public ItunesItem() {

    }

    public String getWrapperType() {

        return wrapperType;
    }

    public void setWrapperType(String wrapperType) {

        this.wrapperType = wrapperType;
    }

    public Long getAmgArtistId() {

        return amgArtistId;
    }

    public void setAmgArtistId(Long amgArtistId) {

        this.amgArtistId = amgArtistId;
    }

    public String getArtistName() {

        return artistName;
    }

    public void setArtistName(String artistName) {

        this.artistName = artistName;
    }

    public String getCollectionName() {

        return collectionName;
    }

    public void setCollectionName(String collectionName) {

        this.collectionName = collectionName;
    }

    public Long getCollectionId() {

        return collectionId;
    }

    public void setCollectionId(Long collectionId) {

        this.collectionId = collectionId;
    }
}
