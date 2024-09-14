module common {
    requires io.swagger.v3.oas.models;
    requires jakarta.persistence;
    requires java.naming;
    requires static lombok;
    requires org.apache.tomcat.embed.core;
    requires spring.beans;
    requires spring.context;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.security.crypto;
    requires spring.security.web;
    requires spring.web;
    requires jakarta.validation;
    requires jjwt.api;
    requires jjwt.impl;
    requires com.fasterxml.jackson.databind;
    requires com.ibm.icu;
    requires spring.tx;
    requires rest.api.sdk;
    requires spring.context.support;
    requires firebase.admin;
    requires com.google.auth;
    requires com.google.auth.oauth2;

    exports hoversprite.common.external.enums;
    exports hoversprite.common.external.type;
    exports hoversprite.common.external.constant;
    exports hoversprite.common.internal.dto;
    exports hoversprite.common.external.serializer;
    exports hoversprite.common.external.util;
}