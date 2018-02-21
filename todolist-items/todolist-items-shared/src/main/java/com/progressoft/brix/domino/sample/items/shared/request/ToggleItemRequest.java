package com.progressoft.brix.domino.sample.items.shared.request;

import com.progressoft.brix.domino.api.shared.request.RequestBean;

public class ToggleItemRequest implements RequestBean{

    private String title;

    public ToggleItemRequest() {
    }

    public ToggleItemRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
