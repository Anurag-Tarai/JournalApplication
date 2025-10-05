package com.anurag.journalapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
public class WheatherApiResponse {
    private Current current;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Current{

        public int temperature;

        public int pressure;
        public int precip;
        public int humidity;
        public int cloudcover;
        public int feelslike;
        public int uv_index;
        public int visibility;
        public String is_day;
    }
}


