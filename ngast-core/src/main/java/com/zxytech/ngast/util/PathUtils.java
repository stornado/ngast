package com.zxytech.ngast.util;

import static com.zxytech.ngast.util.PropertiesUtils.NGAST_ENVIRONMENT_KEY_NAME;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public final class PathUtils {
  private static final String DATA_DIR = "data/";

  private PathUtils() {}

  public static Path get(String first, String... more) {
    return getEnvFilePath(of(first, more));
  }

  public static Path get(URI uri) {
    return getEnvFilePath(of(uri));
  }

  public static Path of(String first, String... more) {
    return Paths.get(first, more);
  }

  public static Path of(URI uri) {
    return Paths.get(uri);
  }

  /**
   * 根据 rdm.profiles={env} 配置值 从data/{env}/目录获取对应文件路径
   *
   * <p>如： ngast.environment=test
   *
   * <p>则：
   *
   * <pre>
   * PathUtils.getEnvFilePath("order/purchased-order.json")         = "data/test/order/purchased-order.json"
   * PathUtils.getEnvFilePath("data/order/purchased-order.json")    = "data/test/order/purchased-order.json"
   * </pre>
   *
   * @param filePath
   * @return
   */
  public static String getEnvFilePath(String filePath) {
    return getAbsolutePath(filePath, PropertiesUtils.getAsString(NGAST_ENVIRONMENT_KEY_NAME));
  }

  public static Path getEnvFilePath(Path filePath) {
    return of(
        getAbsolutePath(
            filePath.toString(), PropertiesUtils.getAsString(NGAST_ENVIRONMENT_KEY_NAME)));
  }

  private static String getClasspathFilePath(String fileName) throws FileNotFoundException {
    URL fileUrl = ClassLoader.getSystemClassLoader().getResource(fileName);
    if (fileUrl != null) {
      return fileUrl.getPath();
    }
    fileUrl = Thread.currentThread().getContextClassLoader().getResource(fileName);
    if (fileUrl != null) {
      return fileUrl.getPath();
    }

    throw new FileNotFoundException(fileName);
  }

  private static String getExistClasspathPath(String filePath) {
    File file = null;
    try {
      file = new File(getClasspathFilePath(filePath));
    } catch (FileNotFoundException ignored) {
    }
    if (file != null && file.exists()) {
      return file.getAbsolutePath();
    }
    return null;
  }

  private static String getAbsolutePath(String path, String env) {
    if (log.isDebugEnabled()) {
      log.debug("{} {}", path, env);
    }
    String envNameLowerCase = StringUtils.isBlank(env) ? "" : StringUtils.trim(env).toLowerCase();

    for (String absPath :
        Arrays.asList(
            getExistClasspathPath(
                path.replaceFirst("^/?data", StringUtils.join(DATA_DIR, envNameLowerCase))),
            getExistClasspathPath(StringUtils.join(DATA_DIR, envNameLowerCase, "/", path)),
            getExistClasspathPath(path),
            getExistClasspathPath(path.replaceFirst("^/?data", DATA_DIR)),
            getExistClasspathPath(StringUtils.join(DATA_DIR, path)))) {
      if (StringUtils.isNotBlank(absPath)) {
        return absPath;
      }
    }

    if (log.isWarnEnabled()) {
      log.warn("Env: {} FileNotFound {}", env, path);
    }
    return path;
  }
}
