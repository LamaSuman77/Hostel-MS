package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ReceptionistDashboard extends Application {

    // Lists to store check-in and check-out records
    private List<String> checkIns = new ArrayList<>();
    private List<String> checkOuts = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Receptionist Dashboard");

        // Buttons for receptionist-specific functionality
        Button manageCheckIns = new Button("Manage Check-Ins");
        Button manageCheckOuts = new Button("Manage Check-Outs");
        Button viewRoomAvailability = new Button("View Room Availability");
        Button logout = new Button("Logout");

        // Set button styles using CSS classes
        manageCheckIns.getStyleClass().add("custom-button");
        manageCheckOuts.getStyleClass().add("custom-button");
        viewRoomAvailability.getStyleClass().add("custom-button");
        logout.getStyleClass().add("logout-button");

        // Add functionality to buttons
        manageCheckIns.setOnAction(e -> showCheckInManagementDialog(primaryStage));
        manageCheckOuts.setOnAction(e -> showCheckOutManagementDialog(primaryStage));
        viewRoomAvailability.setOnAction(e -> System.out.println("View Room Availability clicked"));
        logout.setOnAction(e -> new LoginForm().start(primaryStage));

        // Layout for the dashboard
        VBox layout = new VBox(10, manageCheckIns, manageCheckOuts, viewRoomAvailability, logout);
        layout.setPadding(new javafx.geometry.Insets(20));
        layout.getStyleClass().add("vbox");

        // Create the scene and set it on the stage
        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Displays a dialog for managing check-ins.
     *
     * @param primaryStage The primary stage of the application.
     */
    private void showCheckInManagementDialog(Stage primaryStage) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Manage Check-Ins");

        // Add buttons for adding and deleting check-ins
        ButtonType addButtonType = new ButtonType("Add Check-In", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType("Delete Check-In", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, deleteButtonType, ButtonType.CLOSE);

        // ListView to display check-ins
        ListView<String> checkInList = new ListView<>();
        checkInList.getItems().addAll(checkIns);

        dialog.getDialogPane().setContent(checkInList);

        // Handle button actions
        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButtonType) {
                // Show a dialog to add a new check-in
                TextInputDialog addDialog = new TextInputDialog();
                addDialog.setTitle("Add Check-In");
                addDialog.setHeaderText(null);
                addDialog.setContentText("Enter check-in details:");
                addDialog.showAndWait().ifPresent(checkIn -> {
                    checkIns.add(checkIn);
                    checkInList.getItems().add(checkIn);
                });
            } else if (buttonType == deleteButtonType) {
                // Delete the selected check-in
                String selectedCheckIn = checkInList.getSelectionModel().getSelectedItem();
                if (selectedCheckIn != null) {
                    checkIns.remove(selectedCheckIn);
                    checkInList.getItems().remove(selectedCheckIn);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    /**
     * Displays a dialog for managing check-outs.
     *
     * @param primaryStage The primary stage of the application.
     */
    private void showCheckOutManagementDialog(Stage primaryStage) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Manage Check-Outs");

        // Add buttons for adding and deleting check-outs
        ButtonType addButtonType = new ButtonType("Add Check-Out", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType("Delete Check-Out", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, deleteButtonType, ButtonType.CLOSE);

        // ListView to display check-outs
        ListView<String> checkOutList = new ListView<>();
        checkOutList.getItems().addAll(checkOuts);

        dialog.getDialogPane().setContent(checkOutList);

        // Handle button actions
        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButtonType) {
                // Show a dialog to add a new check-out
                TextInputDialog addDialog = new TextInputDialog();
                addDialog.setTitle("Add Check-Out");
                addDialog.setHeaderText(null);
                addDialog.setContentText("Enter check-out details:");
                addDialog.showAndWait().ifPresent(checkOut -> {
                    checkOuts.add(checkOut);
                    checkOutList.getItems().add(checkOut);
                });
            } else if (buttonType == deleteButtonType) {
                // Delete the selected check-out
                String selectedCheckOut = checkOutList.getSelectionModel().getSelectedItem();
                if (selectedCheckOut != null) {
                    checkOuts.remove(selectedCheckOut);
                    checkOutList.getItems().remove(selectedCheckOut);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }
}