package com.israeldago.flywayDemo.config.security;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

@Component
public class MySecurityEventListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {
    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent evt) {
        System.out.printf("\nLogin failed : %s\n", evt.getException().getMessage());
    }
}
