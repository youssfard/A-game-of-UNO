package com.example.unogame.GUIs;

import com.example.unogame.MenuGUI;
import com.example.unogame.Utils.Settings;
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

public class SettingsGUI {

    private static final Color DARK_PURPLE = Color.rgb(0, 0, 0, 0.5);
    private static final Color BACKGROUND_PURPLE = Color.rgb(75, 0, 130);

    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(BACKGROUND_PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setMaxSize(500, 600);
        layout.setBackground(new Background(new BackgroundFill(DARK_PURPLE, new CornerRadii(20), null)));

        HBox backButton = createBackButton(primaryStage);
        Label rulesLabel = createSectionLabel("Rules");

        Spinner<Integer> botSpinner = new Spinner<>(1, 3, 1);
        HBox botSelection = createOption("Select the number of bots: ", botSpinner);

        CheckBox checkBox2s = new CheckBox();
        HBox stacking2s = createOption("Allow +2 stacking: ", checkBox2s);

        CheckBox checkBox4s = new CheckBox();
        HBox stacking4s = createOption("Allow +4 stacking: ", checkBox4s);

        Label cardsLabel = createSectionLabel("Special Cards");

        CheckBox swapBox = new CheckBox();
        HBox swapCards = createOption("Swap hands cards: ", swapBox);

        CheckBox stealBox = new CheckBox();
        HBox stealCards = createOption("Steal color cards: ", stealBox);

        CheckBox judgmentBox = new CheckBox();
        HBox judgmentCards = createOption("Judgement cards: ", judgmentBox);

        Button playButton = createPlayButton(primaryStage, botSpinner, checkBox2s, checkBox4s, swapBox, stealBox, judgmentBox);
        VBox.setMargin(playButton, new Insets(0, 20, 20, 0));
        layout.setAlignment(Pos.BOTTOM_RIGHT);

        layout.getChildren().addAll(
                backButton, rulesLabel, botSelection, stacking2s, stacking4s,
                cardsLabel, swapCards, stealCards, judgmentCards, playButton
        );

        root.getChildren().add(layout);
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createSectionLabel(String text) {
        Label label = new Label(text);
        label.setBackground(new Background(new BackgroundFill(DARK_PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
        label.setPadding(new Insets(10));
        label.setPrefSize(500, 30);
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("System", FontWeight.BOLD, 16));
        return label;
    }

    private <T extends Control> HBox createOption(String labelText, T control) {
        Label label = new Label(labelText);
        label.setFont(Font.font("System", FontWeight.NORMAL, 16));
        label.setTextFill(Color.WHITE);

        HBox box = new HBox(20, label, control);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private HBox createBackButton(Stage primaryStage) {
        Label backLabel = new Label();
        Image backIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/back.png")));
        ImageView backView = new ImageView(backIcon);
        backView.setFitHeight(30);
        backView.setPreserveRatio(true);
        backLabel.setGraphic(backView);
        backLabel.setCursor(javafx.scene.Cursor.HAND);
        backLabel.setOnMouseClicked(event -> {
            try {
                new MenuGUI().start(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox wrapper = new HBox(backLabel);
        wrapper.setAlignment(Pos.TOP_LEFT);
        VBox.setMargin(wrapper, new Insets(20, 0, -10, 20));
        return wrapper;
    }

    private Button createPlayButton(Stage primaryStage, Spinner<Integer> botSpinner, CheckBox cb2, CheckBox cb4, CheckBox swap, CheckBox steal, CheckBox judgment) {
        Button playButton = new Button("Play");
        playButton.setPrefSize(150, 50);
        playButton.setBackground(new Background(new BackgroundFill(Color.rgb(128, 0, 32), new CornerRadii(20), Insets.EMPTY)));
        playButton.setTextFill(Color.WHITE);
        playButton.setFont(Font.font("System", FontWeight.BOLD, 19));
        playButton.setCursor(javafx.scene.Cursor.HAND);

        playButton.setOnAction(e -> {
            Settings settings = new Settings();
            settings.setBots(botSpinner.getValue());
            settings.setStacking2s(cb2.isSelected());
            settings.setStacking4s(cb4.isSelected());
            settings.setSwapCards(swap.isSelected());
            settings.setStealCards(steal.isSelected());
            settings.setJudgementCards(judgment.isSelected());

            GUI gui = new GUI();
            gui.setSettings(settings);
            try {
                gui.start(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        return playButton;
    }
}
