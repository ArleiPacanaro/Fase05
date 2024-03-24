package com.techchallenge05.msestoque.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Configuração para trabahar com RestTemplate.
 */
@Configuration
@ComponentScan("com.techchallenge05.msestoque")
public class AppConfig {

  @Bean
  RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(new ObjectMapper());
    restTemplate.getMessageConverters().add(converter);

    return restTemplate;
  }
}