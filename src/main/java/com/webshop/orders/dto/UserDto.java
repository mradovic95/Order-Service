package com.webshop.orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Address address;
    private Boolean active;

    @Data
    @NoArgsConstructor
    public static class Address {
        private String country;
        private String city;
        private String postcode;
        private String street;
        private String number;
        private String apartmentNumber;
    }

}
