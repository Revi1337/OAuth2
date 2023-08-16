package com.example.oauth2clientfundamentals;


import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class BeanTest {

    interface ParentClass { }

    static class FirstChildClass implements ParentClass { }

    static class SecondChildClass implements ParentClass { }

    @Configuration
    static class DummyClass {

        @PostConstruct
        public void init() { System.out.println("DummyClass.init"); }

        @Bean
        public ParentClass parentClass() { return new FirstChildClass(); }

        @Bean
        public FirstChildClass firstChildClass() { return new FirstChildClass(); }

        @Bean
        public SecondChildClass secondChildClass() { return new SecondChildClass(); }

    }

    @PostConstruct
    public void init() {
        System.out.println("BeanTest.init");
    }

    @Test
    public void beanTest() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DummyClass.class);

        ParentClass bean = applicationContext.getBean("parentClass", ParentClass.class);
        System.out.println("bean = " + bean);
    }

    @Test
    public void beanTest2() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DummyClass.class);

        FirstChildClass bean = applicationContext.getBean("firstChildClass", FirstChildClass.class);
        System.out.println("bean = " + bean);
    }

    @Test
    public void beanTest3() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DummyClass.class);

        SecondChildClass secondChildClass = applicationContext.getBean(SecondChildClass.class);
        System.out.println("secondChildClass = " + secondChildClass);
    }

    @Test
    public void beanTest4() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DummyClass.class);

        applicationContext.getBeansOfType(ParentClass.class).forEach((beanName, beanType) -> {
            System.out.println(beanName + " : " + beanType);
        });
    }

    @Test
    public void beanTest5() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DummyClass.class);

        applicationContext.getBeansOfType(FirstChildClass.class).forEach((beanName, beanType) -> {
            System.out.println(beanName + " : " + beanType);
        });
    }

    @Test
    public void beanTest6() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DummyClass.class);

        applicationContext.getBeansOfType(SecondChildClass.class).forEach((beanName, beanType) -> {
            System.out.println(beanName + " : " + beanType);
        });
    }

}
