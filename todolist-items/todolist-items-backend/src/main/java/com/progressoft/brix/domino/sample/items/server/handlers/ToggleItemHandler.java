package com.progressoft.brix.domino.sample.items.server.handlers;

import com.progressoft.brix.domino.api.server.context.ExecutionContext;
import com.progressoft.brix.domino.api.server.handler.Handler;
import com.progressoft.brix.domino.api.server.handler.RequestHandler;
import com.progressoft.brix.domino.sample.items.server.TodoItemsStore;
import com.progressoft.brix.domino.sample.items.shared.TodoItem;
import com.progressoft.brix.domino.sample.items.shared.request.ToggleItemRequest;
import com.progressoft.brix.domino.sample.items.shared.response.ToggleItemResponse;

import java.util.logging.Logger;

@Handler("todo/toggle")
public class ToggleItemHandler implements RequestHandler<ToggleItemRequest, ToggleItemResponse> {
    private static final Logger LOGGER = Logger.getLogger(ToggleItemHandler.class.getName());

    @Override
    public void handleRequest(ExecutionContext<ToggleItemRequest, ToggleItemResponse> executionContext) {
        TodoItem todoItem = TodoItemsStore.get(executionContext.getRequestBean().getTitle());
        todoItem.toggle();
        executionContext.end(new ToggleItemResponse(todoItem.isDone()));
    }
}
