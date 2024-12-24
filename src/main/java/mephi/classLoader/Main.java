package mephi.classLoader;

public class Main {
    public static void main(String[] args) {
        // Путь к JAR-файлу
        String jarFilePath = "DynamicLib.jar";
        String className = "mephi.classLoader.MyDynamicClass"; // Полное имя класса в JAR

        // Создаём объект из JAR через утилиту
        MyInterface obj = MyDynamicUtil.createInstanceFromJar(
                MyInterface.class,
                jarFilePath,
                className,
                "Will the robot compose a symphony? Will a robot turn a piece of canvas into a masterpiece of art? - ABoBa"
        );

        System.out.println("Результат вызова obj.length(): " + obj.length());
    }
}
