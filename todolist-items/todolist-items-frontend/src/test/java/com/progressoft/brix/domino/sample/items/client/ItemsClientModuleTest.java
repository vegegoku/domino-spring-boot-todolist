package com.progressoft.brix.domino.sample.items.client;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import com.progressoft.brix.domino.api.client.extension.Contributions;
import com.progressoft.brix.domino.sample.items.client.presenters.ItemsPresenter;
import com.progressoft.brix.domino.sample.items.client.presenters.ItemsPresenterSpy;
import com.progressoft.brix.domino.sample.items.client.requests.*;
import com.progressoft.brix.domino.sample.items.client.views.FakeItemsView;
import com.progressoft.brix.domino.sample.items.client.views.ItemsView;
import com.progressoft.brix.domino.sample.items.shared.TodoItem;
import com.progressoft.brix.domino.sample.items.shared.response.AddItemResponse;
import com.progressoft.brix.domino.sample.items.shared.response.LoadItemsResponse;
import com.progressoft.brix.domino.sample.items.shared.response.RemoveResponse;
import com.progressoft.brix.domino.sample.items.shared.response.ToggleItemResponse;
import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutContext;
import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutExtensionPoint;
import com.progressoft.brix.domino.test.api.client.ClientContext;
import com.progressoft.brix.domino.test.api.client.DominoTestClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.progressoft.brix.domino.sample.items.shared.response.LoadItemsResponse.Item;
import static org.assertj.core.api.Assertions.assertThat;


@ClientModule(name = "TestItems")
@RunWith(GwtMockitoTestRunner.class)
public class ItemsClientModuleTest {

    private static final int DEFAULT_ITEMS_COUNT = 4;

    public static class FakeLayoutContext implements LayoutContext {

        private Map<String, LayoutMenuItem> meuItems = new HashMap<>();
        private CreateItemHandler createItemHandler;
        private LayoutContent content;

        @Override
        public void addMenuItem(LayoutMenuItem layoutMenuItem) {
            meuItems.put(layoutMenuItem.text(), layoutMenuItem);
        }

        @Override
        public void setContent(LayoutContent content) {
            this.content = content;
        }

        @Override
        public void closeMenu() {

        }

        @Override
        public void setOnCreateHandler(CreateItemHandler createItemHandler) {
            this.createItemHandler = createItemHandler;
        }

        Map<String, LayoutMenuItem> getMenuItems() {
            return meuItems;
        }

        CreateItemHandler getShowAddDialogHandler() {
            return createItemHandler;
        }

        LayoutContent getContent() {
            return content;
        }
    }

    private ItemsPresenterSpy presenterSpy;
    private FakeItemsView fakeView;
    private ClientContext clientContext;
    private FakeLayoutContext fakeLayoutContext;
    private ItemsView.ItemsUiHandlers uiHandlers;

    @Before
    public void setUp() {
        presenterSpy = new ItemsPresenterSpy();
        fakeLayoutContext = new FakeLayoutContext();
        uiHandlers=presenterSpy.getUiItemsHandlers();

        DominoTestClient.useModules(new ItemsModuleConfiguration(), new TestItemsModuleConfiguration())
                .replacePresenter(ItemsPresenter.class, presenterSpy)
                .viewOf(ItemsPresenter.class, view -> fakeView = (FakeItemsView) view)
                .onBeforeStart(this::fakeRequests)
                .onStartCompleted(clientContext -> {
                    this.clientContext = clientContext;
                    applyContributions();
                })
                .start();
    }

    private void fakeRequests(ClientContext context) {
        context.forRequest(TodoItemsRequestsFactory.TodoItemsRequests_list.class).returnResponse(makeLoadResponse());
    }

    private LoadItemsResponse makeLoadResponse() {

        List<LoadItemsResponse.Item> items = new ArrayList<>();
        items.add(new Item("item1", "description1", false));
        items.add(new Item("item2", "description2", true));
        items.add(new Item("item3", "description3", true));
        items.add(new Item("item4", "description4", false));

        return new LoadItemsResponse(items);
    }

    private void applyContributions() {
        Contributions.apply(LayoutExtensionPoint.class, () -> fakeLayoutContext);
    }

    @Test
    public void givenNewItemAdded_whenAddItemHandlerIsCalled_thenShouldSendAddItemServerRequestAndItemShouldBeAddedToItemsList() throws Exception {
        clientContext.forRequest(TodoItemsRequestsFactory.TodoItemsRequests_add.class).returnResponse(new AddItemResponse(true));
        uiHandlers.onNewItem("title", "description");
        assertThat(clientContext.getDefaultRoutingListener().isSent(TodoItemsRequestsFactory.TodoItemsRequests_add.class)).isTrue();
        final TodoItem item = presenterSpy.getItem("title");
        assertThat(presenterSpy.getItems()).contains(item);
        assertThat(item).isNotNull();
        assertThat(item.getItemTitle()).isEqualTo("title");
        assertThat(item.getItemDescription()).isEqualTo("description");
        assertThat(item.isDone()).isFalse();
    }

    @Test
    public void givenAddItemRequestSent_whenResponseReturnedWithFalse_thenItemShouldNotBeAddedToItemsList() throws Exception {
        clientContext.forRequest(TodoItemsRequestsFactory.TodoItemsRequests_add.class).returnResponse(new AddItemResponse(false));
        uiHandlers.onNewItem("notAdded", "description");
        assertThat(clientContext.getDefaultRoutingListener().isSent(TodoItemsRequestsFactory.TodoItemsRequests_add.class)).isTrue();
        assertThat(presenterSpy.getItems()).doesNotContain(presenterSpy.getItem("notAdded"));
    }

    @Test
    public void givenRegisteredItemStateHandler_whenItemStateChangedAndHandlerIsFired_thenShouldSendToggleItemRequest() throws Exception {
        clientContext.forRequest(TodoItemsRequestsFactory.TodoItemsRequests_add.class).returnResponse(new AddItemResponse(true));
        clientContext.forRequest(TodoItemsRequestsFactory.TodoItemsRequests_toggle.class).returnResponse(new ToggleItemResponse(true));
        uiHandlers.onNewItem("added", "description");
        uiHandlers.onItemStateChanged(presenterSpy.getItem("added"));
        assertThat(clientContext.getDefaultRoutingListener().isSent(TodoItemsRequestsFactory.TodoItemsRequests_toggle.class)).isTrue();
        TodoItemsRequestsFactory.TodoItemsRequests_toggle
                request = clientContext.getDefaultRoutingListener().getRequest(TodoItemsRequestsFactory.TodoItemsRequests_toggle.class);
        assertThat(request.requestBean().getTitle()).isEqualTo("added");
        assertThat(presenterSpy.getItem("added").isDone()).isTrue();
    }

    @Test
    public void givenModule_whenLayoutContextReceived_thenMenuItemsShouldBeAdded() throws Exception {
        assertThat(fakeLayoutContext.getMenuItems().get("Clear All")).isNotNull();
        assertThat(fakeLayoutContext.getMenuItems().get("Clear All").icon()).isEqualTo("delete");
        assertThat(fakeLayoutContext.getMenuItems().get("Clear All").selectHandler()).isNotNull();

        assertThat(fakeLayoutContext.getMenuItems().get("Clear Done")).isNotNull();
        assertThat(fakeLayoutContext.getMenuItems().get("Clear Done").icon()).isEqualTo("clear");
        assertThat(fakeLayoutContext.getMenuItems().get("Clear Done").selectHandler()).isNotNull();

        assertThat(fakeLayoutContext.getMenuItems().get("Refresh")).isNotNull();
        assertThat(fakeLayoutContext.getMenuItems().get("Refresh").icon()).isEqualTo("refresh");
        assertThat(fakeLayoutContext.getMenuItems().get("Refresh").selectHandler()).isNotNull();

//        assertThat(fakeLayoutContext.getMenuItems().get("Settings")).isNotNull();
//        assertThat(fakeLayoutContext.getMenuItems().get("Settings").icon()).isEqualTo("settings");
//        assertThat(fakeLayoutContext.getMenuItems().get("Settings").selectHandler()).isNotNull();
//
//        assertThat(fakeLayoutContext.getMenuItems().get("Help")).isNotNull();
//        assertThat(fakeLayoutContext.getMenuItems().get("Help").icon()).isEqualTo("help");
//        assertThat(fakeLayoutContext.getMenuItems().get("Help").selectHandler()).isNotNull();
    }

    @Test
    public void givenModule_whenLayoutContextReceived_thenLayoutContentShouldSetToViewContent() throws Exception {
        assertThat(fakeLayoutContext.getContent()).isEqualTo(fakeView.getContent());

    }

    @Test
    public void givenModule_whenLayoutContextReceived_thenShowAddNewItemDialogHandlerShouldBeSetOnView() throws Exception {
        assertThat(fakeLayoutContext.getShowAddDialogHandler()).isNotNull();
    }

    @Test
    public void givenModule_whenShowAddNewItemDialogHandlerTriggered_thenViewShouldShowAddDialog() throws Exception {
        fakeLayoutContext.getShowAddDialogHandler().onCreate();
        assertThat(fakeView.isAddDialogOpen()).isTrue();
    }

    @Test
    public void givenModule_whenLayoutContextReceived_thenShouldSendLoadTodoItemsRequest() throws Exception {
        assertThat(clientContext.getDefaultRoutingListener().isSent(TodoItemsRequestsFactory.TodoItemsRequests_list.class)).isTrue();
        assertThat(presenterSpy.getItem("item1")).isNotNull();
        assertThat(presenterSpy.getItem("item1").getItemDescription()).isEqualTo("description1");
        assertThat(presenterSpy.getItem("item1").isDone()).isFalse();

        assertThat(presenterSpy.getItem("item2")).isNotNull();
        assertThat(presenterSpy.getItem("item2").getItemDescription()).isEqualTo("description2");
        assertThat(presenterSpy.getItem("item2").isDone()).isTrue();

        assertThat(presenterSpy.getItem("item3")).isNotNull();
        assertThat(presenterSpy.getItem("item3").getItemDescription()).isEqualTo("description3");
        assertThat(presenterSpy.getItem("item3").isDone()).isTrue();

        assertThat(presenterSpy.getItem("item4")).isNotNull();
        assertThat(presenterSpy.getItem("item4").getItemDescription()).isEqualTo("description4");
        assertThat(presenterSpy.getItem("item4").isDone()).isFalse();
    }

    @Test
    public void givenMenuItemsAdded_whenClearDoneIsTriggered_thenDoneItemsShouldBeRemoved() throws Exception {
        clientContext.forRequest(TodoItemsRequestsFactory.TodoItemsRequests_clearDone.class).returnResponse(new RemoveResponse(true));
        fakeLayoutContext.getMenuItems().get("Clear Done").selectHandler().onSelect();
        assertThat(clientContext.getDefaultRoutingListener().isSent(TodoItemsRequestsFactory.TodoItemsRequests_clearDone.class)).isTrue();
        assertThat(presenterSpy.getItems().stream().filter(TodoItem::isDone).collect(Collectors.toSet())).isEmpty();
        assertThat(presenterSpy.getItem("item2")).isNull();
        assertThat(presenterSpy.getItem("item3")).isNull();
    }

    @Test
    public void givenMenuItemsAdded_whenClearAllIsTriggeredAndTrueReturned_thenAllItemsShouldBeRemoved() throws Exception {
        clientContext.forRequest(TodoItemsRequestsFactory.TodoItemsRequests_clear.class).returnResponse(new RemoveResponse(true));
        fakeLayoutContext.getMenuItems().get("Clear All").selectHandler().onSelect();
        assertThat(clientContext.getDefaultRoutingListener().isSent(TodoItemsRequestsFactory.TodoItemsRequests_clear.class)).isTrue();
        assertThat(presenterSpy.getItems()).isEmpty();
    }

    @Test
    public void givenMenuItemsAdded_whenClearAllIsTriggeredAndFalseReturned_thenAllItemsShouldBeRemoved() throws Exception {
        clientContext.forRequest(TodoItemsRequestsFactory.TodoItemsRequests_clear.class).returnResponse(new RemoveResponse(false));
        fakeLayoutContext.getMenuItems().get("Clear All").selectHandler().onSelect();
        assertThat(clientContext.getDefaultRoutingListener().isSent(TodoItemsRequestsFactory.TodoItemsRequests_clear.class)).isTrue();
        assertThat(presenterSpy.getItems().size()).isEqualTo(DEFAULT_ITEMS_COUNT);
    }

    @Test
    public void givenAddedMenuItems_whenRefresh_thenShouldReloadItems() throws Exception {
        Item item5 = new Item("item5", "description5", false);
        Item item6 = new Item("item6", "description6", false);
        presenterSpy.getItems().add(item5);
        presenterSpy.getItems().add(item6);
        fakeLayoutContext.getMenuItems().get("Refresh").selectHandler().onSelect();
        assertThat(presenterSpy.getItems().size()).isEqualTo(DEFAULT_ITEMS_COUNT);
        assertThat(presenterSpy.getItems()).doesNotContain(item5, item6);
    }
}
