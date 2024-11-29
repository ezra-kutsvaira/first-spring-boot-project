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
    //Method to generate the QR code
     public String generateQRCode (String shortenedURL, String filePath){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width = 200;//width of the QR code
        int height = 200; // height of the QR code
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(shortenedURL,BarcodeFormat.QR_CODE, width,height);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path); // Write QR code to a file

            return "QR Code saved to: " + filePath;
        }catch (WriterException | IOException e) {
            e.printStackTrace();
            return "Error generating QR Code: " + e.getMessage();
        }
    }

}
