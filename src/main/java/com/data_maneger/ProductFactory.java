package com.data_maneger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
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
        parsing.
     */

    public static <T extends Product> T createProductObject(DataType type, List<Object> elements) throws InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Class<?> clazz = getClassByType(type);
        Constructor<?>[] constructors = clazz.getConstructors();
        /*
            Since we may have more than one constructor in the future, we want to be able to dynamically create an
            object of the Product class when the appropriate constructor (with the required number of parameters) is
            found to create an object of the Product class.
         */

        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == elements.size()) {
                return (T) constructor.newInstance(elements.toArray());
            }
        }

        throw new IllegalArgumentException("There is no suitable constructor for this class with so many parameters.");
    }

    public static List<String> getConstructorClassParametersNames(DataType productType) {
        Class<?> productClass = getClassByType(productType);
        return getParametersNames(productClass);
    }

    public static List<String> getParametersNames(Class<?> productClass) {
        Constructor<?>[] constructors = productClass.getConstructors();
        Constructor<?> constructor = getMainConstructor(constructors);

        Parameter[] parameters = constructor.getParameters();
        List<String> parameterNames = new ArrayList<>();

        for (Parameter parameter : parameters) {
            parameterNames.add(parameter.getName());
        }

        return Collections.unmodifiableList(parameterNames);
    }

    private static Class<?> getClassByType(DataType classType) {
        return switch (classType) {
            case VEDENA -> VedenaProduct.class;
            case CONSUMER -> ConsumerProduct.class;
            case BIROTERAPIYA -> BiroterapiyaProduct.class;
        };
    }

    private static Constructor<?> getMainConstructor(Constructor<?>[] constructors) {
        /*
            By default, we will always take the constructor with the fewest arguments (because it is easier to do), so
            that we can create objects of the given class as easily as possible, since it will only take the basic
            information without which it cannot be created the object. This is a separate method because it would be
            useful to be able to control the logic of which and what constructor we want to take (because that way the
            code is more open to refactoring in the future).
         */

        int argumentsNumber = Integer.MAX_VALUE;
        Constructor<?> mainConstructor = null;

        for (Constructor<?> constructor : constructors) {
            int constructorArguments = constructor.getParameterCount();

            if (argumentsNumber > constructorArguments) {
                argumentsNumber = constructorArguments;
                mainConstructor = constructor;
            }
        }

        if (mainConstructor == null) {
            throw new IllegalArgumentException("No needed constructor is found.");
        }

        return mainConstructor;
    }
}
