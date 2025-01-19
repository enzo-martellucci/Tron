module com.uphf.tron {
    requires jakarta.persistence;

    requires spring.core;
    requires spring.context;
    requires spring.beans;
    requires spring.aop;
    requires spring.boot;
    requires spring.jcl;
    requires spring.jdbc;
    requires spring.tx;
    requires spring.boot.autoconfigure;
    requires spring.data.jpa;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.desktop;
    requires bcrypt;

    opens com.uphf.tron.constants to spring.core;
    opens com.uphf.tron.controller to spring.core, javafx.fxml;
    opens com.uphf.tron.service to spring.core;
    opens com.uphf.tron.entity to spring.core, org.hibernate.orm.core;
    opens com.uphf.tron.repository to spring.core;
    opens com.uphf.tron to spring.core, javafx.fxml;

    exports com.uphf.tron.constants;
    exports com.uphf.tron.controller;
    exports com.uphf.tron.service;
    exports com.uphf.tron.entity;
    exports com.uphf.tron.repository;
    exports com.uphf.tron;
}