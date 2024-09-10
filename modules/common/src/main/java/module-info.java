module common {
    requires io.swagger.v3.oas.models;
    requires jakarta.persistence;
    requires java.naming;
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
    requires com.fasterxml.jackson.databind;
    requires com.ibm.icu;
    requires spring.tx;

    exports shared.enums;
    exports shared.types;
    exports shared.constants;
    exports shared.dtos;
    exports shared.services;
    exports shared.serializer;
    exports shared.utils;
}