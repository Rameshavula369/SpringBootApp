package com.effort.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * This class provides application-wide access to the Spring ApplicationContext.
 * The ApplicationContext is injected by Spring during initialization.
 */
@Component 
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    /**
     * Get access to the Spring ApplicationContext from anywhere in your application.
     */
    public static ApplicationContext getApplicationContext() {
        if (ctx == null) {
            throw new IllegalStateException("ApplicationContext is not initialized yet.");
        }
        return ctx;
    }
    
   
}
