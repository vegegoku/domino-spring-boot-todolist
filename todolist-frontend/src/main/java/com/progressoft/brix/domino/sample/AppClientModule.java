package com.progressoft.brix.domino.sample;

import com.google.gwt.core.client.EntryPoint;
import com.progressoft.brix.domino.api.client.ClientApp;

import java.util.logging.Logger;

public class AppClientModule implements EntryPoint {

    private static final Logger LOGGER = Logger.getLogger(AppClientModule.class.getName());

    public void onModuleLoad() {
        LOGGER.info("todo-list Application frontend have been initialized.");
        ClientApp.make().run();

    }
}
