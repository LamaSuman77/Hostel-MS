package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WardenDashboard extends Application {

    private List<String> rooms = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Warden Dashboard");

        Button manageRooms = new Button("Manage Rooms");
        Button viewReports = new Button("View Reports");
        Button logout = new Button("Logout");

        manageRooms.setOnAction(e -> showRoomManagementDialog(primaryStage));

        viewReports.setOnAction(e -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("View Reports");
            alert.setHeaderText(null);
            alert.setContentText("Report viewing functionality goes here.");
            alert.showAndWait();
        });

        logout.setOnAction(e -> new LoginForm().start(primaryStage));

        VBox layout = new VBox(10, manageRooms, viewReports, logout);
        layout.setPadding(new javafx.geometry.Insets(20));
        layout.getStyleClass().add("vbox");

        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showRoomManagementDialog(Stage primaryStage) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Manage Rooms");

        ButtonType addButtonType = new ButtonType("Add Room", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType("Delete Room", ButtonBar.ButtonData.OK_DONE);
        ButtonType searchButtonType = new ButtonType("Search Room", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, deleteButtonType, searchButtonType, ButtonType.CLOSE);

        ListView<String> roomList = new ListView<>();
        roomList.getItems().addAll(rooms);

        // Add a search field
        TextField searchField = new TextField();
        searchField.setPromptText("Enter room number to search...");

        VBox dialogContent = new VBox(10, searchField, roomList);
        dialog.getDialogPane().setContent(dialogContent);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButtonType) {
                TextInputDialog addDialog = new TextInputDialog();
                addDialog.setTitle("Add Room");
                addDialog.setHeaderText(null);
                addDialog.setContentText("Enter room number:");
                addDialog.showAndWait().ifPresent(roomNumber -> {
                    rooms.add(roomNumber);
                    roomList.getItems().add(roomNumber);
                });
            } else if (buttonType == deleteButtonType) {
                String selectedRoom = roomList.getSelectionModel().getSelectedItem();
                if (selectedRoom != null) {
                    rooms.remove(selectedRoom);
                    roomList.getItems().remove(selectedRoom);
                }
            } else if (buttonType == searchButtonType) {
                String searchText = searchField.getText().trim();
                if (!searchText.isEmpty()) {
                    // Filter rooms based on the search text
                    List<String> filteredRooms = rooms.stream()
                            .filter(room -> room.contains(searchText))
                            .collect(Collectors.toList());

                    // Update the ListView with filtered rooms
                    roomList.getItems().clear();
                    roomList.getItems().addAll(filteredRooms);
                } else {
                    // If search field is empty, show all rooms
                    roomList.getItems().clear();
                    roomList.getItems().addAll(rooms);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }
}