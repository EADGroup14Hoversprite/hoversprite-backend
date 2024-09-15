module email {
    requires common;
    requires spring.beans;
    requires spring.context.support;
    requires spring.context;
    requires jakarta.mail;

    exports hoversprite.email.external.service;
}