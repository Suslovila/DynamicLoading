package mephi.classLoader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    private final Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //    главное — убедиться, что target.getClass() действительно имеет такой метод.
        Class<?> targetClass = target.getClass();

        Method targetMethod = findMethod(targetClass, method);
        if (targetMethod == null) {
            throw new NoSuchMethodException("Метод " + method + " не найден в " + targetClass);
        }

        return targetMethod.invoke(target, args);
    }

    /**
     * Пример реализации поиска метода в targetClass с такой же сигнатурой,
     * как у метода, вызванного на прокси.
     */
    private Method findMethod(Class<?> targetClass, Method proxyMethod) {
        try {
            return targetClass.getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        } catch (NoSuchMethodException e) {
            // Если не найден, возвращаем null, а выше выбросим исключение
            return null;
        }
    }
}
