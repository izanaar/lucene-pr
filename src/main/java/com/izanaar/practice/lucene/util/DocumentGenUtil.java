package com.izanaar.practice.lucene.util;

import org.apache.lucene.document.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * Provides general propose utility methods for generation various format {@link Document}.
 */
public class DocumentGenUtil {

    private static final List<Class> supportedFieldTypes =
            Arrays.asList(StringField.class, TextField.class, IntPoint.class);

    /**
     * Constructs {@link Document}, consisting from specified fields-values-names.
     * @see DocumentGenUtil#supportedFieldTypes for supported field type list.
     *
     * @param fieldValues  list of field values objects
     * @param fieldClasses list of required field classes
     * @param fieldNames   list of required field names
     * @return constructed document
     * @throws IllegalAccessException    thrown, if field's constructor is private
     * @throws InvocationTargetException if constructor invocation went wrong
     * @throws InstantiationException    if constructor invocation went wrong
     * @throws IllegalArgumentException  if list sizes does not match or fieldNames list contains duplicates, or
     *                                   fieldClasses list contains field type that is not supported
     */
    public static Document createDocument(List fieldValues, List<Class> fieldClasses, List<String> fieldNames) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Document document = new Document();
        if (fieldValues.size() != fieldClasses.size() || fieldValues.size() != fieldNames.size())
            throw new IllegalArgumentException("List sizes does not match!");

        fieldClasses.stream()
                .filter(fieldClass -> !supportedFieldTypes.contains(fieldClass))
                .findAny().ifPresent(DocumentGenUtil::notSupportedClassConsumer);

        if (fieldNames.stream().distinct().count() != fieldNames.size())
            throw new IllegalArgumentException("Field name duplicates does not allowed!");

        for (int i = 0; i < fieldClasses.size(); i++) {
            Class currentFieldClass = fieldClasses.get(i);
            Field newField;
            if (isThreeParamsConstructor(currentFieldClass)) {
                Constructor constructor = getConstructorWithRequiredParamsCount(currentFieldClass.getConstructors(), 3);
                newField = (Field) constructor.newInstance(fieldNames.get(i), fieldValues.get(i), Field.Store.YES);
            } else {
                Constructor constructor = getConstructorWithRequiredParamsCount(currentFieldClass.getConstructors(), 2);
                newField = (Field) constructor.newInstance(fieldNames.get(i), new int[]{(int) fieldValues.get(i)});
            }

            document.add(newField);
        }

        return document;
    }

    /**
     * Tests if class is supported.
     *
     * @param notSupportedClass class to test
     * @throws IllegalArgumentException if class is not supported
     */
    private static void notSupportedClassConsumer(Class notSupportedClass) {
        throw new IllegalArgumentException(notSupportedClass.getSimpleName() + " field type is not supported.");
    }

    /**
     * Checks if target class required constructor with 3 parameters.
     * As {@link DocumentGenUtil#createDocument(List, List, List)} supports only 3 {@link Field} types, 2 text and 1
     * numeric, amount of required constructor parameters defines if we need to create text field of numeric field.
     * @param targetClass class to check
     * @return if target field's class constructor requires 3 parameters.
     */
    private static boolean isThreeParamsConstructor(Class targetClass) {
        return (targetClass.equals(StringField.class)) || targetClass.equals(TextField.class);
    }

    private static Constructor getConstructorWithRequiredParamsCount(Constructor[] constructors, int requiredCount) {
        return Arrays.stream(constructors)
                .filter(constructor -> constructor.getParameterCount() == requiredCount)
                .findAny().orElseThrow(RuntimeException::new);
    }

}
