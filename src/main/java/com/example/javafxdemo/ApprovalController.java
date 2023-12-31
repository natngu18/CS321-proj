package com.example.javafxdemo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApprovalController {
    // Form Fields
    @FXML
    private TextField immigrantId;
    @FXML
    private TextField requesterName;
    @FXML
    private TextField requesterEmail;
    // multi-select menu for forms
    @FXML
    private ListView<String> multiSelectFormList;

    // Business Object passed to ApprovalController from ReviewController.
    private GeneaologyRequestApproval request;

    public ApprovalController() {}

    // Constructor to set value for business object.
    public ApprovalController(GeneaologyRequestApproval request) {
        this.request = request;
    }

    // initialize() function is automatically called upon creation of controller.
    public void initialize() {
        // Pre-populate the form fields based on the Business object, if its not null
        if (request != null) {
            immigrantId.setText(String.valueOf(request.getImmigrantId()));
            requesterName.setText(String.valueOf(request.getRequester().getName()));
            requesterEmail.setText(String.valueOf(request.getRequester().getEmail()));
            // Populate selected forms from review screen.
            for (Form form : request.getRequestedForms()) {
                String formName = form.getFormName();
                // set selected form
                multiSelectFormList.getSelectionModel().select(formName);
            }
        }
        // Set selection mode of forms to multiple (need to ctrl+click to select multiple)
        multiSelectFormList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // Populate options of forms to choose from
        ObservableList<String> forms = FXCollections.observableArrayList("Form 1", "Form 2", "Form 3");
        multiSelectFormList.setItems(forms);


    }

    public void submitButtonAction(ActionEvent actionEvent) throws IOException {
        // Approval doesn't have to do verification checks.

        ObservableList<String> selectedForms = multiSelectFormList.getSelectionModel().getSelectedItems();
        // Process the selected forms
        for (String item : selectedForms) {
            System.out.println("Selected form: " + item);
            // TODO: Would need to find these forms for the immigrant with "immigrantId" in database (in-memory database).
        }

        // Get the text from the TextField components
        String immigrantIdValue = immigrantId.getText();
        String requesterNameValue = requesterName.getText();
        String requesterEmailValue = requesterEmail.getText();

        // Process entered form fields:
        System.out.println("Immigrant ID: " + immigrantIdValue);
        System.out.println("Requester Name: " + requesterNameValue);
        System.out.println("Requester Email: " + requesterEmailValue);

        // TODO: Send email of requested forms..

        // Display confirmation alert.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Your request was successful.");
        alert.setHeaderText(null);
        alert.setContentText("Submit successful!");
        alert.showAndWait();

//        // TODO:  Approval should not be able to edit.
    }

    public void cancelButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("review.fxml"));
        Parent reviewView = loader.load();
        // new scene for Review screen
        Scene secondScene = new Scene(reviewView, 720, 720);
        // Get the stage from the current button
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        // Change title
        currentStage.setTitle("Review");
        // Set the new scene
        currentStage.setScene(secondScene);
    }

}