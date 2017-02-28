package com.anotheria.bootcamp.file_transfer.commands;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseCommand <T extends BaseCommandHandler> implements Serializable{

    private Class<?> handlerClass;

    private Class<?> getParameterizedClass()
    {

        if(handlerClass == null)
        {
            Type genericSuperClass = getClass().getGenericSuperclass();

            ParameterizedType parametrizedType = null;
            while (parametrizedType == null) {
                if ((genericSuperClass instanceof ParameterizedType)) {
                    parametrizedType = (ParameterizedType) genericSuperClass;
                } else {
                    genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
                }
            }

            handlerClass = (Class<?>) parametrizedType.getActualTypeArguments()[0];
        }

        return handlerClass;

    }

    boolean isHandlerOfThisCommand(BaseCommandHandler possibleHandler){
        return getParameterizedClass().isInstance(possibleHandler);
    }

    void executeWithHandler(BaseCommandHandler handler){
        execute((T) handler);
    }

    protected abstract void execute(T handler);

}
