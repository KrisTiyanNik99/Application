package com.data_maneger;

import com.example.application.AddProductView;
import com.example.application.MainController;
import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;
import com.example.data_models.table_models.DeliveryTableView;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public static void saveAction(DeliveryTableView mainTable) {
        
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

    private static boolean checkForMatch(Product product, TableView<Product> requestTable) {

        return requestTable.getItems().stream().anyMatch(p -> product.getName().equals(p.getName()));
    }

    public static Class<?> checkTableClassType(ObservableList<Product> products) {
        if (products.isEmpty()) {
            throw new IllegalArgumentException("The product list is empty!");
        }

        // We take from first element our DataType and check if all rest elements match it
        DataType classType = products.getFirst().getType();

        boolean allMatch = products.stream()
                .map(Product::getType)
                .allMatch(type -> type.equals(classType));

        if (!allMatch) {
            throw new IllegalArgumentException("Different data types in one file!");
        }

        return ProductFactory.getClassByType(classType);
    }

    public static double formatToTwoDecimalPlaces(String numberStr) {
        try {
            double number = Double.parseDouble(numberStr);

            return Double.parseDouble(String.format("%.2f", number));
        } catch (NumberFormatException e) {
            return 0.00;
        }
    }

    public static void addActionToProductForm(Node source) {
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/com/example/application/addProductView.fxml"));
            Parent productView = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Product");
            stage.setScene(new Scene(productView));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("No such scene is founded!");
        }
    }

    public static void addActionToTableForm(Node source) {
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AddProductView.class.getResource("/com/example/application/mainView.fxml"));
            Parent productView = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Request App");
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);

            stage.setScene(new Scene(productView));
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException("No such scene is founded!");
        }
    }
}
