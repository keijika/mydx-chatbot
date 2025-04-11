import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

@RestController
@CrossOrigin
public class ChatController {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final String API_URL = "https://api.openai.com/v1/chat/completions";

    @PostMapping("/api/chat")
    public String chat(@RequestBody String userMessage) {
        RestTemplate restTemplate = new RestTemplate();

        // OpenAI API用のヘッダー設定
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openaiApiKey); // Bearerトークン
        headers.setContentType(MediaType.APPLICATION_JSON);

        JsonObject requestBodyJson = new JsonObject();
        requestBodyJson.addProperty("model", "gpt-3.5-turbo");
        
        JsonObject userMessageObject = new JsonObject();
        userMessageObject.addProperty("role", "user");
        userMessageObject.addProperty("content", userMessage);
        
        JsonArray messagesArray = new JsonArray();
        messagesArray.add(userMessageObject);
        
        requestBodyJson.add("messages", messagesArray);

        // 必要に応じて応答の最大トークン数を設定
        requestBodyJson.addProperty("max_tokens", 100);

        // リクエストのエンティティを作成
        HttpEntity<String> entity = new HttpEntity<>(requestBodyJson.toString(), headers);

        try {
            // リクエスト送信
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            // レスポンスをログ出力（デバッグ用）
            System.out.println("Response: " + response.getBody());

            // レスポンスを返す
            return response.getBody();
        } catch (Exception e) {
            // エラーハンドリング: エラーメッセージを表示
            System.out.println("Error occurred: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }
}
