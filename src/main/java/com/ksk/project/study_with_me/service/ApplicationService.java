package com.ksk.project.study_with_me.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
//        System.out.println("=========ApplicationService : App 종료전 호출 : " + ImageUtils.deleteLocalTempPath());
    }
}
