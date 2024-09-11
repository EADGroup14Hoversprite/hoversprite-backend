module order {
    requires spring.data.commons;
    requires email;
    requires user;
    requires payment;
    requires common;
    requires rest.api.sdk;
    requires spring.tx;
    requires spring.security.core;
    requires spring.beans;
    requires spring.context;
    requires io.swagger.v3.oas.annotations;
    requires spring.web;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires org.hibernate.orm.core;
    requires org.apache.tomcat.embed.core;
    requires jakarta.persistence;

    exports hoversprite.order.external.dto;
    exports hoversprite.order.external.service;
}