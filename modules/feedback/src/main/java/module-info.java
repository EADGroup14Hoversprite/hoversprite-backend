module feedback {
    requires io.swagger.v3.oas.annotations;
    requires common;
    requires spring.beans;
    requires spring.web;
    requires spring.security.core;
    requires static lombok;
    requires spring.context;
    requires spring.data.jpa;
    requires org.apache.tomcat.embed.core;
    requires jakarta.persistence;
}