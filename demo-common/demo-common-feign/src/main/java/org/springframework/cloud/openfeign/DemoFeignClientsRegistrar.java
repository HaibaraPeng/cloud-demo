package org.springframework.cloud.openfeign;

import lombok.Getter;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description feign 自动配置功能 from mica
 * @Author dongp
 * @Date 2022/6/29 0029 11:02
 */
public class DemoFeignClientsRegistrar implements ImportBeanDefinitionRegistrar, BeanClassLoaderAware, EnvironmentAware {

    @Getter
    private ClassLoader beanClassLoader;

    @Getter
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerFeignClients(registry);
    }

    private void registerFeignClients(BeanDefinitionRegistry registry) {
        List<String> feignClients = new ArrayList<>(
                SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader()));

        // 支持 springboot 2.7 + 最新版本的配置方式
        ImportCandidates.load(FeignClient.class, getBeanClassLoader()).forEach(feignClients::add);
        // 如果 spring.factories 里为空
        if (feignClients.isEmpty()) {
            return;
        }
        for (String className : feignClients) {
            try {
                Class<?> clazz = beanClassLoader.loadClass(className);
                AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(clazz, FeignClient.class);
                if (attributes == null) {
                    continue;
                }
                // 如果已经存在该bean，支持原生的Feign
                if (registry.containsBeanDefinition(className)) {
                    continue;
                }
                registerClientConfiguration(registry, getClientName(attributes), attributes.get("configuration"));

                validate(attributes);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return the class used by {@link SpringFactoriesLoader} to load configuration
     * candidates.
     *
     * @return the factory class
     */
    private Class<?> getSpringFactoriesLoaderFactoryClass() {
        return DemoFeignClientsRegistrar.class;
    }

    @Nullable
    private String getClientName(@Nullable Map<String, Object> client) {
        if (client == null) {
            return null;
        }
        String value = (String) client.get("contextId");
        if (!StringUtils.hasText(value)) {
            value = (String) client.get("value");
        }
        if (!StringUtils.hasText(value)) {
            value = (String) client.get("name");
        }
        if (!StringUtils.hasText(value)) {
            value = (String) client.get("serviceId");
        }
        if (StringUtils.hasText(value)) {
            return value;
        }

        throw new IllegalStateException(
                "Either 'name' or 'value' must be provided in @" + FeignClient.class.getSimpleName());
    }

    private void registerClientConfiguration(BeanDefinitionRegistry registry, Object name, Object configuration) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(FeignClientSpecification.class);
        builder.addConstructorArgValue(name);
        builder.addConstructorArgValue(configuration);
        registry.registerBeanDefinition(name + "." + FeignClientSpecification.class.getSimpleName(),
                builder.getBeanDefinition());
    }

    private void validate(Map<String, Object> attributes) {
        AnnotationAttributes annotation = AnnotationAttributes.fromMap(attributes);
        // This blows up if an aliased property is overspecified
        FeignClientsRegistrar.validateFallback(annotation.getClass("fallback"));
        FeignClientsRegistrar.validateFallbackFactory(annotation.getClass("fallbackFactory"));
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {

    }

    @Override
    public void setEnvironment(Environment environment) {

    }
}
