package com.example.firstproject;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

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

    //Method to shorten the URL
    public String shortenURL(String originalURL) {
        String shortenedURL = generateShortenedURL(originalURL);
        while (urlStore.containsKey(shortenedURL) && !urlStore.get(shortenedURL).equals(originalURL)){
            shortenedURL = generateShortenedURL(originalURL + Math.random());
        }
        urlStore.put(shortenedURL, originalURL); //
        return shortenedURL;
    }

    private String generateShortenedURL(String originalURL) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalURL.getBytes(StandardCharsets.UTF_8));
            String hashString = Base64.getEncoder().encodeToString(hash);
            return hashString.substring(0, 8); //
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }

    //Function to retrieve the Original URL stored
    public String getOriginalUrl(String shortenedURL) {
        return urlStore.get(shortenedURL);
    }

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
