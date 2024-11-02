package com.operation_maneger.function_manager;

import com.example.elements_models.data_models.Product;
import com.operation_maneger.json_manager.JsonDataManager;
import javafx.scene.control.TableView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/*
    A class with utility methods that will perform operational logic on Tables retrieving and saving data in .
 */

public class FileUtils {
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

    public static void saveAction(List<Product> products, String infoFileName) {
        JsonDataManager jsonManager = new JsonDataManager();
        jsonManager.saveInfoToJsonFile(products, infoFileName);
    }

    public static void deleteProductFromTable(Product selectedProducts, TableView<Product> table) {
        table.getItems().removeAll(selectedProducts);
    }

    public static void deleteElementFromMap(Product selectedProduct, String infoFileName,
                                            Map<String, List<Product>> productDataByFile) {

        productDataByFile.get(infoFileName).removeIf(selectedProduct::equals);
    }

    public static void checkForExistingFile(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File was not found: " + path);
        }
    }
}
