package example.effective.java.javadesignpatterns.abstractdocument.answer.domain;

import example.effective.java.javadesignpatterns.abstractdocument.answer.Document;

import java.util.Optional;

public interface HasModel extends Document {

    String PROPERTY = "model";

    default Optional<String> getModel() { return Optional.ofNullable((String) get(PROPERTY)); }
}
