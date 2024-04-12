package com.cleanhub.codingchallenge;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Class to hold bean declarations
 */
@Component
public class Beans {

    /**
     * Gets RestTemplate Bean
     * @return RestTemplate
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
