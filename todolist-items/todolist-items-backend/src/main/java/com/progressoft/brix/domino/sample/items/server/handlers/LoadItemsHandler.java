package com.progressoft.brix.domino.sample.items.server.handlers;

import com.progressoft.brix.domino.api.server.context.ExecutionContext;
import com.progressoft.brix.domino.api.server.handler.Handler;
import com.progressoft.brix.domino.api.server.handler.RequestHandler;
import com.progressoft.brix.domino.api.shared.request.VoidRequest;
import com.progressoft.brix.domino.sample.items.server.TodoItemsStore;
import com.progressoft.brix.domino.sample.items.shared.response.LoadItemsResponse;

import java.util.List;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

@Handler("todo/items")
public class LoadItemsHandler implements RequestHandler<VoidRequest, LoadItemsResponse> {
    private static final Logger LOGGER = Logger.getLogger(LoadItemsHandler.class.getName());

    @Override
    public void handleRequest(ExecutionContext<VoidRequest, LoadItemsResponse> executionContext) {
        executionContext.end(new LoadItemsResponse(getItems()));
    }

    private List<LoadItemsResponse.Item> getItems() {
        return TodoItemsStore.all().stream().map(LoadItemsResponse.Item::new).collect(toList());
    }
}
