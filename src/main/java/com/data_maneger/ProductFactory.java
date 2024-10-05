package com.data_maneger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;
import com.example.data_models.product_models.models.BiroterapiyaProduct;
import com.example.data_models.product_models.models.ConsumerProduct;
import com.example.data_models.product_models.models.VedenaProduct;

public class ProductFactory {
    /*
        A class that contains all the implementations of the methods that manipulate the information coming from the
        files. The methods deal with the creation of objects based on received parameters, information validation, data
        parsing, as well as extracting information from files
     */
    public static void main(String[] args) {
        JsonDataManager j = new JsonDataManager();

        String s = j.readJsonFile("vedena.json");
        System.out.println(s);
        System.out.println(j.getJsonFields(j.getJsonFileObject("vedena.json")));
    }

    private static <T extends Product> T createObject(Class<T> clazz, List<Object> elements) throws InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Constructor<?>[] constructors = clazz.getConstructors();

        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == elements.size()) {
                return clazz.cast(constructor.newInstance(elements.toArray()));
            }
        }

        throw new IllegalArgumentException("There is no suitable constructor for this class with so many parameters.");
    }

    public static Product createProductFromJsonData(List<Object> data, DataType type) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Product product = null;
        switch (type) {
            case VEDENA -> product = createObject(VedenaProduct.class, data);
            case CONSUMMER -> product = createObject(ConsumerProduct.class, data);
            case BIROTERAPIYA -> product = createObject(BiroterapiyaProduct.class, data);
            default -> throw new IllegalArgumentException("Unknown DataType: " + type);
        }

        return product;
    }
}
