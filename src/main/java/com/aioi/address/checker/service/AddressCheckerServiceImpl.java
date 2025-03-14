package com.aioi.address.checker.service;

import com.aioi.address.checker.dto.AddressResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AddressCheckerServiceImpl implements AddressCheckerService{

    @Value("${api.nzpost.addresschecker.api.url}")
    private String apiUrl;
    @Value("${api.nzpost.addresschecker.accessToken}")
    private String accessToken;
    @Value("${api.nzpost.addresschecker.key.query}")
    private String parameterKeyQuery;
    @Value("${api.nzpost.addresschekcer.key.size}")
    private String parameterKeySize;
    @Value("${api.nzpost.addresschecker.value.size}")
    private String parameterValueSize;
    @Value("${api.nzpost.addresschecker.header.key.accept}")
    private String headerKeyAccept;
    @Value("${api.nzpost.addresschecker.header.key.authorization}")
    private String headerKeyAuthorization;
    @Value("${api.nzpost.addresschecker.header.value.accept}")
    private String headerValueAccept;
    @Value("${api.nzpost.addresschecker.header.value.tokenType}")
    private String headerValueTokenType;

    private static final Logger logger = LoggerFactory.getLogger(AddressCheckerServiceImpl.class);

    @Override
    public AddressResponseDto getAddressesByQuery(String searchQueryText) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponents buildUri = createPathParameters(searchQueryText);
        try {
            HttpHeaders headers = createHttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<AddressResponseDto> addressResponseDtoResponseEntity = restTemplate.exchange(buildUri.toUriString(), HttpMethod.GET,entity,AddressResponseDto.class);
            logger.info("Result - status ("+ addressResponseDtoResponseEntity.getStatusCode() + ") has body: "+ addressResponseDtoResponseEntity.hasBody());
            return addressResponseDtoResponseEntity.getBody();
        }catch (Exception exception){
            logger.error(exception.getMessage());
        }
        return new AddressResponseDto();
    }

    private HttpHeaders createHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set(headerKeyAccept,headerValueAccept);
        headers.set(headerKeyAuthorization, headerValueTokenType.concat(accessToken));
        return headers;
    }

    private UriComponents createPathParameters(String queryText){
        return UriComponentsBuilder.fromHttpUrl(apiUrl).queryParam(parameterKeyQuery,queryText).queryParam(parameterKeySize,parameterValueSize).build();
    }
}
