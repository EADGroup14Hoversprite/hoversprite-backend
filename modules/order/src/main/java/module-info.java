module order {
    requires spring.data.commons;
    requires email;
    requires user;
    requires payment;
    requires org.hibernate.orm.core;
    requires notification;
    requires io.swagger.v3.oas.annotations;
    requires spring.data.jpa;
    requires common;
    requires spring.security.core;
    requires spring.tx;
    requires org.apache.tomcat.embed.core;
    requires spring.beans;
    requires jakarta.persistence;
    requires spring.context;
    requires rest.api.sdk;
    requires spring.web;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires com.google.zxing;
    requires com.google.zxing.javase;

    exports hoversprite.order.external.dto;
    exports hoversprite.order.external.service;
}