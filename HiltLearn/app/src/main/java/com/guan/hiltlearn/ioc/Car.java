package com.guan.hiltlearn.ioc;

public class Car {
    Engine engine;

    public void start() {
        engine.run();
    }

    public Car() {
        super();
    }

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
