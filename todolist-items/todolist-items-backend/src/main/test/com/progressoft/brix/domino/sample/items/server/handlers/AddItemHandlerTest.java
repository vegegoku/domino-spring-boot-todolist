package com.progressoft.brix.domino.sample.items.server.handlers;

import com.progressoft.brix.domino.sample.items.server.TodoItemsStore;
import com.progressoft.brix.domino.sample.items.shared.request.AddItemRequest;
import com.progressoft.brix.domino.sample.items.shared.request.LoadItemsRequest;
import com.progressoft.brix.domino.sample.items.shared.request.RemoveRequest;
import com.progressoft.brix.domino.sample.items.shared.request.ToggleItemRequest;
import com.progressoft.brix.domino.test.api.DominoTestServer;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Domino uses Vertx by default to create an Http server, so when we test our endpoints we use vertx-unit.
 * @see <a href="http://vertx.io/docs/vertx-unit/java/">Vertx unit</a>
 */
@RunWith(VertxUnitRunner.class)
public class AddItemHandlerTest {

    private Vertx vertx;

    @Rule
    public RunTestOnContext vertxRule = new RunTestOnContext();
    private DominoTestServer.HttpServerContext serverContext;

    /**
     * Domino provides a way to start up a test domino server with two handlers that works as hooks to obtain a domino test context
     * we start the domino test server by supplying a vertx instance, we use the onBeforeLoad method to do things before the application domino modules are loaded
     * or to obtain a DominoTestContext object, the onAfterLoad method can be used to do stuff after the modules are loaded and to obtain a DominoTestContext and the HttpServerContext
     * @param testContext
     * @throws Exception
     */
    @Before
    public void setUp(TestContext testContext) throws Exception {
        vertx=vertxRule.vertx();
        DominoTestServer.vertx(vertx)
                .onBeforeLoad(dominoTestContext -> storeTestItems())
                .onAfterLoad((dominoTestContext, httpServerContext) -> serverContext=httpServerContext)
                .start(testContext);
    }

    /**
     * just initialize the store with 10 items
     */
    private void storeTestItems() {
        for(int i=0;i<10;i++){
            AddItemRequest addItemRequest=new AddItemRequest("item"+i, "itemDesc"+i, i>4);
            TodoItemsStore.store(addItemRequest.getItemTitle(), addItemRequest);
        }
    }

    /**
     *
     * @param testContext
     * @throws Exception
     */
    @Test
    public void whenAddItemRequestIsSent_thenTodoItemShouldBeAddedToTheStore(TestContext testContext) throws Exception {
        Async async=testContext.async();
        AddItemRequest addItemRequest=new AddItemRequest("item1Title", "item1Desc", false);
        serverContext.makeServiceRequest("add")
                .sendJson(addItemRequest, event -> {
                    assertThat(TodoItemsStore.get("item1Title")).isNotNull();
                    assertThat(TodoItemsStore.get("item1Title").isDone()).isFalse();
                    assertThat(TodoItemsStore.get("item1Title").getItemTitle()).isEqualTo("item1Title");
                    assertThat(TodoItemsStore.get("item1Title").getItemDescription()).isEqualTo("item1Desc");
                    async.complete();
                });
    }

    @Test
    public void whenClearAllRequestIsSent_thenTodoItemStoreShouldBeEmpty(TestContext testContext) throws Exception {
        Async async=testContext.async();
        serverContext.makeServiceRequest("clear").putHeader("REQUEST_KEY", RemoveRequest.class.getCanonicalName()+"_clear")
                .sendJson(new RemoveRequest(), event -> {
                    assertThat(TodoItemsStore.all()).isEmpty();
                    async.complete();
                });
    }

    @Test
    public void whenClearDoneRequestIsSent_thenDoneTodoItemsShouldBeRemovedFromStore(TestContext testContext) throws Exception {
        Async async=testContext.async();
        serverContext.makeServiceRequest("removeDone").putHeader("REQUEST_KEY", RemoveRequest.class.getCanonicalName()+"_removeDone")
                .sendJson(new RemoveRequest(), event -> {
                    assertThat(TodoItemsStore.all().size()).isEqualTo(5);
                    TodoItemsStore.all().forEach(item -> assertThat(item.isDone()).isFalse());
                    async.complete();
                });
    }

    @Test
    public void whenLoadItemRequestIsSent_thenShouldReceiveAllItemsInStore(TestContext testContext) throws Exception {
        Async async=testContext.async();
        serverContext.makeServiceRequest("items")
                .sendJson(new LoadItemsRequest(), event -> {
                    assertThat(TodoItemsStore.all().size()).isEqualTo(10);
                    async.complete();
                });
    }

    @Test
    public void whenToggleItemRequestIsSent_thenItemDoneStateShouldChange(TestContext testContext) throws Exception {
        Async async1=testContext.async();
        serverContext.makeServiceRequest("toggle")
                .sendJson(new ToggleItemRequest("item0"), event -> {
                    assertThat(TodoItemsStore.get("item0").isDone()).isTrue();
                    async1.complete();
                });
        Async async2=testContext.async();
        serverContext.makeServiceRequest("toggle")
                .sendJson(new ToggleItemRequest("item9"), event -> {
                    assertThat(TodoItemsStore.get("item9").isDone()).isFalse();
                    async2.complete();
                });
    }

    @After
    public void tearDown() throws Exception {
        TodoItemsStore.clear();
    }
}
