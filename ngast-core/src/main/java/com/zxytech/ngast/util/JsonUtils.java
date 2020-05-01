package com.zxytech.ngast.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.FileUtils;

@Slf4j
public final class JsonUtils {
  public static final Gson gson;

  static {
    gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
  }

  private JsonUtils() {}

  /**
   * 获取 json 对象的 value 部分
   *
   * <pre>
   *     [{k1:v1,k2:v2},{k3:v3,k4:v4}] --&gt; new Object[][]{{v1,v2},{v3,v4}}
   * </pre>
   *
   * @param json
   * @return JsonPrimitive 类型的 value
   */
  public static Object[][] fetchJsonDictValues(@NonNull final String json) {
    JsonArray array = gson.fromJson(json, JsonArray.class);
    Object[][] results = new Object[array.size()][];
    for (int i = 0; i < array.size(); i++) {
      results[i] =
          array.get(i).getAsJsonObject().entrySet().stream().map(Entry::getValue).toArray();
    }
    return results;
  }

  /**
   * 获取 json 数组中元素
   *
   * <pre>
   *     [[e1,e2],[e3,e4]] --&gt; new Object[][]{{e1,e2},{e3,e4}}
   * </pre>
   *
   * @param json
   * @return JsonPrimitive 类型的 element
   */
  public static Object[][] fetchJsonArrayElements(@NonNull final String json) {
    JsonArray array = gson.fromJson(json, JsonArray.class);
    Object[][] results = new Object[array.size()][];
    for (int i = 0; i < array.size(); i++) {
      JsonArray arrayi = array.get(i).getAsJsonArray();
      results[i] = new Object[arrayi.size()];
      for (int j = 0; j < arrayi.size(); j++) {
        results[i][j] = arrayi.get(j);
      }
    }
    return results;
  }

  public static Object[][] loadJsonDictValues(@NonNull final File json) throws IOException {

    return fetchJsonDictValues(FileUtils.readFileToString(json, Charsets.UTF_8));
  }

  public static Object[][] loadJsonDictValues(@NonNull final String filepath) throws IOException {
    return loadJsonDictValues(new File(filepath));
  }

  public static Object[][] loadJsonArrayElements(@NonNull final File json) throws IOException {
    return fetchJsonArrayElements(FileUtils.readFileToString(json, Charsets.UTF_8));
  }

  public static Object[][] loadJsonArrayElements(@NonNull final String filepath)
      throws IOException {
    return loadJsonArrayElements(new File(filepath));
  }
}
