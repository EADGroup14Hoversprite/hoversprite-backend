module auth {
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
}