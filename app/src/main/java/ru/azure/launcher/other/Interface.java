package ru.azure.launcher.other;

import ru.azure.launcher.model.News;
import ru.azure.launcher.model.Servers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Interface {
    @GET("http://wh24696.web3.maze-tech.ru/Files/servers.json")
    Call<List<Servers>> getServers();

    @GET("http://wh24696.web3.maze-tech.ru/Files/stories.json")
    Call<List<News>> getNews();

}
