package com.anurag.journalapp.dto.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public class WheatherApiResponse {
    private Current current;

    @Data
    public class Current{

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


