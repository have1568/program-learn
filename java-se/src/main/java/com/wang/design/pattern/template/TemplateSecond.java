package com.wang.design.pattern.template;

public class TemplateSecond extends Template {
    @Override
    public void start() {
        System.out.println("SecondStart");
    }

    @Override
    public void end() {
        System.out.println("SecondEnd");
    }
}
