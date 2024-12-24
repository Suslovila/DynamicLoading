package mephi.classLoader;

public class MyDynamicClass {
    private final String data;

    public MyDynamicClass(String data) {
        this.data = data;
    }

    public int length() {
        return data != null ? data.length() : 0;
    }
}