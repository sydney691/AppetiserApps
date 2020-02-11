package com.sample.sydneyzamoranos.pojo;

import java.util.List;

public class SongInfo {
    private String resultCount;

    private List<Results> results;

    public String getResultCount ()
    {
        return resultCount;
    }

    public void setResultCount (String resultCount)
    {
        this.resultCount = resultCount;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [resultCount = "+resultCount+", results = "+results+"]";
    }
}
