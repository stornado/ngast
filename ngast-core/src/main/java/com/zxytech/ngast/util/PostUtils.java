package com.zxytech.ngast.util;

import io.restassured.internal.http.BasicNameValuePairWithNoValueSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class PostUtils {

    public static final CloseableHttpClient client;

    static {
        client = HttpClients.createDefault();
    }

    public static CloseableHttpResponse postJson(@NonNull String url, @NonNull String json)
        throws IOException {
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        return client.execute(post);
    }

    public static CloseableHttpResponse postJson(@NonNull String url, @NonNull Map<String, ?> json)
        throws IOException {
        return postJson(url, JsonUtils.gson.toJson(json));
    }

    public static CloseableHttpResponse postJson(
        @NonNull String url, @NonNull Map<String, ?> json, Header... headers) throws IOException {

        return postJson(url, JsonUtils.gson.toJson(json), headers);
    }

    public static CloseableHttpResponse postJson(
        @NonNull String url, @NonNull String json, Header... headers) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        post.setHeaders(headers);
        return client.execute(post);
    }

    public static CloseableHttpResponse postJson(
        @NonNull String url,
        @NonNull Map<String, ?> json,
        Map<String, ?> queryParams,
        Header... headers)
        throws IOException {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        queryParams.forEach(
            (key, value) -> nameValuePairs
                .add(new BasicNameValuePairWithNoValueSupport(key, value)));
        new UrlEncodedFormEntity(nameValuePairs);
        return postJson(url, JsonUtils.gson.toJson(json), headers);
    }

    public static CloseableHttpResponse postForm(
        @NonNull String url, @NonNull Map<String, ?> form, Header... headers) throws IOException {
        HttpPost post = new HttpPost(url);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        form.forEach(
            (key, value) -> nameValuePairs
                .add(new BasicNameValuePairWithNoValueSupport(key, value)));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        post.setHeaders(headers);
        return client.execute(post);
    }

}
