module order {
    requires spring.data.commons;
    requires email;
    requires user;
    requires payment;
    requires org.hibernate.orm.core;
    requires notification;
    requires common;
    requires spring.security.core;
    requires rest.api.sdk;
    requires org.apache.tomcat.embed.core;
    requires spring.tx;
    requires jakarta.persistence;
    requires spring.beans;
    requires spring.context;
    requires lombok;
    requires com.fasterxml.jackson.databind;
    requires spring.web;
    requires io.swagger.v3.oas.annotations;
    requires spring.data.jpa;

    exports hoversprite.order.external.dto;
    exports hoversprite.order.external.service;
}