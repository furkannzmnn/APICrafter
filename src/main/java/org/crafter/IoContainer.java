package org.crafter;

import org.crafter.adapters.OpenAIAdapter;
import org.crafter.templates.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IoContainer {
    private static final Map<String, List<Class<?>>> BEANS = new HashMap<>();

    static {
        BEANS.put("openAIAdapter", List.of(OpenAIAdapter.class));
        BEANS.put("projectStarter", List.of(ProjectStarter.class));
        BEANS.put("gitStrategy", List.of(GitPushService.class));
        BEANS.put("applicationTemplates", List.of(
                ModelTemplate.class,
                RepositoryTemplate.class,
                ServiceTemplate.class,
                ControllerTemplate.class
        ));
        BEANS.put("postCreateActions", List.of(
                GitPushService.class
        ));
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


}
