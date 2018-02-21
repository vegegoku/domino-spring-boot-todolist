package com.progressoft.brix.domino.sample.layout.shared.extension;


import com.progressoft.brix.domino.api.shared.extension.Context;

public interface LayoutContext extends Context {
    void addMenuItem(LayoutMenuItem layoutMenuItem);

    void setContent(LayoutContent content);
    void closeMenu();


    void setOnCreateHandler(CreateItemHandler createItemHandler);

    interface LayoutMenuItem {
        String icon();
        String text();
        SelectHandler selectHandler();

        @FunctionalInterface
        interface SelectHandler {
            void onSelect();
        }
    }

    @FunctionalInterface
    interface LayoutContent<T> {
        T get();
    }

    @FunctionalInterface
    interface CreateItemHandler {
        void onCreate();
    }

    class MenuItemConnotBeNullException extends RuntimeException{
    }

    class ContentConnotBeNullException extends RuntimeException{
    }

    class HandlerConnotBeNullException extends RuntimeException{
    }
}
