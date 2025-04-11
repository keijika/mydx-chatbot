import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import okhttp3.*;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestBody;

public class ChatGPTExample {
    public static void main(String[] args) {
        String apiKey = "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";  // 必ず自分のAPIキーに置き換えてください
        OkHttpClient client = new OkHttpClient();

        // JSONオブジェクトの作成
        JsonObject json = new JsonObject();
        json.addProperty("model", "gpt-3.5-turbo");

        // メッセージ配列の作成
        JsonArray messagesArray = new JsonArray();
        messagesArray.add(buildMessage("system", "You are a helpful assistant.")); // システムメッセージ
        String textToTranslate = "Hello, how are you?";  // テキスト（翻訳したい内容）
        messagesArray.add(buildMessage("user", "Translate the following English text to French: " + textToTranslate)); // ユーザーからのメッセージ

        // messages配列をJSONオブジェクトに追加
        json.add("messages", messagesArray);

        // リクエストボディをJSON文字列に変換
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json.toString()
        );

        // リクエスト作成
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")  // 正しいエンドポイント
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
        message.addProperty("role", role);  // 'system' もしくは 'user'
        message.addProperty("content", content);
        return message;
    }
    System.out.println("Response: " + response.body().string());

}
