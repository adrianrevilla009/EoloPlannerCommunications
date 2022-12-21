package es.codeurjc.eoloplanner.server.client;

import es.codeurjc.mastercloudapps.planner.grpc.GetWeatherRequest;
import es.codeurjc.mastercloudapps.planner.grpc.Weather;
import es.codeurjc.mastercloudapps.planner.grpc.WeatherServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static es.codeurjc.mastercloudapps.planner.grpc.WeatherServiceGrpc.WeatherServiceBlockingStub;

@Service
public class WeatherClient {

    @Async
    public CompletableFuture<String> getWeather(String city) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        WeatherServiceGrpc.WeatherServiceBlockingStub client =
                WeatherServiceGrpc.newBlockingStub(channel);

        GetWeatherRequest request = GetWeatherRequest.newBuilder()
                .setCity(city)
                .build();

        Weather response = client.getWeather(request);

        channel.shutdown();

        return CompletableFuture.completedFuture(response.getWeather());
    }
}