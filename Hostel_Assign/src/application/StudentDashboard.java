package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StudentDashboard extends Application {

    private List<String> maintenanceRequests = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Dashboard");

        Button viewRoom = new Button("View Room");
        Button requestMaintenance = new Button("Request Maintenance");
        Button logout = new Button("Logout");

        viewRoom.setOnAction(e -> System.out.println("View Room clicked"));
        requestMaintenance.setOnAction(e -> showMaintenanceRequestDialog(primaryStage));
        logout.setOnAction(e -> new LoginForm().start(primaryStage));

        VBox layout = new VBox(10, viewRoom, requestMaintenance, logout);
        layout.setPadding(new javafx.geometry.Insets(20));
        layout.getStyleClass().add("vbox");

        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMaintenanceRequestDialog(Stage primaryStage) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Request Maintenance");

        ButtonType addButtonType = new ButtonType("Add Request", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType("Delete Request", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, deleteButtonType, ButtonType.CLOSE);

        ListView<String> maintenanceList = new ListView<>();
        maintenanceList.getItems().addAll(maintenanceRequests);

        dialog.getDialogPane().setContent(maintenanceList);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButtonType) {
                TextInputDialog addDialog = new TextInputDialog();
                addDialog.setTitle("Add Maintenance Request");
                addDialog.setHeaderText(null);
                addDialog.setContentText("Enter maintenance request details:");
                addDialog.showAndWait().ifPresent(request -> {
                    maintenanceRequests.add(request);
                    maintenanceList.getItems().add(request);
                });
            } else if (buttonType == deleteButtonType) {
                String selectedRequest = maintenanceList.getSelectionModel().getSelectedItem();
                if (selectedRequest != null) {
                    maintenanceRequests.remove(selectedRequest);
                    maintenanceList.getItems().remove(selectedRequest);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }
}