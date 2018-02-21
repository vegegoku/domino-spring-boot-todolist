package com.progressoft.brix.domino.sample.items.server.handlers;

import com.progressoft.brix.domino.api.server.context.ExecutionContext;
import com.progressoft.brix.domino.api.server.handler.Handler;
import com.progressoft.brix.domino.api.server.handler.RequestHandler;
import com.progressoft.brix.domino.api.shared.request.VoidRequest;
import com.progressoft.brix.domino.sample.items.server.TodoItemsStore;
import com.progressoft.brix.domino.sample.items.shared.response.RemoveResponse;

import java.util.logging.Logger;

@Handler(value = "todo/clear")
public class ClearAllHandler implements RequestHandler<VoidRequest, RemoveResponse> {
    private static final Logger LOGGER = Logger.getLogger(ClearAllHandler.class.getName());

    @Override
    public void handleRequest(ExecutionContext<VoidRequest, RemoveResponse> executionContext) {
        try {
            TodoItemsStore.clear();
            executionContext.end(new RemoveResponse(true));
        } catch (Exception e) {
            executionContext.end(new RemoveResponse(false));
        }
    }
}
