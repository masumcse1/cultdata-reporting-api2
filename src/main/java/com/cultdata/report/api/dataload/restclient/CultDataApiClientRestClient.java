package com.cultdata.report.api.dataload.restclient;

import com.cultdata.report.api.dataload.transfer.Client3Dto;
import com.cultdata.report.api.dataload.transfer.ClientPage;
import com.cultdata.report.api.support.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CultDataApiClientRestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${cultdata.api.key}")
    private String apiKey;

    @Value("${cultdata.api.base-url}")
    private String baseUrl;

    Logger logger = LoggerFactory.getLogger(CultDataApiClientRestClient.class);

    public List<Client3Dto> getAllClientPages(Integer limit) throws DataNotFoundException {

        int page = 1;
        ClientPage firstPage = getClients(limit, page);

        int totalPages = firstPage.getTotalPages() + 1;
        logger.info("Total pages in supplier API (Client): " + totalPages);

        List<Client3Dto> allClients = new ArrayList<>();

        for (int i = 1; i <= totalPages; i++) {
            ClientPage clientPage = getClients(limit, i);
            if (clientPage != null && clientPage.getData() != null) {
                allClients.addAll(clientPage.getData());
            }
        }

        return allClients;
    }

    public ClientPage getClients(Integer limit, int page) {
        String apiUrl = baseUrl + "/api/clients";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("limit", limit)
                .queryParam("page", page);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ClientPage> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    requestEntity,
                    ClientPage.class
            );

            return response.getBody();

        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch clients from CultDataAPI", e);
        }
    }

    public Client3Dto getClientById(Integer id) {
        String apiUrl = baseUrl + "/api/client/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Client3Dto> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    requestEntity,
                    Client3Dto.class
            );

            return response.getBody();

        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch client by ID from CultDataAPI", e);
        }
    }

}
