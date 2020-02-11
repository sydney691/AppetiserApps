package com.sample.sydneyzamoranos.view;

import com.sample.sydneyzamoranos.pojo.SongInfo;
import com.sample.sydneyzamoranos.presenter.CallBackResponse;

public interface ItunesSongsView {
    void processSong(boolean done, CallBackResponse callBackResponse);
    void setSongsToUI(SongInfo songInfo);
}
