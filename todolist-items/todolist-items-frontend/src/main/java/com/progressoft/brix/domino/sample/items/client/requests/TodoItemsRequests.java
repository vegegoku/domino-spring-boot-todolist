package com.progressoft.brix.domino.sample.items.client.requests;

import com.progressoft.brix.domino.api.client.annotations.Path;
import com.progressoft.brix.domino.api.client.annotations.RequestFactory;
import com.progressoft.brix.domino.api.client.request.Response;
import com.progressoft.brix.domino.sample.items.shared.request.AddItemRequest;
import com.progressoft.brix.domino.sample.items.shared.request.ToggleItemRequest;
import com.progressoft.brix.domino.sample.items.shared.response.AddItemResponse;
import com.progressoft.brix.domino.sample.items.shared.response.LoadItemsResponse;
import com.progressoft.brix.domino.sample.items.shared.response.RemoveResponse;
import com.progressoft.brix.domino.sample.items.shared.response.ToggleItemResponse;

import javax.ws.rs.HttpMethod;

@RequestFactory("todo/")
public interface TodoItemsRequests {

    @Path("add")
    Response<AddItemResponse> add(AddItemRequest addItemRequest);

    @Path("clear")
    Response<RemoveResponse> clear();

    @Path("remove-done")
    Response<RemoveResponse> clearDone();

    @Path(value = "items", method = HttpMethod.GET)
    Response<LoadItemsResponse> list();

    @Path("toggle")
    Response<ToggleItemResponse> toggle(ToggleItemRequest toggleItemRequest);
}
