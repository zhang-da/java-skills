![](1839455518-5ef800834775c.png)

### 1 ApplicationContextInitializer

```
org.springframework.context.ApplicationContextInitializer
```

   这是整个spring容器在刷新之前初始化ConfigurableApplicationContext的回调接口，简单来说，就是在容器刷新之前调用此类的initialize方法。
   这个点允许被用户自己扩展。用户可以在整个spring容器还没被初始化之前做一些事情。
   可以想到的场景可能为，在最开始激活一些配置，或者利用这时候class还没被类加载器加载的时机，进行动态字节码注入等操作。

   扩展方式为：
   ```
   public class TestApplicationContextInitializer implements ApplicationContextInitializer {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            System.out.println("[ApplicationContextInitializer]");
        }
    }
   ```

因为这时候spring容器还没被初始化，所以想要自己的扩展的生效，有以下三种方式：

- 在启动类中用`springApplication.addInitializers(new TestApplicationContextInitializer())`语句加入

- 配置文件配置`context.initializer.classes=com.example.demo.TestApplicationContextInitializer`

- Spring SPI扩展，在spring.factories中加入`org.springframework.context.ApplicationContextInitializer=com.example.demo.TestApplicationContextInitializer`

  

### 2 BeanDefinitionRegistryPostProcessor

```
org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
```

这个接口在读取项目中的beanDefinition之后执行，提供一个补充的扩展点

使用场景：你可以在这里动态注册自己的beanDefinition，可以加载classpath之外的bean

扩展方式为:

```java
public class TestBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("[BeanDefinitionRegistryPostProcessor] postProcessBeanDefinitionRegistry");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("[BeanDefinitionRegistryPostProcessor] postProcessBeanFactory");
    }

}
```


