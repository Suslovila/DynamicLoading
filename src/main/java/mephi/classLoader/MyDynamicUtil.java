package mephi.classLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;

public class MyDynamicUtil {

    @SuppressWarnings("unchecked")
    public static <T> T createInstanceFromJar(
            Class<T> interfaceClass,
            String jarFilePath,
            String className,
            Object... constructorArgs
    ) {
        try {
            // 1) Создаём загрузчик
            MyClassLoader classLoader = new MyClassLoader(jarFilePath, interfaceClass.getClassLoader());

            // 2) Загружаем класс
            Class<?> loadedClass = classLoader.loadClass(className);

            // 3) Вызываем подходящий конструктор
            Class<?>[] argTypes = toClassArray(constructorArgs);
            Constructor<?> constructor = loadedClass.getConstructor(argTypes);

            // 4) Создаём объект
            Object realObject = constructor.newInstance(constructorArgs);

            // 5) Создаём Proxy
            return (T) Proxy.newProxyInstance(
                    interfaceClass.getClassLoader(),
                    new Class<?>[]{interfaceClass},
                    new MyInvocationHandler(realObject)
            );

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании прокси для " + className + " из JAR-файла", e);
        }
    }

    private static Class<?>[] toClassArray(Object[] args) {
        if (args == null) {
            return new Class<?>[0];
        }
        Class<?>[] arr = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            arr[i] = args[i].getClass();
        }
        return arr;
    }
}
