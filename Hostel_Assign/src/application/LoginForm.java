package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm extends Application {

    final static String SERVER = "localhost";
    final static String USER = "root";
    final static String PASS = "Official@00";
    final static Integer PORT = 3306;
    final static String dDB_NAME = "HostelManagementSystem"; // Correct database name
    final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    final static String URL = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + dDB_NAME + "?useSSL=false&serverTimezone=UTC";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hostel Management System - Login");

        // Create the grid pane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add title text
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // Add username label and text field
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        // Add password label and password field
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        // Add login button
        Button btn = new Button("Sign in");
        grid.add(btn, 1, 4);

        // Add error message label
        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");
        grid.add(errorMessage, 1, 3);

        // Add action to the login button
        btn.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();

            // Validate login
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                String query = "SELECT role FROM Users WHERE username = ? AND password = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, username);
                pstmt.setString(2, password); // Use hashed passwords
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role");
                    System.out.println("Login successful! Role: " + role);

                    // Redirect to role-specific dashboard
                    switch (role) {
                        case "Warden":
                            new WardenDashboard().start(primaryStage);
                            break;
                        case "Coworker":
                            new CoworkerDashboard().start(primaryStage);
                            break;
                        case "Receptionist":
                            new ReceptionistDashboard().start(primaryStage);
                            break;
                        case "Student":
                            new StudentDashboard().start(primaryStage);
                            break;
                        default:
                            errorMessage.setText("Unknown role.");
                            break;
                    }
                } else {
                    errorMessage.setText("Invalid username or password.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                errorMessage.setText("Database error. Please try again.");
            }
        });

        // Create the scene and set it on the stage
        Scene scene = new Scene(grid, 350, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}