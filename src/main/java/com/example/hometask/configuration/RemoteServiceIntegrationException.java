package com.example.hometask.configuration;

public class RemoteServiceIntegrationException extends RuntimeException {

    public RemoteServiceIntegrationException(Throwable e) {

        super(e);
    }

    public RemoteServiceIntegrationException(String message) {

        super(message);
    }
}
