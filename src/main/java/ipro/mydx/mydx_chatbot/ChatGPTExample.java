import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import okhttp3.*;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestBody;

public class ChatGPTExample {
    public static void main(String[] args) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        OkHttpClient client = new OkHttpClient();

        // リクエストボディの作成
        JsonObject json = new JsonObject();
        json.addProperty("model", "gpt-3.5-turbo"); // 使用するモデルを指定
        
        JsonArray messagesArray = new JsonArray();
        messagesArray.add(buildMessage("system", "You are a helpful assistant.")); // システムメッセージ
        String textToTranslate = "Hello, how are you?";  // 動的に変更するテキスト
        messagesArray.add(buildMessage("user", "Translate the following English text to French: " + textToTranslate)); // ユーザーからのメッセージ
        
        json.add("messages", messagesArray);

        // リクエストボディをJSON文字列に変換
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json.toString()
        );

        // リクエスト作成
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions") // 正しいエンドポイントを使用
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

    // メッセージ作成用のヘルパーメソッド
    private static JsonObject buildMessage(String role, String content) {
        JsonObject message = new JsonObject();
        message.addProperty("role", role); // 'system' もしくは 'user'
        message.addProperty("content", content);
        return message;
    }
    System.out.println("Response: " + response.body().string());

}
