package com.progressoft.brix.domino.sample.layout.client;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import com.progressoft.brix.domino.sample.layout.client.presenters.LayoutPresenter;
import com.progressoft.brix.domino.sample.layout.client.presenters.LayoutPresenterSpy;
import com.progressoft.brix.domino.sample.layout.client.views.FakeLayoutView;
import com.progressoft.brix.domino.sample.layout.client.views.LayoutView;
import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutContext;
import com.progressoft.brix.domino.test.api.client.DominoTestClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.progressoft.brix.domino.sample.layout.shared.extension.LayoutContext.*;
import static org.assertj.core.api.Assertions.assertThat;

@ClientModule(name = "TestLayout")
@RunWith(GwtMockitoTestRunner.class)
public class LayoutClientModuleTest {

    private LayoutPresenterSpy presenterSpy;
    private LayoutContext layoutContext;
    private LayoutView.LayoutUiHandlers layoutUiHandlers;
    private FakeLayoutView fakeView;

    private FakeLayoutContribution fakeLayoutContribution;
    private LayoutMenuItem testLayoutItem;

    @Before
    public void setUp() {
        presenterSpy = new LayoutPresenterSpy();
        layoutContext = presenterSpy.getLayoutContext();
        layoutUiHandlers = presenterSpy.getLayoutUiHandlers();
        DominoTestClient.useModules(new LayoutModuleConfiguration(), new TestLayoutModuleConfiguration())
                .replacePresenter(LayoutPresenter.class, presenterSpy)
                .viewOf(LayoutPresenter.class, view -> fakeView = (FakeLayoutView) view)
                .contributionOf(FakeLayoutContribution.class, contribution -> fakeLayoutContribution = contribution)
                .start();

        testLayoutItem = new LayoutMenuItem() {
            @Override
            public String icon() {
                return "icon";
            }

            @Override
            public String text() {
                return "layout item text";
            }

            @Override
            public SelectHandler selectHandler() {
                return () -> {};
            }
        };
    }

    @Test
    public void givenLayoutModule_whenContributingToMainExtensionPoint_thenShouldReceiveMainContextAndShowLayout() {
        assertThat(presenterSpy.getMainContext()).isNotNull();
        assertThat(presenterSpy.isViewRevealed()).isTrue();
    }

    @Test
    public void givenLayoutIsVisible_whenContributingToLayout_thenShouldReceiveLayoutContext() throws Exception {
        assertThat(fakeLayoutContribution.context).isNotNull();
    }

    @Test(expected = MenuItemConnotBeNullException.class)
    public void givenLayoutContext_whenAddNullMenuItem_thenShouldThrowException() throws Exception {
        layoutContext.addMenuItem(null);
    }

    @Test
    public void givenLayoutContext_whenAddMenuItem_thenMenuItemShouldBeAddedToLayoutView() throws Exception {
        layoutContext.addMenuItem(testLayoutItem);
        assertThat(fakeView.layoutMenuItems).contains(testLayoutItem);
        assertThat(fakeView.layoutMenuItems.size()).isEqualTo(1);
    }

    @Test
    public void givenLayoutContext_whenSetContent_thenContentShouldSetInLayoutView() throws Exception {
        final LayoutContent<Object> content= () -> null;
        layoutContext.setContent(content);
        assertThat(fakeView.content).isEqualTo(content);
    }

    @Test(expected = ContentConnotBeNullException.class)
    public void givenLayoutContext_whenSetNullContent_thenThrowException() throws Exception {
        layoutContext.setContent(null);
    }

    @Test
    public void givenLayoutContext_whenSetOnCreateHandlerAndViewCallsOnCreate_thenShouldShouldCallTheOnCreateHandler() throws Exception {

        final boolean[] handlerCalled = {false};
        CreateItemHandler createItemHandler= () -> handlerCalled[0] =true;
        layoutContext.setOnCreateHandler(createItemHandler);
        layoutUiHandlers.onCreate();
        assertThat(presenterSpy.receivedCreateEvent).isTrue();
        assertThat(handlerCalled[0]).isTrue();
    }

    @Test(expected = HandlerConnotBeNullException.class)
    public void givenLayoutContext_whenSetNullCreateHandler_thenShouldThrowException() throws Exception {
        layoutContext.setOnCreateHandler(null);
    }
}
