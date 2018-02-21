package com.progressoft.brix.domino.sample.items.client.presenters;

import com.progressoft.brix.domino.sample.items.client.views.ItemsView;
import com.progressoft.brix.domino.sample.items.shared.TodoItem;
import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutContext;

import java.util.List;

public class ItemsPresenterSpy extends DefaultItemsPresenter{

    private LayoutContext layoutContext;

    @Override
    public void contributeToLayoutModule(LayoutContext context) {
        super.contributeToLayoutModule(context);
        this.layoutContext=context;
    }

    public ItemsView.ItemsUiHandlers getUiItemsHandlers(){
        return this;
    }

    public TodoItem getItem(String title) {
        return todoItems.stream().filter(i->i.getItemTitle().equals(title)).findFirst().orElse(null);
    }

    @Override
    protected String getConcrete() {
        return DefaultItemsPresenter.class.getCanonicalName();
    }

    public LayoutContext getLayoutContext() {
        return layoutContext;
    }

    public List<TodoItem> getItems() {
        return todoItems;
    }
}
