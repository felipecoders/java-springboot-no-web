package com.fortech.screenmatch.main;

import com.fortech.screenmatch.model.ApiEpisode;
import com.fortech.screenmatch.model.ApiSeason;
import com.fortech.screenmatch.model.ApiSeries;
import com.fortech.screenmatch.model.Episode;
import com.fortech.screenmatch.service.ConsumeApi;
import com.fortech.screenmatch.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

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
        ApiSeries series = convertData.getData(json, ApiSeries.class);
        System.out.println(series);

        List<ApiSeason> apiSeasons = new ArrayList<>();
        for (int i = 1; i <= series.totalSeasons(); i++) {
            json = consume.getData(URI + seriesName.replace(" ", "+") + "&season=" + i + API_KEY);
            ApiSeason apiSeason = convertData.getData(json, ApiSeason.class);
            apiSeasons.add(apiSeason);
        }
        apiSeasons.forEach(System.out::println);

        apiSeasons.forEach(s -> s.apiEpisodes().forEach(e -> System.out.println(e.title())));

        List<ApiEpisode> apiEpisodes = apiSeasons.stream()
                .flatMap(s -> s.apiEpisodes().stream())
                .toList();

        System.out.println("Top 10 Episodes:");
//        apiEpisodes.stream()
//                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
////                .peek(e -> System.out.println("Filter not N/A " + e))
//                .sorted(Comparator.comparing(ApiEpisode::rating).reversed())
////                .peek(e -> System.out.println("Sort " + e))
//                .limit(10)
////                .peek(e -> System.out.println("Limit " + e))
//                .map(e -> e.title().toUpperCase())
////                .peek(e -> System.out.println("ToUpperCase " + e))
//                .forEach(System.out::println);

        List<Episode> episodes = apiSeasons.stream()
                .flatMap(s -> s.apiEpisodes().stream().map(e -> new Episode(s.season(), e)))
                .collect(Collectors.toList());

        episodes.forEach(System.out::println);

        //#region find by title
//        System.out.print("Digite o título que esta procurando: ");
//        var title = scanner.nextLine();
//        Optional<Episode> foundEpisode = episodes.stream()
//                .filter(e -> e.getTitle().toUpperCase().contains(title.toUpperCase()))
//                .findFirst();
//
//        if (foundEpisode.isEmpty()) {
//            System.out.println("Nenhum episódeo encontrado!");
//        } else {
//            System.out.println("Temporada: " + foundEpisode.get().getSeason());
//            System.out.println("Episódio: " + foundEpisode.get().getEpisode());
//        }
        //#endregion

        //#region filter by year
//        System.out.println("A partir de que ano você deseja ver os episodeos?");
//        var year = scanner.nextInt();
//        scanner.nextLine();
//
//        LocalDate searchData = LocalDate.of(year, 1, 1);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodes.stream()
//                .filter(e -> e.getReleased() != null && (e.getReleased().isAfter(searchData) || e.getReleased().isEqual(searchData)))
//                .forEach(e -> System.out.println("Temporada: " + e.getSeason() + " | " +
//                        "Episódio: " + e.getTitle() + " | " +
//                        "Data de lançamento: " + e.getReleased().format(formatter)));
        //#endregion

        //#region average of rating
        Map<Integer, Double> ratingBySeason = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(
                        Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)));

        System.out.println(ratingBySeason);

        DoubleSummaryStatistics statistics = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));

        System.out.println("Média: " + statistics.getAverage());
        System.out.println("Melhor: " + statistics.getMax());
        System.out.println("Pior: " + statistics.getMin());
        System.out.println("Episódios avaliados: " + statistics.getCount());
        //#endregion
    }
}
