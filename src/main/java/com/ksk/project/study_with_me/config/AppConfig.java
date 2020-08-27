package com.ksk.project.study_with_me.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("conditionLanguages", MatchNames.ConditionLanguages.class);
        return enumMapper;
    }
}
