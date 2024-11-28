package com.example.firstproject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstprojectApplication.class, args);
		UrlShortener urlShortener = new UrlShortener();
		String originalURL = "https://www.crafman.com/long-url";
		String shortenedURL = urlShortener.shortenURL(originalURL);

		String qrCodeFilePath = "craf.png";
		String result = urlShortener.generateQRCode(shortenedURL, qrCodeFilePath);
		System.out.println(result);


		System.out.println("Original URL: " + originalURL);
		System.out.println("Shortened URL: " + shortenedURL);
		System.out.println("Retrieved URL: " + urlShortener.getOriginalUrl(shortenedURL));

	}

}
