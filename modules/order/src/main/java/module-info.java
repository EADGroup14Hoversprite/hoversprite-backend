module order {
    requires io.swagger.v3.oas.annotations;
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires spring.beans;
    requires spring.context;
    requires spring.data.jpa;
    requires spring.web;
    requires common;
    requires com.fasterxml.jackson.annotation;
}