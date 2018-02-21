package com.progressoft.brix.domino.sample.layout.client;

import com.progressoft.brix.domino.api.client.annotations.Contribute;
import com.progressoft.brix.domino.api.shared.extension.Contribution;
import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutContext;
import com.progressoft.brix.domino.sample.layout.shared.extension.LayoutExtensionPoint;

@Contribute
public class FakeLayoutContribution implements Contribution<LayoutExtensionPoint> {

    LayoutContext context;

    @Override
    public void contribute(LayoutExtensionPoint extensionPoint) {
        this.context=extensionPoint.context();
    }
}
