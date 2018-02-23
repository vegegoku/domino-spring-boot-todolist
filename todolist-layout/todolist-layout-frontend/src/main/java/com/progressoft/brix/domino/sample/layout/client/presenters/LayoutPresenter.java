package com.progressoft.brix.domino.sample.layout.client.presenters;

import com.progressoft.brix.domino.api.client.annotations.InjectContext;
import com.progressoft.brix.domino.api.client.annotations.Presenter;
import com.progressoft.brix.domino.api.client.mvp.presenter.BaseClientPresenter;
import com.progressoft.brix.domino.api.shared.extension.MainContext;
import com.progressoft.brix.domino.api.shared.extension.MainExtensionPoint;
import com.progressoft.brix.domino.sample.layout.client.views.LayoutView;
import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutContext;
import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutExtensionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Presenter
public class LayoutPresenter extends BaseClientPresenter<LayoutView> implements LayoutView.LayoutUiHandlers, LayoutContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(LayoutPresenter.class);
    private CreateItemHandler createItemHandler;

    @Override
    public void initView(LayoutView view) {
        view.setUiHandlers(this);
    }

    @InjectContext(extensionPoint=MainExtensionPoint.class)
    public void contributeToMainModule(MainContext context) {
        view.show();
    }

    @Override
    public void onShow() {
        applyContributions(LayoutExtensionPoint.class, () -> LayoutPresenter.this);
    }

    @Override
    public void onCreate() {
        if(nonNull(createItemHandler))
            this.createItemHandler.onCreate();
    }

    @Override
    public void addMenuItem(LayoutMenuItem layoutMenuItem) {
        if (isNull(layoutMenuItem))
            throw new MenuItemConnotBeNullException();
        view.addMenuItem(layoutMenuItem);
        LOGGER.info("Layout - adding menu item ["+layoutMenuItem.text()+"]");
    }

    @Override
    public void setContent(LayoutContent content) {
        if (isNull(content))
            throw new ContentConnotBeNullException();
        view.setContent(content);
        LOGGER.info("Layout - setting main content.");
    }

    @Override
    public void closeMenu() {
        view.closeMenu();
        LOGGER.info("Layout - menu closed");
    }

    @Override
    public void setOnCreateHandler(CreateItemHandler createItemHandler) {
        if (isNull(createItemHandler))
            throw new HandlerConnotBeNullException();
        this.createItemHandler=createItemHandler;
        LOGGER.info("Layout - setting add item handler.");
    }
}