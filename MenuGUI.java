package com.example.unogame;

import com.example.unogame.GUIs.SettingsGUI;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuGUI extends Application {

    private static Color DARK_PURPLE = Color.rgb(0, 0, 0, 0.5);

    public void start(Stage primaryStage) throws IOException {
        StackPane root = new StackPane();
        startGUI(root,primaryStage);
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void startGUI(StackPane root,Stage primaryStage) {
        root.setBackground(new Background(new BackgroundFill(Color.rgb(75,0,130), CornerRadii.EMPTY, Insets.EMPTY)));
        SettingsGUI settingsGUI = new SettingsGUI();
        VBox vBox = new VBox();
        vBox.setMaxSize(500, 500);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundFill(DARK_PURPLE,new CornerRadii(20),null)));


        Label logoLabel = createLogo();
        Button startButton = createButton("Start");
        Button exitButton = createButton("Exit");
        startButton.setCursor(javafx.scene.Cursor.HAND);
        exitButton.setCursor(javafx.scene.Cursor.HAND);

        VBox.setMargin(startButton, new Insets(40, 0, 25, 0));
        VBox.setMargin(exitButton, new Insets(0, 0, 5, 0));

        vBox.getChildren().addAll(logoLabel,startButton, exitButton);
        root.getChildren().add(vBox);

        exitButton.setOnAction(e -> {
            System.exit(0);
        });

        startButton.setOnAction(e -> {
            settingsGUI.start(primaryStage);
        });
    }

    private Label createLogo(){
        Label logoLabel = new Label();
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/unologo.png")));
        ImageView logoImageView = new ImageView(logoImage);
        logoLabel.setGraphic(logoImageView);
        return logoLabel;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setBackground(new Background(new BackgroundFill(DARK_PURPLE, new CornerRadii(20), Insets.EMPTY)));
        button.setPrefSize(400, 100);
        button.setTextFill(Color.WHITE);
        button.setFont(Font.font("System", FontWeight.BOLD, 19));
        return button;
    }

}
