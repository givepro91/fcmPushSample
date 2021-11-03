package com.example.fcmpushsample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validateOnly;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Notification notification;
        private CustomData customData;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;

    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class CustomData {
        //        private String title;
//        private String message;
//        private String type;
//        private String id;
//        private String targetUrl;
//        private String image;
        private String click_action;
    }
}
