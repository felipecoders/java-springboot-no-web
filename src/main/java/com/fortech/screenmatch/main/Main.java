package com.fortech.screenmatch.main;

import com.fortech.screenmatch.model.Season;
import com.fortech.screenmatch.model.Series;
import com.fortech.screenmatch.service.ConsumeApi;
import com.fortech.screenmatch.service.ConvertData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final ConsumeApi consume = new ConsumeApi();
    private final Scanner scanner = new Scanner(System.in);
    private final ConvertData convertData = new ConvertData();

    public void showMenu() {
        System.out.println("Digite o nome da série: ");
        var seriesName = scanner.nextLine();
        String URI = "https://www.omdbapi.com/?t=";
        String API_KEY = "&apikey=6585022c";
        var json = consume.getData(URI + seriesName.replace(" ", "+") + API_KEY);
        Series series = convertData.getData(json, Series.class);
        System.out.println(series);

        List<Season> seasons = new ArrayList<>();
        for (int i = 1; i <= series.totalSeasons(); i++) {
            json = consume.getData(URI + seriesName.replace(" ", "+") + "&season=" + i + API_KEY);
            Season season = convertData.getData(json, Season.class);
            seasons.add(season);
        }
        seasons.forEach(System.out::println);

        seasons.forEach(s -> s.episodes().forEach(e -> System.out.println(e.title())));
    }
}
