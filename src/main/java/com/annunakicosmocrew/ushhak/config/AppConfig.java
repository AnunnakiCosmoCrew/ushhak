package com.annunakicosmocrew.ushhak.config;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Tika tika() throws TikaException, IOException, SAXException {
        TikaConfig tikaConfig = new TikaConfig(new FileInputStream("tika-config.xml"));
        return new Tika(tikaConfig);
    }
}
