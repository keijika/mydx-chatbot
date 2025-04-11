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

    private final String API_URL = "https://api.openai.com/v1/completions";

    @PostMapping("/api/chat")
    public String chat(@RequestBody String userMessage) {
        RestTemplate restTemplate = new RestTemplate();

        // OpenAI API用のヘッダー設定
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openaiApiKey); // Bearerトークン
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSONリクエストボディ作成
        JsonObject requestBodyJson = new JsonObject();
        requestBodyJson.addProperty("model", "gpt-3.5-turbo");

        // メッセージの作成
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", userMessage);

        // メッセージを配列として追加
        JsonArray messagesArray = new JsonArray();
        messagesArray.add(message);
        requestBodyJson.add("messages", messagesArray);

        // リクエストのエンティティを作成
        HttpEntity<String> entity = new HttpEntity<>(requestBodyJson.toString(), headers);

        // リクエスト送信
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

        // レスポンスを返す
        return response.getBody();
    }
}
