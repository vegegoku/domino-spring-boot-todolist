package com.progressoft.brix.domino.sample.items.shared.request;

import com.progressoft.brix.domino.api.shared.request.RequestBean;
import com.progressoft.brix.domino.sample.items.shared.TodoItem;

public class AddItemRequest implements RequestBean, TodoItem {

    private String itemTitle;
    private String itemDescription;
    private boolean done=false;

    public AddItemRequest() {
    }

    public AddItemRequest(TodoItem todoItem) {
        this.itemTitle = todoItem.getItemTitle();
        this.itemDescription = todoItem.getItemDescription();
        this.done = todoItem.isDone();
    }

    public AddItemRequest(String title, String itemDescription, boolean done) {
        this.itemTitle = title;
        this.itemDescription = itemDescription;
        this.done = done;
    }

    @Override
    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    @Override
    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public void toggle() {
        this.done=!this.done;
    }
}
