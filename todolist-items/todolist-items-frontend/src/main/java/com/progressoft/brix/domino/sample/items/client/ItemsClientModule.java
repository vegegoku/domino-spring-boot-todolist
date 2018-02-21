package com.progressoft.brix.domino.sample.items.client;

import com.google.gwt.core.client.EntryPoint;
import com.progressoft.brix.domino.api.client.ModuleConfigurator;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientModule(name="Items")
public class ItemsClientModule implements EntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemsClientModule.class);

	public void onModuleLoad() {
		LOGGER.info("Initializing Items frontend module ...");
		new ModuleConfigurator().configureModule(new ItemsModuleConfiguration());
	}
}
