import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;


public class UrlShortener {
    @Getter @Setter
    private String originalURL;
    @Getter @Setter
    private String shortenedURL;
    @Getter @Setter
    private String createdBy;
    @Getter @Setter
    private String securityPassword;

    private final Map<String,String> urlStore = new HashMap<>();

    //Function to shorten the URL
    public String shortenURL(String originalURL) {
        String shortenedURL = Base64.getEncoder()
                .encodeToString(originalURL.getBytes())
                .substring(0, 8); // Generate a shortened URL
        urlStore.put(shortenedURL, originalURL); // Save the mapping in the store
        return shortenedURL;
    }
    //Function to retrieve the Original URL stored
    public String getOriginalUrl(String shortenedURL) {
        return urlStore.get(shortenedURL);
    }
}