package org.crafter;

import org.crafter.adapters.OpenAIAdapter;
import org.crafter.templates.*;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Constructor;
import java.util.*;

public class IoContainer {
    private static final Map<String, List<Class<?>>> BEANS = new HashMap<>();

    static {

        Reflections reflections = new Reflections("org.crafter", new TypeAnnotationsScanner(), new SubTypesScanner());

        Set<Class<?>> constructors = reflections.getTypesAnnotatedWith(Bean.class);

        for (Class<?> classz : constructors) {
            Bean annotation = classz.getAnnotation(Bean.class);
            String beanName = annotation.name();

            List<Class<?>> beanImplementations = BEANS.getOrDefault(beanName, new ArrayList<>());
            beanImplementations.add(classz);
            BEANS.put(beanName, beanImplementations);
        }

    }

    public static <T> T getBean(String beanName) {
        try {
            return (T) BEANS.get(beanName).get(0).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            if (e instanceof IndexOutOfBoundsException) {
                String message = String.format("Bean with name %s not found", beanName);
                throw new RuntimeException(message);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public static Object getBean(String beanName, Object... args) {
        try {
            return BEANS.get(beanName).get(0).getDeclaredConstructor().newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> void fillBeanInIoContainer(String beanName, List<T> bean) {
        // gelen bean name'i bul, eğer birden fazla varsa hepsini listeye ekle

        try {
            List<Class<?>> list = BEANS.get(beanName);
            // bean listesine ekle

            for (Class<?> aClass : list) {
                bean.add((T) aClass.getDeclaredConstructor().newInstance());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void fillBeanInIoContainer(Map<String, List<? extends Ioc>> beans) {
        for (Map.Entry<String, List<? extends Ioc>> entry : beans.entrySet()) {
            fillBeanInIoContainer(entry.getKey(), entry.getValue());
        }
    }

    public static void autoLoadInClassBean(Class<?> classz) {
        // verilen sınıfın içindeki tüm beanlari consturctor ile oluşturup initialize et

        Constructor<?>[] constructors = classz.getConstructors();

        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                parameters[i] = getBean(parameterTypes[i].getSimpleName());
            }

            try {
                constructor.newInstance(parameters);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}
