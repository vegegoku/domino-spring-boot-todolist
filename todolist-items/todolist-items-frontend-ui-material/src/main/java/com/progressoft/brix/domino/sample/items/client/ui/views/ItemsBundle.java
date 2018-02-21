package com.progressoft.brix.domino.sample.items.client.ui.views;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface ItemsBundle extends ClientBundle{

    interface Style extends CssResource {
        String done();
    }

    @Source("css/items.gss")
    Style style();

}