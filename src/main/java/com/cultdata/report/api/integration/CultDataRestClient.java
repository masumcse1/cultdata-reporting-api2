package com.cultdata.report.api.integration;

import com.cultdata.report.api.common.responsedto.DistributionManagerDTO;
import com.cultdata.report.api.common.responsedto.*;
import com.cultdata.report.api.support.exception.AppException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Data
@RequiredArgsConstructor
@Component
public class CultDataRestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${cultdata.api.key}")
    private  String apiKey ;

    @Value("${cultdata.api.base-url}")
    private String baseUrl;

    Logger logger = LoggerFactory.getLogger(CultDataRestClient.class);

    @Cacheable(value = "distributionManagersCache", key = "'distributionManagerData'")
    public List<DistributionManagerDTO> getCacheDistributionManagers(Boolean only_mapped) {
        logger.info("Fetching fresh distribution managers data");
        return getDistributionManagers(only_mapped);
    }

    public List<DistributionManagerDTO> getDistributionManagers(Boolean only_mapped) {

        logger.info(" Fetch distribution managers data--from CultData api ");

        String apiUrl = baseUrl + "api/distribution-managers";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("limit", 200)
                .queryParam("page", 1);

        if (only_mapped != null) {
            builder.queryParam("only_mapped", only_mapped);
        }

        String url = builder.toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<DistributionManagerResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<DistributionManagerResponse>() {}
            );

            if (response.getBody() != null && response.getBody().getData() != null) {
                return response.getBody().getData();
            }
        } catch (RestClientException e) {
            logger.error("Failed to fetch distribution managers: {}", e.toString(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching distribution managers: {}", e.toString(), e);
            throw new AppException("Error while fetching distribution managers", e);
        }
        return Collections.emptyList();
    }


    public List<BusinessUnitDto> getBusinessUnitList() {

        logger.info(" Fetch Business Unit data--from CultData api ");

        String apiUrl = baseUrl + "api/business-units";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);

        String url = builder.toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<BusinessUnitDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<BusinessUnitDto>>() {}
            );

            if (response.getBody() != null && response.getBody() != null) {
                return response.getBody();
            }
        } catch (RestClientException e) {
            logger.error("Failed to fetch distribution managers: {}", e.toString(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching distribution managers: {}", e.toString(), e);
            throw new AppException("Error while fetching distribution managers", e);
        }
        return Collections.emptyList();
    }


    public List<CountryDto> getCountryList() {

        logger.info(" Fetch country data--from CultData api ");

        String apiUrl = baseUrl + "api/countries";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);

        String url = builder.toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<CountryDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<CountryDto>>() {}
            );

            if (response.getBody() != null && response.getBody() != null) {
                return response.getBody();
            }
        } catch (RestClientException e) {
            logger.error("Failed to fetch distribution managers: {}", e.toString(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching distribution managers: {}", e.toString(), e);
            throw new AppException("Error while fetching distribution managers", e);
        }
        return Collections.emptyList();
    }

    public List<ChannelDto> getDistributorChannelList() {

        logger.info(" Fetch Channel data--from CultData api ");

        String apiUrl = baseUrl + "api/channels";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);

        String url = builder.toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<ChannelDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<ChannelDto>>() {}
            );

            if (response.getBody() != null && response.getBody() != null) {
                return response.getBody();
            }
        } catch (RestClientException e) {
            logger.error("Failed to fetch distribution managers: {}", e.toString(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching distribution managers: {}", e.toString(), e);
            throw new AppException("Error while fetching distribution managers", e);
        }
        return Collections.emptyList();
    }


    public List<SupplierDto> getSupplierList(String type) {

        logger.info("Fetch partner data from Partners API");

        String apiUrl = baseUrl + "api/partners";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);
        builder.queryParam("type", type);

        String url = builder.toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKey);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<SupplierDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<SupplierDto>>() {}
            );

            if (response.getBody() != null && !response.getBody().isEmpty()) {
                return response.getBody();
            }
        } catch (RestClientException e) {
            logger.error("Failed to fetch partners: {}", e.toString(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while fetching partners: {}", e.toString(), e);
            throw new AppException("Error while fetching partners", e);
        }
        return Collections.emptyList();
    }

}
