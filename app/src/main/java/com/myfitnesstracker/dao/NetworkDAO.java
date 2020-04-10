package com.myfitnesstracker.dao;

import okhttp3.*;

public class NetworkDAO {
    /**
     * Return the data found at the given endpoint
     * @param endpoint a URL or other location where we can find data.
     * @return All of the data returned as one string.
     * @throws Exception
     */
    public String request(String endpoint) throws Exception  {
        String responseBody = "";

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, "{}");
        Request request = new Request.Builder()
                .url(endpoint)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()){

            assert response.body() != null;
            responseBody = response.body().string();

        }
        return responseBody;
    }

}