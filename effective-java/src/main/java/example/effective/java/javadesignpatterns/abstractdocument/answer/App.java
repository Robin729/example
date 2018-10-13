package example.effective.java.javadesignpatterns.abstractdocument.answer;

import example.effective.java.javadesignpatterns.abstractdocument.answer.domain.*;

import java.util.*;

public class App {

    App() {
        Map<String, Object> carProperties = new HashMap<>();
        carProperties.put(HasModel.PROPERTY, "300SL");
        carProperties.put(HasPrice.PROPERTY, 10000L);

        Map<String, Object> wheelProperties = new HashMap<>();
        wheelProperties.put(HasType.PROPERTY, "wheel");
        wheelProperties.put(HasModel.PROPERTY, "15C");
        wheelProperties.put(HasPrice.PROPERTY, 100L);

        Map<String, Object> doorProperties = new HashMap<>();
        doorProperties.put(HasType.PROPERTY, "door");
        doorProperties.put(HasModel.PROPERTY, "Lambo");
        doorProperties.put(HasPrice.PROPERTY, 300L);

        carProperties.put(HasParts.PROPERTY, Arrays.asList(wheelProperties, doorProperties));

        Car car = new Car(carProperties);

        print("Here is our car:");
        print("-> model: {}", car.getModel().get());
        print("-> price: {}", String.valueOf(car.getPrice().get()));
        print("-> parts: ");
        car.getParts().forEach(p -> print("\t{}/{}/{}", p.getType().get(), p.getModel().get(), String.valueOf(p.getPrice().get())));
    }

    private void print(String ... str) {
        for (String s : str)
            System.out.println(s);
        System.out.println();
    }

    public static void main(String[] args) {
        new App();
    }
}
