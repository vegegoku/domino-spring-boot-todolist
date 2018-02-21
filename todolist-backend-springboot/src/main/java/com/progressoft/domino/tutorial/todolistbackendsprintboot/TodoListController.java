package com.progressoft.domino.tutorial.todolistbackendsprintboot;

import com.progressoft.brix.domino.api.server.context.DefaultExecutionContext;
import com.progressoft.brix.domino.api.server.context.ExecutionContext;
import com.progressoft.brix.domino.api.server.request.DefaultMultiMap;
import com.progressoft.brix.domino.api.server.request.DefaultRequestContext;
import com.progressoft.brix.domino.api.server.request.RequestContext;
import com.progressoft.brix.domino.api.shared.request.RequestBean;
import com.progressoft.brix.domino.api.shared.request.ResponseBean;
import com.progressoft.brix.domino.sample.items.server.handlers.*;
import com.progressoft.brix.domino.sample.items.shared.request.AddItemRequest;
import com.progressoft.brix.domino.sample.items.shared.request.ToggleItemRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/service/todo/")
@CrossOrigin(origins = {"*"})
public class TodoListController {

    private RequestContext requestContext;
    private SpringResponseContext responseContext;

    private <R extends RequestBean, S extends ResponseBean> ExecutionContext<R, S> createExecutionContext(R request, String requestURI) {
        requestContext = DefaultRequestContext.forRequest(request)
                .requestPath(requestURI)
                .headers(new DefaultMultiMap<>())
                .parameters(new DefaultMultiMap<>()).build();
        responseContext = new SpringResponseContext();
        return new DefaultExecutionContext<>(requestContext, responseContext);
    }

    @RequestMapping(value = "items", method = RequestMethod.GET)
    public ResponseEntity findAll(HttpServletRequest request) {
        new LoadItemsHandler().handleRequest(createExecutionContext(RequestBean.VOID_REQUEST, request.getRequestURI()));
        return ResponseEntity.ok(responseContext.getResponseBean());
    }

    @RequestMapping(value = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addItem(@RequestBody AddItemRequest request, HttpServletRequest httpServletRequest) {
        new AddItemHandler().handleRequest(createExecutionContext(request, httpServletRequest.getRequestURI()));
        return ResponseEntity.ok(responseContext.getResponseBean());
    }

    @RequestMapping(value = "clear", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity clear(HttpServletRequest httpServletRequest) {
        new ClearAllHandler().handleRequest(createExecutionContext(RequestBean.VOID_REQUEST, httpServletRequest.getRequestURI()));
        return ResponseEntity.ok(responseContext.getResponseBean());
    }

    @RequestMapping(value = "remove-done", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeDone(HttpServletRequest httpServletRequest) {
        new ClearDoneHandler().handleRequest(createExecutionContext(RequestBean.VOID_REQUEST, httpServletRequest.getRequestURI()));
        return ResponseEntity.ok(responseContext.getResponseBean());
    }

    @RequestMapping(value = "toggle", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity toggleItem(@RequestBody ToggleItemRequest request, HttpServletRequest httpServletRequest) {
        new ToggleItemHandler().handleRequest(createExecutionContext(request, httpServletRequest.getRequestURI()));
        return ResponseEntity.ok(responseContext.getResponseBean());
    }
}
