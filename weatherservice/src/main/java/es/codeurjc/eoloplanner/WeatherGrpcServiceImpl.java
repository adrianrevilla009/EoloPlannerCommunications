package es.codeurjc.eoloplanner;

import es.codeurjc.mastercloudapps.planner.grpc.GetWeatherRequest;
import es.codeurjc.mastercloudapps.planner.grpc.Weather;
import es.codeurjc.mastercloudapps.planner.grpc.WeatherService;
import io.quarkus.grpc.GrpcService;

import io.smallrye.mutiny.Uni;

import java.time.Duration;
import java.util.Random;

@GrpcService
public class WeatherGrpcServiceImpl implements WeatherService {

    @Override
    public Uni<Weather> getWeather(GetWeatherRequest request) {

        String city = request.getCity();

        String weather = city.matches("^[aeiou].*") ? "Rainy" : "Sunny";

        return Uni.createFrom().item(Weather.newBuilder().setWeather(weather).setCity(city).build())
                .onItem().delayIt().by(Duration.ofMillis(1000 + new Random().nextInt(2000)));
    }
}
