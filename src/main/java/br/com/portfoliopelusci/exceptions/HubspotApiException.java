package br.com.portfoliopelusci.exceptions;

public class HubspotApiException extends RuntimeException {
    private final int statusCode;

    public HubspotApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
