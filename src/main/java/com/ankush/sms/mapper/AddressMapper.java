package com.ankush.sms.mapper;

import com.ankush.sms.dto.request.AddressRequest;
import com.ankush.sms.dto.response.AddressResponse;
import com.ankush.sms.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressRequest request) {

        if (request == null) {
            return null;
        }

        return Address.builder()
                .type(request.getType())
                .street(request.getStreet())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .pincode(request.getPincode())
                .build();
    }

    public AddressResponse toResponse(Address address) {

        if (address == null) {
            return null;
        }

        return AddressResponse.builder()
                .id(address.getId())
                .type(address.getType())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .pincode(address.getPincode())
                .build();
    }

}
