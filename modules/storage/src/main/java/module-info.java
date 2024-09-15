module storage {
    requires spring.context;
    requires firebase.admin;
    requires google.cloud.storage;
    requires spring.web;

    exports hoversprite.storage.external.service;
}