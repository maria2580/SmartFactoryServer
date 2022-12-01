package com.primitive.SmartFactoryServer;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import java.io.IOException;

public class FCMessage {

    // FCM에서 가입 후 받는 키값
    public final static String AUTH_KEY_FCM = APIKEY.getAPIKEY();

    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
    public static FirebaseApp firebaseApp;
    public static FirebaseMessaging firebaseMessaging;
    public static void init() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setDatabaseUrl("https://smartfactory-25ac3.firebaseio.com/")
                .build();

        firebaseApp.initializeApp(options);
        firebaseMessaging = FirebaseMessaging.getInstance(FirebaseApp.getInstance());
    }
    public static void send(String title,String msg,String to) throws Exception{
        if(FirebaseApp.getApps().isEmpty()){
            init();
            System.out.println("init실행 됨");
        }
        System.out.println("전송할거야");
        System.out.println("전송할거야");
        System.out.println("전송할거야");
        System.out.println("전송할거야");
        System.out.println("전송할거야");
        System.out.println("title: "+title+" msg: "+msg+" to: "+to);
        firebaseMessaging.send(Message.builder()
                        .setToken(to)
                        .setNotification(Notification.builder().setTitle(title).setBody(msg).build())
                        .build());
        //String token = tokenList.get(count).getDEVICE_ID();
/*
        String _title = title;
        String _body = msg;
        String _actionType = "new";
        String _code = "test";
        //String _token = "/topics/ALL"; // 전체

        // 모바일기기에서 얻음
        String _token = to; // 개인


        final String apiKey = AUTH_KEY_FCM;
        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key=" + apiKey);

        conn.setDoOutput(true);


        JSONObject json = new JSONObject();
        JSONObject notification = new JSONObject();

        notification.put("title", _title);
        notification.put("body", _body);

        json.put("notification", notification);
        json.put("to", _token);

        String sendMsg = json.toString();

        OutputStream os = conn.getOutputStream();

        // 서버에서 날려서 한글 깨지는 사람은 아래처럼  UTF-8로 인코딩해서 날려주자
        //os.write(input.getBytes("UTF-8"));
        os.write(sendMsg.getBytes("UTF-8"));
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        // print result
        System.out.println(response.toString());*/
    }
}