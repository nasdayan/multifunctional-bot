package com.github.nasdayan.trackbot.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ZipService {

    private final HttpClient httpClient;

    public ZipService(HttpClient httpClient){
        this.httpClient = httpClient;
    }

    @Lookup
    public HttpPost getHttpPost() {
        return null;
    }

    public String uploadFile(byte[] data, String filename) throws IOException {
        ByteArrayBody byteArrayBody = new ByteArrayBody(data, filename);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addPart("file", byteArrayBody);
        HttpEntity entity = multipartEntityBuilder.build();
        HttpPost post = getHttpPost();
        post.setEntity(entity);
        //HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(post);
        JSONObject jsonObject = new JSONObject(new String(IOUtils.toByteArray(response.getEntity().getContent())));
        return jsonObject.getString("link");
    }
}
