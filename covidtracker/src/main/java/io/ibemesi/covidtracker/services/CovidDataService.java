/*
*   Class which acts as a data service, requesting data on Covid-19 cases from the CSV
*   https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv
*   on start-up of the program and at a scheduled rate.
*/

package io.ibemesi.covidtracker.services;

import io.ibemesi.covidtracker.model.LocationCases;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidDataService {

    private int totalCovidCases;
    private List<LocationCases> covidStats = new ArrayList<>();
    private static String COVID_DATA = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    public List<LocationCases> getCovidStats() {
        return covidStats;
    }

    /*
     *   Method which requests and formats the data regarding Covid-19 cases.
     */
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void getData() throws IOException, InterruptedException {
        List<LocationCases> covidIndividualStats = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(COVID_DATA)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader dataReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(dataReader);
        for (CSVRecord record : records) {

            LocationCases location = new LocationCases();

            location.setProvince(record.get("Province/State"));
            location.setCountry(record.get("Country/Region"));

            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int previousDayCases = Integer.parseInt(record.get(record.size() - 2));

            location.setTotalCases(latestCases);
            location.setTotalDifference(latestCases - previousDayCases);
            covidIndividualStats.add(location);

            int temp = 0;
            for(int i = 0; i < covidIndividualStats.size(); i++){
                temp = temp + covidIndividualStats.get(i).getTotalCases();
            }
            totalCovidCases = temp;
        }
        covidStats = (covidIndividualStats);
    }
    // returns the total number of cases world-wide.
    public int getTotalCovidCases() {
        return totalCovidCases;
    }
}
