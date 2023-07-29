import com.fasterxml.jackson.annotation.JsonProperty;

public record Apod(
        String date,
        String explanation,
        String hdurl,
        @JsonProperty("media_type") String mediaType,
        @JsonProperty("service_version") String serviceVersion,
        String title,
        String url
) {
}
