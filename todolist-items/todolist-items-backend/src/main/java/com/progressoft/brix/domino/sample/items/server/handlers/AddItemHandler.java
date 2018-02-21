package com.progressoft.brix.domino.sample.items.server.handlers;

import com.progressoft.brix.domino.api.server.context.ExecutionContext;
import com.progressoft.brix.domino.api.server.handler.Handler;
import com.progressoft.brix.domino.api.server.handler.RequestHandler;
import com.progressoft.brix.domino.sample.items.server.TodoItemsStore;
import com.progressoft.brix.domino.sample.items.shared.request.AddItemRequest;
import com.progressoft.brix.domino.sample.items.shared.response.AddItemResponse;

import java.util.logging.Logger;

@Handler("todo/add")
public class AddItemHandler implements RequestHandler<AddItemRequest, AddItemResponse> {
    private static final Logger LOGGER = Logger.getLogger(AddItemHandler.class.getName());

    @Override
    public void handleRequest(ExecutionContext<AddItemRequest, AddItemResponse> executionContext) {
        AddItemRequest addItemRequest = executionContext.getRequestBean();
        TodoItemsStore.store(addItemRequest.getItemTitle(), addItemRequest);
        executionContext.end(new AddItemResponse(true));
    }
}
