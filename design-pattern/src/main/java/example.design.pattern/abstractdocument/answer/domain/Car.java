package example.effective.java.javadesignpatterns.abstractdocument.answer.domain;

import example.effective.java.javadesignpatterns.abstractdocument.answer.AbstractDocument;

import java.util.Map;

public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

    public Car(Map<String, Object> properties) { super(properties); }

}
