package mephi.classLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MyClassLoader extends ClassLoader {

    private final String jarFilePath; // Путь к JAR-файлу

    public MyClassLoader(String jarFilePath, ClassLoader parent) {
        super(parent);
        this.jarFilePath = jarFilePath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // Преобразуем имя класса в путь внутри JAR-файла
        String classFilePath = name.replace('.', '/') + ".class";

        try (JarFile jarFile = new JarFile(jarFilePath)) {
            JarEntry entry = jarFile.getJarEntry(classFilePath);
            if (entry == null) {
                throw new ClassNotFoundException("Класс " + name + " не найден в JAR-файле " + jarFilePath);
            }

            try (InputStream inputStream = jarFile.getInputStream(entry)) {
                byte[] classBytes = inputStream.readAllBytes();
                return defineClass(name, classBytes, 0, classBytes.length);
            }
        } catch (IOException e) {
            throw new ClassNotFoundException("Ошибка загрузки класса " + name + " из JAR-файла", e);
        }
    }
}
