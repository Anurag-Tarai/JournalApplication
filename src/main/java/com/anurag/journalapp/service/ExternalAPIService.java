package com.anurag.journalapp.service;

import com.anurag.journalapp.dto.response.QuoteResponse;
import com.anurag.journalapp.dto.response.WheatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalAPIService {

    @Value("${quote.api.key}")
    private String QU0TE_API_KEY;

    @Value("${wheather.api.key}")
    private String WHEATHER_API_KEY;

    private  static final String QUOTE_API = "https://api.api-ninjas.com/v1/quotes";
    private  static final String WHEATHER_API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


    private final RestTemplate restTemplate;

    private final RedisService redisService;


    public ExternalAPIService(RestTemplate restTemplate, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.redisService = redisService;
    }

    public WheatherApiResponse getWheather(String city){

       WheatherApiResponse resp = redisService.
               get("wheather_of_"+city, WheatherApiResponse.class);
       if(resp!=null) return resp;
       else{
           String finalApi = WHEATHER_API.replace("API_KEY", WHEATHER_API_KEY).replace("CITY", city);
           ResponseEntity<WheatherApiResponse> response = restTemplate
                   .exchange(finalApi, HttpMethod.GET, null, WheatherApiResponse.class );
           WheatherApiResponse body =  response.getBody();
           if(body!=null){
               redisService.set("wheather_of_"+city, body, 300l);
           }
           return body;
       }
    }

    public QuoteResponse getQuote(){
        QuoteResponse resp = redisService.get("quote", QuoteResponse.class);
        if(resp!=null){
            return resp;
        }else{
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Api-Key", "3q7NA1mopp9tcbcJbJ2IQw==yejcVCNsQclMpqF8");

// Wrap headers in HttpEntity (no body for GET request)
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<QuoteResponse[]> response = restTemplate.exchange(
                    QUOTE_API,
                    HttpMethod.GET,
                    entity,
                    QuoteResponse[].class
            );

            QuoteResponse[] quoteResponses = response.getBody();
            QuoteResponse quoteResponse = quoteResponses != null && quoteResponses.length > 0
                    ? quoteResponses[0]
                    : null;
            if(quoteResponse!=null){
                redisService.set("quote", quoteResponse,300l);
            }
            return quoteResponse;
        }
    }

}
