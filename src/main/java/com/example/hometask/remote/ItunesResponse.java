package com.example.hometask.remote;

import java.io.Serializable;
import java.util.List;

public class ItunesResponse implements Serializable {

    private int resultCount;
    private List<ItunesItem> results;


    public ItunesResponse() {

    }

    public ItunesResponse(int resultCount, List<ItunesItem> results) {

        this.resultCount = resultCount;
        this.results = results;
    }

    public int getResultCount() {

        return resultCount;
    }

    public void setResultCount(int resultCount) {

        this.resultCount = resultCount;
    }

    public List<ItunesItem> getResults() {

        return results;
    }

    public void setResults(List<ItunesItem> results) {

        this.results = results;
    }
}
