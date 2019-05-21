package com.webshop.orders.service;

import io.jsonwebtoken.Claims;

public interface TokenService {

    Claims parseToken(String jwt);

}
