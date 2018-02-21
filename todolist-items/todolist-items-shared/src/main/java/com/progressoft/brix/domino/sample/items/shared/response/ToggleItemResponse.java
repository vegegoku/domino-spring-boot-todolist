package com.progressoft.brix.domino.sample.items.shared.response;

import com.progressoft.brix.domino.api.shared.request.ResponseBean;

public class ToggleItemResponse implements ResponseBean {

    private boolean done;

    public ToggleItemResponse() {
    }

    public ToggleItemResponse(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}


