package me.liuning.core.ioc.handler;


import me.liuning.core.ioc.Finder;

public interface AnnotationHandler {
    void handle(Object target, Object source, Finder finder);
}
