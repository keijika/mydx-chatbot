import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;

public class ChatGPTExample {
    public static void main(String[] args) {
        String apiKey = "your-api-key";
        OkHttpClient client = new OkHttpClient();

        JsonObject json = new JsonObject();
        json.addProperty("model", "gpt-3.5-turbo");
        json.add("messages", new JsonArray());
        json.get("messages").getAsJsonArray().add(buildMessage("system", "You are a helpful assistant."));
        json.get("messages").getAsJsonArray().add(buildMessage("user", "Translate the following English text to French: '{text}'"));

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json.toString()
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/engines/davinci-codex/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonObject buildMessage(String role, String content) {
        JsonObject message = new JsonObject();
        message.addProperty("role", role);
        message.addProperty("content", content);
        return message;
    }
}
