package com.progressoft.brix.domino.sample.layout.client.views;

import com.progressoft.brix.domino.api.client.mvp.view.HasUiHandlers;
import com.progressoft.brix.domino.api.client.mvp.view.UiHandlers;
import com.progressoft.brix.domino.api.client.mvp.view.View;
import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutContext;

public interface LayoutView extends View, HasUiHandlers<LayoutView.LayoutUiHandlers> {

    void show();

    void addMenuItem(LayoutContext.LayoutMenuItem layoutMenuItem);

    void setContent(LayoutContext.LayoutContent content);

    void closeMenu();

    interface LayoutUiHandlers extends UiHandlers{
        void onShow();
        void onCreate();
    }
}