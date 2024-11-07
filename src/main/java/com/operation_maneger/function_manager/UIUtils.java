package com.operation_maneger.function_manager;

import com.example.elements_models.config.FilePath;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class UIUtils {
    public static void setSlideAction(Node currentMenu, Node otherMenu, int slideX,
                                      int shoppingCartMenuX, AnchorPane shoppingCartMenu) {

        currentMenu.setOnMouseClicked(click -> {
            TranslateTransition slide = new TranslateTransition();

            setStartSlideActionToElement(slideX, shoppingCartMenu, slide);
            shoppingCartMenu.setTranslateX(shoppingCartMenuX);
            setEndSlideActionToElements(currentMenu, otherMenu, slide);
        });
    }

    public static void switchSceneAction(Node source, String fileName, String stageName, Class<?> clazz) {
        closeCurrentStage(source);
        try {
            loadNewSceneFile(fileName, stageName, clazz);
        } catch (Exception e) {
            throw new RuntimeException("No such scene is found!");
        }
    }

    private static void loadNewSceneFile(String fileName, String stageName, Class<?> clazz) throws IOException {
        String directory = FilePath.UI_FILE_DIR + fileName;
        Parent productView = getParent(clazz, directory);

        Stage stage = setUpStageSettings(stageName, productView);
        stage.show();
    }

    @NotNull
    private static Stage setUpStageSettings(String stageName, Parent productView) {
        Stage stage = new Stage();
        stage.setTitle(stageName);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(new Scene(productView));
        stage.initModality(Modality.APPLICATION_MODAL);

        return stage;
    }

    private static Parent getParent(Class<?> clazz, String directory) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(clazz.getResource(directory));
        return fxmlLoader.load();
    }

    private static void setStartSlideActionToElement(int slideX, AnchorPane shoppingCartMenu, TranslateTransition slide) {
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(shoppingCartMenu);

        slide.setToX(slideX);
        slide.play();
    }

    private static void setEndSlideActionToElements(Node currentMenu, Node otherMenu, TranslateTransition slide) {
        slide.setOnFinished((ActionEvent e) -> {
            currentMenu.setVisible(false);
            otherMenu.setVisible(true);
        });
    }

    private static void closeCurrentStage(Node source) {
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();
    }

    public static void alertMessage(String title, String headerText, String text, Alert.AlertType type) {
        Alert invalid = new Alert(type);

        invalid.setTitle(title);
        invalid.setHeaderText(headerText);
        invalid.setContentText(text);
        invalid.showAndWait();
    }
}
