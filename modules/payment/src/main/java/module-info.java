module payment {
    requires common;
    requires rest.api.sdk;
    requires spring.beans;
    requires jakarta.annotation;
    requires spring.context;
    requires static lombok;
    requires spring.security.core;

    exports hoversprite.payment.external.service;
}