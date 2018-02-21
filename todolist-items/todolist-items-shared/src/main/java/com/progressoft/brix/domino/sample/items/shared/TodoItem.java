package com.progressoft.brix.domino.sample.items.shared;

public interface TodoItem {

    boolean isDone();

    String getItemTitle();

    String getItemDescription();

    void toggle();
}
