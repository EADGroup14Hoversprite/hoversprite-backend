module jwt {
    requires common;
    requires jjwt.api;
    requires jjwt.impl;
    requires static lombok;
    requires org.apache.tomcat.embed.core;
    requires spring.beans;
    requires spring.context;
    requires spring.security.core;
    requires spring.security.web;
    requires spring.web;
    requires user;

    exports api.jwt;
}