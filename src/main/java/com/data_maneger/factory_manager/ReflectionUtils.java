package com.data_maneger.factory_manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReflectionUtils {
    public static <T> T createObject(Class<?> clazz, List<Object> elements) throws
            InvocationTargetException, InstantiationException, IllegalAccessException {
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

        throw new IllegalArgumentException("There is no suitable constructor for this class with so many parameters." +
                System.lineSeparator() + "Cannot create class " + clazz);
    }

    public static List<String> getAllDeclaredClassFieldsNames(Class<?> clazz) {
        /*
            Any field names that possibly overlap in the parent and child classes will be fetched once. Avoids errors
            where in the parent class the field that matches that of the successor is redefined and possible data
            confusion occurs.
         */

        List<String> allClassFieldsNames = new ArrayList<>();

        while (clazz != null && clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                allClassFieldsNames.add(field.getName());
            }

            clazz = clazz.getSuperclass();
        }

        return Collections.unmodifiableList(allClassFieldsNames);
    }

    public static List<String> getClassConstructorParametersNames(Class<?> clazz) {
        Constructor<?> constructor = getMainConstructor(clazz.getConstructors());

        Parameter[] parameters = constructor.getParameters();
        List<String> parameterNames = new ArrayList<>();

        for (Parameter parameter : parameters) {
            parameterNames.add(parameter.getName());
        }

        return Collections.unmodifiableList(parameterNames);
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
            int constructorArgumentsNumber = constructor.getParameterCount();

            if (argumentsNumber > constructorArgumentsNumber) {
                argumentsNumber = constructorArgumentsNumber;
                mainConstructor = constructor;
            }
        }

        if (mainConstructor == null) {
            throw new IllegalArgumentException("No matching constructor is found.");
        }

        return mainConstructor;
    }
}
