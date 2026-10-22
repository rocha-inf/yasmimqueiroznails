package com.rocha_inf.yasmimqueiroznails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class YasmimqueiroznailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(YasmimqueiroznailsApplication.class, args);
	}

}
