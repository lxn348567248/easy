package me.liuning.core.ioc.handler;

import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.liuning.core.ioc.Finder;
import me.liuning.core.ioc.annotation.OnClick;

public class OnClickHandler implements AnnotationHandler {
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

    private void injectTraversal(Class<?> superClass, Object target, Object source, Finder finder) {

        if (superClass == null) {
            return;
        }

        injectTraversal(superClass.getSuperclass(), target, source, finder);

        Method[] allMethods = superClass.getDeclaredMethods();
        for (Method itemMethod : allMethods) {
            injectClickListener(target, source, finder, itemMethod);
        }


    }

    private void injectClickListener(Object target, Object source, Finder finder, Method itemMethod) {
        itemMethod.setAccessible(true);
        OnClick onClickAnnotation = itemMethod.getAnnotation(OnClick.class);
        if (onClickAnnotation == null) {
            return;
        }
        int[] viewIds = onClickAnnotation.value();
        for (int viewId : viewIds) {
            if (viewId < 0) {
                continue;
            }
            View targetView = finder.findViewById(source, viewId);
            if (targetView != null) {
                targetView.setOnClickListener(new DeclareClickListener(itemMethod, target));
            }
        }

    }


    private class DeclareClickListener implements View.OnClickListener {

        private Method mMethod;
        private Object mTarget;

        DeclareClickListener(Method method, Object target) {
            this.mMethod = method;
            this.mTarget = target;
        }

        @Override
        public void onClick(View v) {

            try {
                mMethod.setAccessible(true);
                mMethod.invoke(mTarget, v);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(
                        "Could not execute  method for @onClick", e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(
                        "Could not execute method for @onClick", e);
            }
        }
    }
}
