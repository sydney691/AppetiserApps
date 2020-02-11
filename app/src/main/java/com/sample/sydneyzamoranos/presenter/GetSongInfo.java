package com.sample.sydneyzamoranos.presenter;

import com.sample.sydneyzamoranos.pojo.Results;
import com.sample.sydneyzamoranos.pojo.SongInfo;

import java.util.List;

public interface GetSongInfo {
    void getAllSongs(List<Results> resultsList, CallBackResponse callBackResponse);
    void processUi();
}
