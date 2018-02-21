package com.progressoft.brix.domino.sample.items.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

public class NewItemDialog extends Composite {

    MaterialModal modal;

    @UiField
    MaterialTextBox titleField;

    @UiField
    MaterialTextArea descriptionField;

    @UiField
    MaterialButton addButton;

    @UiField
    MaterialButton cancelButton;

    interface NewItemDialogUiBinder extends UiBinder<MaterialModal, NewItemDialog> {}

    private static NewItemDialogUiBinder uiBinder = GWT.create(NewItemDialogUiBinder.class);

    public NewItemDialog() {
        modal = uiBinder.createAndBindUi(this);
        initWidget(modal);
    }

    @UiHandler("cancelButton")
    void onCancel(ClickEvent event){
        modal.close();
    }
}
