package com.zxytech.ngast.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import com.zxytech.ngast.testng.NgastAnnotationTransformer;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Slf4j
@Listeners(NgastAnnotationTransformer.class)
public class PropertiesUtilsTest {
  public static final String NGAST_PROPERTIES_FILE_PATH =
      FilenameUtils.concat(
          "/Users/ryan/Github/ngast/ngast-core/src/test/resources", "ngast.properties");

  public static final String PROPS_KEY_STRING = "props.key.string";
  public static final String PROPS_VALUE_STRING = "ABCabc123";
  public static final String PROPS_STRING = "props.key.string=ABCabc123";
  public static final String PROPS_KEY_INTEGER = "props.key.integer";
  public static final Integer PROPS_VALUE_INTEGER = 123;
  public static final String PROPS_INTEGER = "props.key.integer=123";

  public static final String PROPS_KEY_LONG = "props.key.long";
  public static final Long PROPS_VALUE_LONG = 123L;
  public static final String PROPS_LONG = "props.key.long=123";

  public static final String PROPS_KEY_FLOAT = "props.key.float";
  public static final Float PROPS_VALUE_FLOAT = 9.9F;
  public static final String PROPS_FLOAT = "props.key.float=9.9";

  public static final String PROPS_KEY_DOUBLE = "props.key.double";
  public static final Double PROPS_VALUE_DOUBLE = 9.9;
  public static final String PROPS_DOUBLE = "props.key.double=9.9";

  public static final String PROPS_KEY_BIG_DECIMAL = "props.key.big.decimal";
  public static final BigDecimal PROPS_VALUE_BIG_DECIMAL = new BigDecimal("9.9");
  public static final String PROPS_BIG_DECIMAL = "props.key.big.decimal=9.9";

  @BeforeClass
  public void setUp() {
    File ngastProperties = new File(NGAST_PROPERTIES_FILE_PATH);
    try {
      FileUtils.writeLines(
          ngastProperties,
          Arrays.asList(
              PROPS_STRING,
              PROPS_INTEGER,
              PROPS_LONG,
              PROPS_FLOAT,
              PROPS_DOUBLE,
              PROPS_BIG_DECIMAL));
    } catch (IOException e) {
      log.error("create {} failed", NGAST_PROPERTIES_FILE_PATH, e);
    }
  }

  //  @AfterClass
  //  public void tearDown() {
  //    try {
  //      FileUtils.forceDeleteOnExit(new File(NGAST_PROPERTIES_FILE_PATH));
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //  }

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
    assertEquals(PropertiesUtils.getAsBigDecimal(PROPS_KEY_BIG_DECIMAL), PROPS_VALUE_BIG_DECIMAL);

    assertNotEquals(
        BigDecimal.valueOf(PropertiesUtils.getAsFloat(PROPS_KEY_BIG_DECIMAL)),
        PROPS_VALUE_BIG_DECIMAL);
    assertEquals(
        BigDecimal.valueOf(PropertiesUtils.getAsDouble(PROPS_KEY_BIG_DECIMAL)),
        PROPS_VALUE_BIG_DECIMAL);
  }
}
