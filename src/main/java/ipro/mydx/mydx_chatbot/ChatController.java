package ipro.mydx.mydx_chatbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
public class ChatController {

    // OpenAI APIキーをプロパティファイルから注入
    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final String API_URL = "https://api.openai.com/v1/completions";

    // チャットのリクエストを受け取り、OpenAI APIを呼び出す
    @PostMapping("/api/chat")
    public String chat(@RequestBody String userMessage) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openaiApiKey);  // Bearerトークン方式で認証
        headers.setContentType(MediaType.APPLICATION_JSON);

        // リクエストボディの作成
        String requestBody = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + userMessage + "\"}]}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // OpenAI APIにPOSTリクエストを送信
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

        return response.getBody(); // 受け取ったレスポンスの内容を返す
    }
}
