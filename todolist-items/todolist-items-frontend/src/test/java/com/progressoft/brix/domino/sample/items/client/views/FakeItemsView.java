package com.progressoft.brix.domino.sample.items.client.views;

import com.progressoft.brix.domino.api.client.annotations.UiView;
import com.progressoft.brix.domino.sample.items.client.presenters.ItemsPresenter;
import com.progressoft.brix.domino.sample.items.shared.TodoItem;
import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutContext;

@UiView(presentable = ItemsPresenter.class)
public class FakeItemsView implements ItemsView {

    private LayoutContext.LayoutContent layoutContent= () -> null;
    private boolean addDialogOpen;
    private ItemsUiHandlers uiHandlers;

    @Override
    public void setUiHandlers(ItemsUiHandlers uiHandlers) {
        this.uiHandlers=uiHandlers;
    }

    @Override
    public void showAddDialog() {
        this.addDialogOpen=true;
    }

    @Override
    public LayoutContext.LayoutContent getContent() {
        return layoutContent;
    }


    @Override
    public void addItem(String title, String description, boolean done) {
        FakeTodoItem todoItem = new FakeTodoItem(title, description, done);
        uiHandlers.onSuccessCreation(todoItem);
    }

    @Override
    public void remove(TodoItem item) {
    }

    public boolean isAddDialogOpen() {
        return addDialogOpen;
    }

    private class FakeTodoItem implements TodoItem {

        private String title;
        private String description;
        private boolean done;

        FakeTodoItem(String title, String description, boolean done) {
            this.title = title;
            this.description = description;
            this.done = done;
        }

        @Override
        public boolean isDone() {
            return done;
        }

        @Override
        public String getItemTitle() {
            return title;
        }

        @Override
        public String getItemDescription() {
            return description;
        }

        @Override
        public void toggle() {
            this.done = !done;
        }
    }
}
