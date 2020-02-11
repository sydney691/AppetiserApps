package com.sample.sydneyzamoranos.presenter;

import com.sample.sydneyzamoranos.model.ItuneRestServiceImpl;
import com.sample.sydneyzamoranos.model.ItunesRestService;

import com.sample.sydneyzamoranos.pojo.Results;
import com.sample.sydneyzamoranos.view.ItunesSongsView;

import java.util.List;

public class GetSongInfoImpl implements GetSongInfo {

    private ItunesRestService itunesRestService = new ItuneRestServiceImpl();

    private  ItunesSongsView itunesSongsView;

    public GetSongInfoImpl(ItunesSongsView itunesSongsView){
        this.itunesSongsView = itunesSongsView;
    }

    public void getAllSongs(List<Results> resultsList, CallBackResponse callBackResponse){
        itunesSongsView.processSong(itunesRestService.processSongs(resultsList, callBackResponse), callBackResponse);
    }

    public void processUi(){
        itunesSongsView.setSongsToUI(itunesRestService.getSong());
    }
}
