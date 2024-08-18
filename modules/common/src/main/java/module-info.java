module common {
    requires io.swagger.v3.oas.models;
    requires jakarta.persistence;
    requires java.naming;
    requires java.sql;
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

    exports configs;
    exports enums;
    exports exception;
    exports types;
    exports dtos.response;
    exports dtos.request;
    exports interfaces;
    exports dtos;
}