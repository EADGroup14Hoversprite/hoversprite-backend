module notification {
    exports hoversprite.notification.external.service;
    requires auth;
    requires common;
    requires io.swagger.v3.oas.annotations;
    requires jakarta.persistence;
    requires static lombok;
    requires spring.beans;
    requires spring.context;
    requires spring.data.jpa;
    requires spring.messaging;
    requires spring.web;
    requires spring.websocket;
    requires com.fasterxml.jackson.databind;
    requires spring.security.config;
    requires spring.security.core;
    requires java.management;
    requires netty.socketio;
    requires io.netty.codec.http;
}