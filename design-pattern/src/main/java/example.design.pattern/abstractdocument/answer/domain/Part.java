package example.effective.java.javadesignpatterns.abstractdocument.answer.domain;

import example.effective.java.javadesignpatterns.abstractdocument.answer.AbstractDocument;

import java.util.Map;

public class Part extends AbstractDocument implements HasType, HasModel, HasPrice {

    public Part(Map<String, Object> properties) { super(properties); }
}
