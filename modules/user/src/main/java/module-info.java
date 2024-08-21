module user {
    requires io.swagger.v3.oas.annotations;
    requires jakarta.persistence;
    requires java.sql;
    requires org.apache.tomcat.embed.core;
    requires org.hibernate.orm.core;
    requires spring.beans;
    requires spring.context;
    requires spring.data.jpa;
    requires spring.security.core;
    requires spring.web;
    requires common;
    requires static lombok;
    requires spring.tx;
}