module auth {
    exports hoversprite.auth.external.service;
    requires user;
    requires common;
    requires spring.beans;
    requires spring.security.core;
    requires spring.context;
    requires spring.security.crypto;
    requires jjwt.api;
    requires jjwt.impl;
    requires org.apache.tomcat.embed.core;
    requires static lombok;
    requires spring.security.web;
    requires spring.web;
    requires io.swagger.v3.oas.annotations;
    requires jakarta.validation;
    requires spring.security.config;
    requires io.swagger.v3.oas.models;
    requires spring.security.oauth2.client;
    requires json.smart;
    requires jakarta.persistence;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires spring.webmvc;
}