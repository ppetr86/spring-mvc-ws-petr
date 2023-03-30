package com.shopapp.batch.service;

import com.shopapp.data.entitydto.out.CurrencyEntityDtoOut;
import com.shopapp.data.entitydto.out.UserDtoOut;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BatchService {

    private List<UserDtoOut> users;

    //here single object must be returned in the ItemReader
    public UserDtoOut getUser() {
        if (this.users == null) {
            restCallToGetUsers();
        } else if (!users.isEmpty()) {
            return users.remove(0);
        }
        return null;
    }

    public List<UserDtoOut> restCallToGetUsers() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Bearer", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdmFuYUB0ZXN0LmNvbSIsImV4cCI6MTY4MDg1MDM4Nn0.m4mrZHLfLFerukCr6NmoVIVc0O_iNcNXafIgjeseRmUB6jID7B6OSg9wO4cp5tSJcOVEXTnZwYXf_aNFt_UXRg");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        var restTemplate = new RestTemplate();
        var result = restTemplate.exchange("http://localhost:8081/api/v1/users", HttpMethod.GET, request, UserDtoOut[].class);

        users = new ArrayList<>();

        return Arrays.asList(Objects.requireNonNull(result.getBody()));
    }

    public List<CurrencyEntityDtoOut> restCallToCreateCurrencies () {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Bearer", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdmFuYUB0ZXN0LmNvbSIsImV4cCI6MTY4MDg1MDM4Nn0.m4mrZHLfLFerukCr6NmoVIVc0O_iNcNXafIgjeseRmUB6jID7B6OSg9wO4cp5tSJcOVEXTnZwYXf_aNFt_UXRg");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        var restTemplate = new RestTemplate();
        var result = restTemplate.exchange("http://localhost:8081/api/v1/currencies", HttpMethod.POST, request, CurrencyEntityDtoOut[].class);

        users = new ArrayList<>();

        return Arrays.asList(Objects.requireNonNull(result.getBody()));
    }
}
