module storage {
    requires spring.context;
    requires firebase.admin;
    requires spring.web;
    requires google.cloud.storage;
    requires com.google.auth;
    requires com.google.auth.oauth2;

    exports hoversprite.storage.external.service;
}