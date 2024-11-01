package com.operation_maneger.factory_manager;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.example.elements_models.data_models.DataType;
import com.example.elements_models.data_models.Product;
import com.example.elements_models.data_models.child_products.BiroterapiyaProduct;
import com.example.elements_models.data_models.child_products.ConsumerProduct;
import com.example.elements_models.data_models.child_products.VedenaProduct;

public class ProductFactory {
    /*
        A class that contains all the implementations of the methods that manipulate the information coming from the
        files. The methods deal with the creation of Product objects based on received parameters, information validation,
        data parsing.
     */

    public static Product createProductObject(DataType type, List<Object> elements) throws InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Class<?> productClass = getClassByType(type);

        return ReflectionUtils.createObject(productClass, elements);
    }

    public static List<String> getProductConstructorParametersNames(DataType productType) {
        Class<?> productClass = getClassByType(productType);

        return ReflectionUtils.getClassConstructorParametersNames(productClass);
    }

    public static Class<?> checkTableItemsClassType(List<Product> products) {
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

    public static Class<?> getClassByType(DataType classType) {
        /*
            This is one of the most important methods. It is responsible for creating the various descendants of the
            main parent Product class, by DataType Enum class. For notation, we use Class<?> with "?" sign so that we
            can achieve a higher level of abstraction and the method works smoothly in future extension.
         */

        return switch (classType) {
            case VEDENA -> VedenaProduct.class;
            case CONSUMER -> ConsumerProduct.class;
            case BIROTERAPIYA -> BiroterapiyaProduct.class;
        };
    }
}
