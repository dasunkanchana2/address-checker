package com.aioi.address.checker.service;

import com.aioi.address.checker.dto.AddressResponseDto;

public interface AddressCheckerService {
    AddressResponseDto getAddressesByQuery(String searchQueryText);
}
