package example.effective.java.javadesignpatterns.abstractdocument.answer.domain;

import example.effective.java.javadesignpatterns.abstractdocument.answer.Document;

import java.util.Optional;

public interface HasType extends Document {

    String PROPERTY = "type";

    default Optional<String> getType() { return Optional.ofNullable((String) get(PROPERTY)); }

}
