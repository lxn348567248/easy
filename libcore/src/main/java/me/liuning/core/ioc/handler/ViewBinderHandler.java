package me.liuning.core.ioc.handler;

import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

import me.liuning.core.ioc.Finder;
import me.liuning.core.ioc.annotation.ViewBind;

public class ViewBinderHandler implements AnnotationHandler {
    @Override
    public void handle(Object target, Object source, Finder finder) {
        if (target == null) {
            throw new NullPointerException("target must be not null");
        }

        if (source == null) {
            throw new NullPointerException("source must be not null");
        }

        injectTraversal(target.getClass(), target, source, finder);

    }


    private void injectTraversal(Class<?> targetClass, Object target, Object source, Finder finder) {

        if (targetClass == null) {
            return;
        }

        injectTraversal(targetClass.getSuperclass(), target, source, finder);

        Field[] allFields = targetClass.getDeclaredFields();
        for (Field itemField : allFields) {
            injectField(target, source, finder, itemField);
        }


    }

    private void injectField(Object target, Object source, Finder finder, Field itemField) {
        itemField.setAccessible(true);
        ViewBind viewBindAnnotation = itemField.getAnnotation(ViewBind.class);
        if (viewBindAnnotation == null) {
            return;
        }
        int viewId = viewBindAnnotation.value();

        if (viewId < 0) {
            Log.e("ViewBinderHandler", "viewId is inValidate");
            return;
        }

        View targetView = finder.findViewById(source, viewId);
        try {
            itemField.set(target, targetView);
        } catch (IllegalAccessException e) {
            //ignore
        }
    }


}
