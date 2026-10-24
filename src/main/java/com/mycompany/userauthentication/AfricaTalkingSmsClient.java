package com.mycompany.userauthentication;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AfricaTalkingSmsClient {

    private static final String SANDBOX_URL =
            "https://api.sandbox.africastalking.com/version1/messaging";

    private AfricaTalkingSmsClient() {
    }

    public static String sendSandbox(String phoneNumber, String message)
            throws Exception {

        String apiKey = System.getenv("HMS_SMS_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "HMS_SMS_API_KEY is not configured.");
        }

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Recipient phone number is missing.");
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException(
                    "SMS message is empty.");
        }

        String form =
                "username=sandbox"
                + "&to=" + encode(phoneNumber.trim())
                + "&message=" + encode(message);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SANDBOX_URL))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .header("Content-Type",
                        "application/x-www-form-urlencoded")
                .header("apiKey", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() < 200
                || response.statusCode() >= 300) {
            throw new IllegalStateException(
                    "Africa's Talking sandbox returned HTTP "
                    + response.statusCode() + ".");
        }

        String body = response.body();

        Pattern successPattern = Pattern.compile(
                "\"status\"\\s*:\\s*\"Success\"",
                Pattern.CASE_INSENSITIVE);

        Matcher matcher = successPattern.matcher(
                body == null ? "" : body);

        if (!matcher.find()) {
            throw new IllegalStateException(
                    "Sandbox did not confirm SMS acceptance. "
                    + "Check the provider dashboard and API response "
                    + "privately; response details are not logged here.");
        }

        return "SMS_ACCEPTED_SANDBOX";
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
