import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        final var httpClient = getClient();
        final var request = getRequest();
        final var response = httpClient.execute(request);
        final var json = response.getEntity().getContent();

        final var objectMapper = new ObjectMapper();
        final List<Cat> cats = objectMapper.readValue(
                json,
                new TypeReference<>() {
                }
        );

        cats.stream()
                .filter(cat -> cat.getUpvotes() != null && cat.getUpvotes() > 0)
                .forEach(System.out::println);
    }

    public static HttpUriRequest getRequest() {
        return new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
    }

    public static CloseableHttpClient getClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
    }
}
