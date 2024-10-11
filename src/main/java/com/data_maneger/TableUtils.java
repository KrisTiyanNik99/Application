package com.data_maneger;

import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;
import javafx.collections.ObservableList;

public class TableUtils {
    /*
        A class with utility methods that will perform operational logic on Tables
     */
    public static Class<?> checkClassType(ObservableList<Product> products){
        DataType classType = null;
        for (Product product : products) {
            DataType currentType = product.getType();

            if (classType == null) {
                classType = currentType;
            }

            if (!classType.equals(currentType)){
                throw new IllegalArgumentException("Different data types in one file!");
            }
        }

        return ProductFactory.getClassByType(classType);
    }
}
