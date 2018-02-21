package com.progressoft.brix.domino.sample.layout.client.presenters;

import com.progressoft.brix.domino.api.shared.extension.MainContext;
import com.progressoft.brix.domino.sample.layout.client.views.LayoutView;
import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutContext;

public class LayoutPresenterSpy extends DefaultLayoutPresenter{

    private MainContext mainContext;
    public boolean receivedCreateEvent;
    private boolean viewRevealed;

    @Override
    public void contributeToMainModule(MainContext context) {
        super.contributeToMainModule(context);
        this.mainContext=context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.receivedCreateEvent=true;
    }

    public MainContext getMainContext() {
        return mainContext;
    }

    @Override
    protected String getConcrete() {
        return DefaultLayoutPresenter.class.getCanonicalName();
    }

    public LayoutContext getLayoutContext() {
        return this;
    }

    @Override
    public void onShow() {
        super.onShow();
        this.viewRevealed=true;
    }

    public LayoutView.LayoutUiHandlers getLayoutUiHandlers() {
        return this;
    }

    public boolean isViewRevealed() {
        return viewRevealed;
    }
}
