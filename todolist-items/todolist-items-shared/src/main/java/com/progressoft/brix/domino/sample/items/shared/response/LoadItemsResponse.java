package com.progressoft.brix.domino.sample.items.shared.response;

import com.progressoft.brix.domino.api.shared.request.ResponseBean;
import com.progressoft.brix.domino.sample.items.shared.TodoItem;

import java.util.List;

public class LoadItemsResponse implements ResponseBean {

    private List<Item> items;

    public LoadItemsResponse() {
    }

    public LoadItemsResponse(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item implements TodoItem{

        private String itemTitle;
        private String itemDescription;
        private boolean done;

        public Item() {
        }

        public Item(TodoItem todoItem){
            this.done=todoItem.isDone();
            this.itemTitle=todoItem.getItemTitle();
            this.itemDescription=todoItem.getItemDescription();
        }

        public Item(String itemTitle, String itemDescription, boolean done) {
            this.itemTitle = itemTitle;
            this.itemDescription = itemDescription;
            this.done = done;
        }

        @Override
        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
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
        public void toggle() {
            this.done=!this.done;
        }
    }
}
