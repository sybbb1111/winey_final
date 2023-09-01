package com.green.winey_final.wine;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.winey_final.wine.model.*;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;


import java.util.List;


@Slf4j
@Service
public class WineMatchService {

    private final WineMatchMapper mapper;
    private final WebClient webClient;

    @Autowired
    public WineMatchService(WineMatchMapper mapper) {
        this.mapper = mapper;
        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(10000));
                    connection.addHandlerLast(new WriteTimeoutHandler(10000));
                });

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory size
                .build();

        this.webClient = WebClient
                .builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl("https://www.vivino.com")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public List<Match> getWineMatches() {

        List<Match> wineMatches = null;
        for (int j = 1; j < 100; j++) {
            int finalJ = j;
            String jsonStr = webClient.get().uri(uriBuilder ->
                            uriBuilder.path("/api/explore/explore")
                                    .queryParam("country_code", "KR")
                                    .queryParam("currency_code", "KRW")
                                    .queryParam("grape_filter", "varietal")
                                    .queryParam("min_rating", "1")
                                    .queryParam("price_range_max", "500000")
                                    .queryParam("price_range_min", "0")
                                    .queryParam("wine_type_ids", "1")
                                    .queryParam("wine_type_ids", "2")
                                    .queryParam("wine_type_ids", "3")
                                    .queryParam("wine_type_ids", "4")
                                    .queryParam("wine_type_ids", "7")
                                    .queryParam("wine_type_ids", "24")
                                    .queryParam("page", finalJ)
                                    .queryParam("language", "en")
                                    .build()
                    ).retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode jsonNode = null;


            try {
                jsonNode = om.readTree(jsonStr);
                wineMatches = om.convertValue(jsonNode.path("explore_vintage").path("matches"), new TypeReference<List<Match>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
                log.error("WineMatches 변환 중 오류 발생: " + e.getMessage(), e);
            }

            if (wineMatches != null) {

                WineInsDto dto = new WineInsDto();

                for (int i = 0; i < wineMatches.size(); i++) {
                    Vintage vintage = wineMatches.get(i).getVintage();
                    if (vintage != null) {
                        Wine wine = vintage.getWine();
                        if (wine != null) {
                            Taste taste = wine.getTaste();
                            if (taste != null && taste.getStructure() != null) {
//                        dto.setId(vintage.getId());
                                dto.setLocation(vintage.getImage().getLocation());
//                        dto.setBottle_small(vintage.getImage().getVariations().getBottleSmall() != null ? vintage.getImage().getVariations().getBottleSmall() : "default_small");
//                        dto.setBottle_medium(vintage.getImage().getVariations().getBottleMedium() != null ? vintage.getImage().getVariations().getBottleMedium() : "default_medium");
                                dto.setBottle_large(vintage.getImage().getVariations().getBottle_large());
                                dto.setId(wine.getId());
                                dto.setName(wine.getName());
                                dto.setPrice(wineMatches.get(i).getPrice().getAmount());
                                dto.setType_id(wine.getType_id());
                                dto.setAcidity(taste.getStructure().getAcidity());
                                dto.setFizziness(taste.getStructure().getFizziness());
                                dto.setSweetness(taste.getStructure().getSweetness());
                                dto.setTannin(taste.getStructure().getTannin());
                                mapper.insWine(dto);
                            }
                        }
                    }
                }
            }
        }
        return wineMatches;
    }


    public int insWine(WineInsDto dto) {
        return mapper.insWine(dto);
    }
}