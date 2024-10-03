package com.data_maneger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;
import com.example.data_models.product_models.models.BiroterapiyaProduct;
import com.example.data_models.product_models.models.ConsumerProduct;
import com.example.data_models.product_models.models.VedenaProduct;

public class FunctionManager {
    public static void main(String[] args) {

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

    public Product createProductFromJsonData(List<Object> data, DataType type) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Product product = null;
        switch (type) {
            case VEDENA -> product = createObject(VedenaProduct.class, data);
            case CONSUMMER -> product = createObject(ConsumerProduct.class, data);
            case BIROTERAPIYA -> product = createObject(BiroterapiyaProduct.class, data);
        }

        return product;
    }
}
