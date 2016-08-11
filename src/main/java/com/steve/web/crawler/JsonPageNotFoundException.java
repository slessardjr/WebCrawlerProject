package com.steve.web.crawler;

public class JsonPageNotFoundException extends Exception {
    JsonPageNotFoundException(final String message) {
        super(message);
    }

    JsonPageNotFoundException(final String message, Throwable t) {
        super(message, t);
    }
}
