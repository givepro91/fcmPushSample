package com.example.fcmpushsample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FcmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Autowired
    public FcmController(FirebaseCloudMessageService firebaseCloudMessageService) {
        this.firebaseCloudMessageService = firebaseCloudMessageService;
    }

    @RequestMapping(value = "/fcm/test")
    public void fcmTest() throws IOException {
        // 근식
        String targetToken = "dfu0kQlpU00TmItWZXs6Wn:APA91bHMUG8y_NwtJe6aIVB9_y1Mw_OshjPsXO1pt1NmdiCNyr7Irk9_D-8E0iBmBR4NAsspmnCs9pNNMkj59uB2PemkZ3YjJ7r0g8PbkcreB7C9BOXrjU5sABMTN81aLIeqij18P88O";
        // 하준
//        String targetToken = "fhYRx_aVRyW4s0Xrh4LcbD:APA91bGZQEVyunD5dwbTTNE6_acU6YaKTA7zysIeANw9Qtn5ceEgjGA62YJigEVrdtsb4lFWt8GnhxdocDKKp5TjYpIWt_6E9YNhWFKVLWxinO0nxDSxqaS-B7bBcNx47_HJPyKoVULl";
        String title = "test1111";
        String body = "111";

        firebaseCloudMessageService.sendMessageTo(targetToken, title, body);
    }

}
