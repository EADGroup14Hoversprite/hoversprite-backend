module security {
    requires common;
    requires io.swagger.v3.oas.models;
    requires jjwt.api;
    requires jjwt.impl;
    requires static lombok;
    requires org.apache.tomcat.embed.core;
    requires spring.beans;
    requires spring.context;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.security.crypto;
    requires spring.security.web;
    requires spring.web;
    requires user;

    exports api.security;
    exports internal.config;
}