import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        useClient(httpClient -> {
            try {
                final Apod apod = getApod(httpClient);
                final var url = apod.url();
                final var fileName = url.substring(url.lastIndexOf('/') + 1);
                saveImageFromWeb(httpClient, url, fileName);
            } catch (final IOException ex) {
                System.err.println(ex);
            }
        });
    }

    public static void saveImageFromWeb(
            CloseableHttpClient httpClient,
            String url,
            String targetFile
    ) throws IOException {
        final var request = new HttpGet(url);
        final var response = httpClient.execute(request);
        final var bytes = response.getEntity().getContent().readAllBytes();
        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            outStream.write(bytes);
        }
    }

    public static Apod getApod(CloseableHttpClient httpClient) throws IOException {
        final var request = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=" + System.getenv("NASA_APOD_API_KEY"));
        final var response = httpClient.execute(request);
        final var json = response.getEntity().getContent();

        final var objectMapper = new ObjectMapper();
        final Apod apod = objectMapper.readValue(json, Apod.class);

        return apod;
    }

    public static void useClient(Consumer<CloseableHttpClient> block) {
        try (var httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build()
        ) {
            block.accept(httpClient);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
