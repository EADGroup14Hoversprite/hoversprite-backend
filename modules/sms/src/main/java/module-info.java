module otp {
    requires spring.beans;
    requires common;
    requires firebase.admin;
    requires com.google.auth;
    requires twilio;
    requires spring.context;

    exports hoversprite.otp.external.service;
}