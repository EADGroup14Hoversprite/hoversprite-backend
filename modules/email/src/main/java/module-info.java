module email {
    requires common;
    requires spring.beans;
    requires spring.context.support;
    requires spring.context;

    exports hoversprite.email.external.service;
}