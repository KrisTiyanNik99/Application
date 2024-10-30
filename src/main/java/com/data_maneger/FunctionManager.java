package com.data_maneger;

import com.data_maneger.json_manager.JsonDataManager;
import com.example.elements_models.data_models.Product;
import com.example.elements_models.table_models.DeliveryTableView;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;

public class FunctionManager {
    /*
        A class with utility methods that will perform operational logic on Tables and usefully methods for operation
        login in the code
     */

    public static void saveAction(DeliveryTableView mainTable, String infoFileName) {
        List<Product> products = mainTable.getItems();
        JsonDataManager jsonManager = new JsonDataManager();
        jsonManager.saveInfoToJsonFile(products, infoFileName);
    }

    public static void saveAction(Product product, String fileName) {
        /*
            This method saves a finished product in the corresponding file in which we want to save it. We add it this
            way because we want the method to visually and permanently add the product to the data file that resides in
            the resources directory always. The file name must have it and the extension, otherwise the method will
            throw an exception.
         */

        JsonDataManager jsonDataManager = new JsonDataManager();
        jsonDataManager.saveInfoToJsonFile(product.toJsonFileFormat(), fileName);
    }

    public static void deleteActionFromTables(DeliveryTableView mainTable,
                                              TableView<Product> requestTable, Label productCounter) {

        Product selectedProducts = mainTable.getSelectionModel().getSelectedItem();
        mainTable.getItems().removeAll(selectedProducts);

        ObservableList<Product> selectedProductsRequest = requestTable.getItems();
        for (Product product : selectedProductsRequest) {
            if (selectedProducts.equals(product)) {
                requestTable.getItems().removeAll(product);

                int productNum = Integer.parseInt(productCounter.getText());
                productNum--;
                productCounter.setText(String.valueOf(productNum));
                break;
            }
        }
    }

    public static void deleteElementFromMap(DeliveryTableView mainTable, String infoFileName,
                                            Map<String, List<Product>> productDataByFile) {

        List<Product> productList = mainTable.getItems();
        productDataByFile.get(infoFileName).removeIf(value -> !productList.contains(value));
    }

    public static void setSlideAction(Node currentMenu, Node otherMenu, int slideX,
                                      int shoppingCartMenuX, AnchorPane shoppingCartMenu) {

        currentMenu.setOnMouseClicked(click -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(shoppingCartMenu);

            slide.setToX(slideX);
            slide.play();

            shoppingCartMenu.setTranslateX(shoppingCartMenuX);

            slide.setOnFinished((ActionEvent e) -> {
                currentMenu.setVisible(false);
                otherMenu.setVisible(true);
            });
        });
    }

    public static void setAddingActionEventToCheckBox(CheckBox box, Product product,
                                                      TableView<Product> requestTable, Label productCounter) {

        box.setOnAction(event -> {
            int productNum = Integer.parseInt(productCounter.getText());
            boolean productExists = checkForMatch(product, requestTable);


            if (box.isSelected()) {

                if (!productExists) {
                    productNum++;
                    requestTable.getItems().add(product);
                }
            } else {
                productNum--;
                requestTable.getItems().removeIf(existingProduct -> existingProduct.getName().equals(product.getName()));
                requestTable.refresh();
            }

            productCounter.setText(String.valueOf(productNum));
        });
    }

    public static void alertMessage(String title, String headerText, String text, Alert.AlertType type) {
        Alert invalid = new Alert(type);

        invalid.setTitle(title);
        invalid.setHeaderText(headerText);
        invalid.setContentText(text);
        invalid.showAndWait();
    }

    public static double formatToTwoDecimalPlaces(String numberStr) {
        try {
            double number = Double.parseDouble(numberStr);

            return Double.parseDouble(String.format("%.2f", number));
        } catch (NumberFormatException e) {
            return 0.00;
        }
    }

    public static void switchSceneAction(Node source, String fileName, String stageName, Class<?> clazz) {

        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(clazz.getResource("/com/example/application/" + fileName));
            Parent productView = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle(stageName);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.setScene(new Scene(productView));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("No such scene is founded!");
        }
    }

    private static boolean checkForMatch(Product product, TableView<Product> requestTable) {

        return requestTable.getItems().stream()
                .anyMatch(p -> product.getName().equals(p.getName()));
    }
}
