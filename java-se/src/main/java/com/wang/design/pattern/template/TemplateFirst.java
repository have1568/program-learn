package com.wang.design.pattern.template;

public class TemplateFirst extends Template{
    @Override
    public void start() {
        System.out.println("FirstStart");
    }

    @Override
    public void end() {
        System.out.println("FirstEnd");
    }
}
