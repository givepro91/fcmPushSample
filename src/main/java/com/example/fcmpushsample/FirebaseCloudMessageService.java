package com.example.fcmpushsample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;


@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    /**
     * firebase console -> project setting -> common -> project id
     */
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/{project-id}/messages:send";
    private final ObjectMapper objectMapper;

    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/cloud-platform";
    private static final String[] SCOPES = { MESSAGING_SCOPE };

    /**
     *
     * @param targetToken
     * @param title
     * @param body
     * @throws IOException
     */
    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    /**
     *
     * @param targetToken
     * @param title
     * @param body
     * @return
     * @throws JsonParseException
     * @throws JsonProcessingException
     */
    private String makeMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .customData(FcmMessage.CustomData.builder()
                                .click_action("{app action data}")
                                .build())
                        .build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    /**
     * Retrieve a valid access token that can be use to authorize requests to the FCM REST API.
     *
     * @return
     * @throws IOException
     */
    private static String getAccessToken() throws IOException {
        // firebase console -> project setting -> service account -> Firebase Admin SDK private key create
        String firebaseConfigPath = "fcm/fcm_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(Arrays.asList(SCOPES));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
