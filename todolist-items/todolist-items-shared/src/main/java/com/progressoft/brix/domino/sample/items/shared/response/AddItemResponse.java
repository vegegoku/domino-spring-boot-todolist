package com.progressoft.brix.domino.sample.items.shared.response;

import com.progressoft.brix.domino.api.shared.request.ResponseBean;

public class AddItemResponse implements ResponseBean {

    private boolean added;

    public AddItemResponse() {
    }

    public AddItemResponse(boolean added) {
        this.added = added;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
}
