import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import okhttp3.*;

import java.io.IOException;

public class ChatGPTExample {
    public static void main(String[] args) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        OkHttpClient client = new OkHttpClient();

        JsonObject json = new JsonObject();
        json.addProperty("model", "gpt-3.5-turbo");
        
        JsonArray messagesArray = new JsonArray();
        messagesArray.add(buildMessage("system", "You are a helpful assistant."));
        
        String textToTranslate = "Hello, how are you?";  // 動的に変えたいテキスト
        messagesArray.add(buildMessage("user", "Translate the following English text to French: " + textToTranslate));
        
        json.add("messages", messagesArray);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json.toString()
        );

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions") // 修正されたエンドポイント
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("Response: " + response.body().string());
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
