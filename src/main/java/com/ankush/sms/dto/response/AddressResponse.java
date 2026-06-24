package com.ankush.sms.dto.response;
import com.ankush.sms.enums.AddressType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {

    private Long id;
    private AddressType type;
    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
