package com.fortech.screenmatch;

import com.fortech.screenmatch.model.Series;
import com.fortech.screenmatch.service.ConsumeApi;
import com.fortech.screenmatch.service.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumeApi = new ConsumeApi();
		var json = consumeApi.getData("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);

		var convertData = new ConvertData();
		Series series = convertData.getData(json, Series.class);

		System.out.println(series);
	}
}
