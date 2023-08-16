package com.example.oauth2clientfundamentals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectTest {

    @Configuration static class NormalConfiguration1 {}
    @Configuration static class NormalConfiguration2 {}

    @Configuration
    @Import({NormalConfiguration1.class, NormalConfiguration2.class})
    static class DummyConfiguration {}

    @Test
    public void dummyConfigurationTest() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DummyConfiguration.class);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }

        NormalConfiguration1 normalConfiguration1 = applicationContext.getBean(NormalConfiguration1.class);
        NormalConfiguration2 normalConfiguration2 = applicationContext.getBean(NormalConfiguration2.class);

        assertThat(normalConfiguration1).isNotNull();
        assertThat(normalConfiguration2).isNotNull();
    }

    // IMPORT SELECTOR
    @Configuration static class NormalConfiguration3 {}
    @Configuration static class NormalConfiguration4 {}

    static class DummyImportSelector implements ImportSelector {
        static final Logger log = LoggerFactory.getLogger(DummyImportSelector.class);
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            log.info("[DummyImportSelector called]");
            LinkedHashSet<String> imports = new LinkedHashSet<>();
            imports.add("com.example.oauth2clientfundamentals.SelectTest$NormalConfiguration3");
            imports.add("com.example.oauth2clientfundamentals.SelectTest$NormalConfiguration4");
            return StringUtils.toStringArray(imports);
        }
    }

    @Configuration
    @Import(DummyImportSelector.class)
    static class SelectorConfiguration {}

    @Test
    public void dummyImportSelectorTest() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(SelectorConfiguration.class);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }

        NormalConfiguration3 normalConfiguration3 = applicationContext.getBean(NormalConfiguration3.class);
        NormalConfiguration4 normalConfiguration4 = applicationContext.getBean(NormalConfiguration4.class);

        assertThat(normalConfiguration3).isNotNull();
        assertThat(normalConfiguration4).isNotNull();
    }

}
