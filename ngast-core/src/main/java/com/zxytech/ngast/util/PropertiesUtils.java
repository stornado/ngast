package com.zxytech.ngast.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Properties;
import java.util.Set;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public final class PropertiesUtils {

  public static final String NGAST_PROPERTIES_FILE_NAME = "ngast.properties";
  public static final String NGAST_ENVIRONMENT_KEY_NAME = "ngast.environment";

  private static final Properties properties;

  static {
    properties = loadNgastProperties();

    // 读取环境配置
    String env = PropertiesUtils.getProperty(NGAST_ENVIRONMENT_KEY_NAME);
    if (StringUtils.isNotBlank(env)) {
      try {
        properties.putAll(
            PropertiesUtils.loadPropertiesFrom(
                StringUtils.join("ngast-", StringUtils.trim(env), ".properties")));
      } catch (Exception e) {
        log.warn("{}", env, e);
      }
    }
  }

  private PropertiesUtils() {}

  public static Properties loadNgastProperties() {
    return loadPropertiesFrom(NGAST_PROPERTIES_FILE_NAME);
  }

  /**
   * 读取properties文件内容
   *
   * @param fileName properties文件名或相对于resources的相对路径
   * @return properties in fileName
   */
  public static Properties loadPropertiesFrom(@NonNull final String fileName) {
    final Properties properties = new Properties();
    loadPropertiesFrom(ClassLoader.getSystemClassLoader(), fileName, properties);
    loadPropertiesFrom(Thread.currentThread().getContextClassLoader(), fileName, properties);
    return properties;
  }

  /**
   * Searches for the property with the specified key in this property list. If the key is not found
   * in this property list, the default property list, and its defaults, recursively, are then
   * checked. The method returns {@code null} if the property is not found.
   *
   * @param key the property key.
   * @return the value in this property list with the specified key value.
   */
  public static String getProperty(@NonNull final String key) {
    return properties.getProperty(key);
  }

  /**
   * Searches for the property with the specified key in this property list. If the key is not found
   * in this property list, the default property list, and its defaults, recursively, are then
   * checked. The method returns the default value argument if the property is not found.
   *
   * @param key the hashtable key.
   * @param defaultValue a default value.
   * @return the value in this property list with the specified key value.
   */
  public static String getProperty(@NonNull final String key, final String defaultValue) {
    String val = getProperty(key);
    return (val == null) ? defaultValue : val;
  }

  public static Object get(@NonNull final Object key) {
    return properties.get(key);
  }

  public static Object getOrDefault(@NonNull final Object key, final Object defaultValue) {
    return properties.getOrDefault(key, defaultValue);
  }

  public static String getAsString(@NonNull final String key) {
    return getProperty(key);
  }

  public static String getAsString(@NonNull final String key, final String defaultValue) {
    return getProperty(key, defaultValue);
  }

  public static Short getAsShort(@NonNull final String key) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : Short.parseShort(StringUtils.trim(val));
  }

  public static Short getAsShort(@NonNull final String key, final int radix) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : Short.parseShort(StringUtils.trim(val), radix);
  }

  public static Integer getAsInteger(@NonNull final String key) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : Integer.parseInt(StringUtils.trim(val));
  }

  public static Integer getAsInteger(@NonNull final String key, final int radix) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : Integer.parseInt(StringUtils.trim(val), radix);
  }

  public static Long getAsLong(@NonNull final String key) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : Long.parseLong(StringUtils.trim(val));
  }

  public static Long getAsLong(@NonNull final String key, final int radix) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : Long.parseLong(StringUtils.trim(val), radix);
  }

  public static Float getAsFloat(@NonNull final String key) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : Float.parseFloat(StringUtils.trim(val));
  }

  public static Double getAsDouble(@NonNull final String key) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : Double.parseDouble(StringUtils.trim(val));
  }

  public static BigInteger getAsBigInteger(@NonNull final String key) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : new BigInteger(StringUtils.trim(val));
  }

  public static BigDecimal getAsBigDecimal(@NonNull final String key) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : new BigDecimal(StringUtils.trim(val));
  }

  public static Byte getAsByte(@NonNull final String key) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : Byte.parseByte(StringUtils.trim(val));
  }

  public static Boolean getAsBoolean(@NonNull final String key) {
    String val = getProperty(key);
    return StringUtils.isBlank(val) ? null : Boolean.parseBoolean(StringUtils.trim(val));
  }

  /**
   * Returns an unmodifiable set of keys from this property list where the key and its corresponding
   * value are strings, including distinct keys in the default property list if a key of the same
   * name has not already been found from the main properties list. Properties whose key or value is
   * not of type {@code String} are omitted.
   *
   * <p>The returned set is not backed by this {@code Properties} object. Changes to this {@code
   * Properties} object are not reflected in the returned set.
   *
   * @return an unmodifiable set of keys in this property list where the key and its corresponding
   *     value are strings, including the keys in the default property list.
   */
  public static Set<String> stringPropertyNames() {
    return properties.stringPropertyNames();
  }

  private static void loadPropertiesFrom(
      @NonNull final ClassLoader classLoader,
      @NonNull final String fileName,
      @NonNull final Properties properties) {

    try (InputStream stream = classLoader.getResourceAsStream(fileName)) {
      if (stream != null) {
        properties.load(stream);
      }
    } catch (IOException e) {
      log.error("Error while reading {} from classpath:", fileName, e);
    }
  }
}
