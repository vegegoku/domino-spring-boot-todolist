package com.progressoft.brix.domino.sample.items.client.presenters;

import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutContext;

class MenuItem implements LayoutContext.LayoutMenuItem {

    private final String icon;
    private final String text;
    private final SelectHandler selectHandler;

    MenuItem(String icon, String text, SelectHandler selectHandler) {
        this.icon = icon;
        this.text = text;
        this.selectHandler = selectHandler;
    }

    @Override
    public String icon() {
        return icon;
    }

    @Override
    public String text() {
        return text;
    }

    @Override
    public SelectHandler selectHandler() {
        return selectHandler;
    }
}
