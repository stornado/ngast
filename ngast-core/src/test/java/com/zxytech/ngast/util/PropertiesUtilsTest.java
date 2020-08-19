package com.zxytech.ngast.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import com.zxytech.ngast.testng.NgastAnnotationTransformer;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Slf4j
@Listeners(NgastAnnotationTransformer.class)
public class PropertiesUtilsTest {

    public static final String PROPS_KEY_STRING = "props.key.string";
    public static final String PROPS_VALUE_STRING = "ABCabc123";
    public static final String PROPS_KEY_INTEGER = "props.key.integer";
    public static final Integer PROPS_VALUE_INTEGER = 123;

    public static final String PROPS_KEY_LONG = "props.key.long";
    public static final Long PROPS_VALUE_LONG = 123L;

    public static final String PROPS_KEY_FLOAT = "props.key.float";
    public static final Float PROPS_VALUE_FLOAT = 9.9F;

    public static final String PROPS_KEY_DOUBLE = "props.key.double";
    public static final Double PROPS_VALUE_DOUBLE = 9.9;

    public static final String PROPS_KEY_BIG_DECIMAL = "props.key.big.decimal";
    public static final BigDecimal PROPS_VALUE_BIG_DECIMAL = new BigDecimal("9.9");


    @Test
    public void testGetProperty() {
        assertEquals(PropertiesUtils.getProperty(PROPS_KEY_STRING), PROPS_VALUE_STRING);
    }

    @Test
    public void testGetAsString() {
        assertEquals(PropertiesUtils.getAsString(PROPS_KEY_STRING), PROPS_VALUE_STRING);
    }

    @Test
    public void testGetAsInteger() {
        assertEquals(PropertiesUtils.getAsInteger(PROPS_KEY_INTEGER), PROPS_VALUE_INTEGER);
    }

    @Test
    public void testGetAsLong() {
        assertEquals(PropertiesUtils.getAsLong(PROPS_KEY_LONG), PROPS_VALUE_LONG);
    }

    @Test
    public void testGetAsFloat() {
        assertEquals(PropertiesUtils.getAsFloat(PROPS_KEY_FLOAT), PROPS_VALUE_FLOAT);
    }

    @Test
    public void testGetAsDouble() {
        assertEquals(PropertiesUtils.getAsDouble(PROPS_KEY_DOUBLE), PROPS_VALUE_DOUBLE);
    }

    @Test
    public void testGetAsBigDecimal() {
        assertEquals(PropertiesUtils.getAsBigDecimal(PROPS_KEY_BIG_DECIMAL),
            PROPS_VALUE_BIG_DECIMAL);

        assertNotEquals(
            BigDecimal.valueOf(PropertiesUtils.getAsFloat(PROPS_KEY_BIG_DECIMAL)),
            PROPS_VALUE_BIG_DECIMAL);
        assertEquals(
            BigDecimal.valueOf(PropertiesUtils.getAsDouble(PROPS_KEY_BIG_DECIMAL)),
            PROPS_VALUE_BIG_DECIMAL);
    }
}
