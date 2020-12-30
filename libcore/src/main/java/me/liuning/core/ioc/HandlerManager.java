package me.liuning.core.ioc;

import java.util.ArrayList;
import java.util.List;

import me.liuning.core.ioc.handler.AnnotationHandler;
import me.liuning.core.ioc.handler.OnClickHandler;
import me.liuning.core.ioc.handler.ViewBinderHandler;

public class HandlerManager {

    private static List<AnnotationHandler> sHandler = new ArrayList<>();

    private static HandlerManager instance = new HandlerManager();

    private HandlerManager() {

    }

    public static HandlerManager getInstance() {
        return instance;
    }

    static {
        sHandler.add(new ViewBinderHandler());
        sHandler.add(new OnClickHandler());
    }

    public void handle(Object target, Object source, Finder finder) {
        for (AnnotationHandler annotationHandler : sHandler) {
            annotationHandler.handle(target, source, finder);
        }

    }
}
