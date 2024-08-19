module auth {
    requires io.swagger.v3.oas.annotations;
    requires jakarta.validation;
    requires static lombok;
    requires spring.beans;
    requires spring.context;
    requires spring.security.core;
    requires spring.security.crypto;
    requires spring.web;
    requires common;
    requires user;
    requires jwt;

    exports api.auth;
}