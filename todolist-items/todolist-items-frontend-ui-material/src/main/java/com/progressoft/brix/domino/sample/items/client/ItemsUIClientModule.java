package com.progressoft.brix.domino.sample.items.client;

import com.google.gwt.core.client.EntryPoint;
import com.progressoft.brix.domino.api.client.ModuleConfigurator;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import com.progressoft.brix.domino.sample.items.client.ui.views.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientModule(name="ItemsUI")
public class ItemsUIClientModule implements EntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemsUIClientModule.class);

	public void onModuleLoad() {
		LOGGER.info("Initializing Items frontend UI module ...");
		Bundle.INSTANCE.style().ensureInjected();
		new ModuleConfigurator().configureModule(new ItemsUIModuleConfiguration());
	}
}
