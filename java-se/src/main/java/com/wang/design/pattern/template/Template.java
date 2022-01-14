package com.wang.design.pattern.template;

public abstract class Template {

    public void doSomething() {

        start();
        end();
    }


    public abstract void start();

    public abstract void end();
}
