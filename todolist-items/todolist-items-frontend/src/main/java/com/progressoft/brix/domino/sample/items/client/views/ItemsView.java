package com.progressoft.brix.domino.sample.items.client.views;

import com.progressoft.brix.domino.api.client.mvp.view.HasUiHandlers;
import com.progressoft.brix.domino.api.client.mvp.view.UiHandlers;
import com.progressoft.brix.domino.api.client.mvp.view.View;
import com.progressoft.brix.domino.sample.items.shared.TodoItem;

import static com.progressoft.brix.domino.sample.layout.shared.extension.LayoutContext.LayoutContent;


public interface ItemsView extends View, HasUiHandlers<ItemsView.ItemsUiHandlers>{
    void showAddDialog();

    LayoutContent getContent();


    void addItem(String title, String description, boolean done);

    void remove(TodoItem item);


    @FunctionalInterface
    interface NewItemHandler {
        void onNewItem(String title, String description);
    }

    interface SuccessAddHandler {
        void onSuccess(TodoItem item);
    }

    interface ItemsUiHandlers extends UiHandlers{
        void onNewItem(String title, String description);
        void onSuccessCreation(TodoItem item);
        void onItemStateChanged(TodoItem item);
    }
}