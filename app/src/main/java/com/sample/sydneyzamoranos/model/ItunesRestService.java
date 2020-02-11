package com.sample.sydneyzamoranos.model;

import com.sample.sydneyzamoranos.pojo.Results;
import com.sample.sydneyzamoranos.pojo.SongInfo;
import com.sample.sydneyzamoranos.presenter.CallBackResponse;

import java.util.List;

public interface ItunesRestService {
    boolean processSongs(List<Results> resultsList, CallBackResponse callBackResponse);
    SongInfo getSong();
}
