package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CoworkerDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Coworker Dashboard");

        // Buttons for coworker-specific functionality
        Button manageTasks = new Button("Manage Tasks");
        Button viewSchedule = new Button("View Schedule");
        Button reportIssues = new Button("Report Issues");
        Button logout = new Button("Logout");

        // Add functionality to buttons
        manageTasks.setOnAction(e -> System.out.println("Manage Tasks clicked"));
        viewSchedule.setOnAction(e -> System.out.println("View Schedule clicked"));
        reportIssues.setOnAction(e -> System.out.println("Report Issues clicked"));
        logout.setOnAction(e -> {
            // Redirect to the login page
            new LoginForm().start(primaryStage);
        });

        // Layout for the dashboard
        VBox layout = new VBox(10, manageTasks, viewSchedule, reportIssues, logout);
        layout.setPadding(new javafx.geometry.Insets(20));

        // Create the scene and set it on the stage
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}