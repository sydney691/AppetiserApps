package com.sample.sydneyzamoranos.model;

import com.google.gson.Gson;
import com.sample.sydneyzamoranos.pojo.Results;
import com.sample.sydneyzamoranos.pojo.SongInfo;
import com.sample.sydneyzamoranos.presenter.CallBackResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ItuneRestServiceImpl implements ItunesRestService {

    @Override
    public boolean processSongs(List<Results> resultsList , CallBackResponse callBackResponse) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://itunes.apple.com/search?term=star&country=au&media=movie&all/")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                SongInfo song = gson.fromJson(response.body().string(), SongInfo.class);
                resultsList.clear();
                resultsList.addAll(song.getResults());
                callBackResponse.completed(true);
            }
        });
        if(resultsList.size() > 1){
            return true;
        }else
            return false;
    }

    @Override
    public SongInfo getSong() {
        return null;
    }
}
