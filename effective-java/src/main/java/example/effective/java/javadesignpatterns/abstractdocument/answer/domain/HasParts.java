package example.effective.java.javadesignpatterns.abstractdocument.answer.domain;

import example.effective.java.javadesignpatterns.abstractdocument.answer.Document;

import java.util.stream.Stream;

//public interface HasParts extends Document {
//
//    String PROPERTY = "parts";
//
//    default Stream<Part> getParts() {
//        return children(PROPERTY, Part::new);
//    }
//
//}

public interface HasParts extends Document {

    String PROPERTY = "parts";

    default Stream<Part> getParts() {
        return children(PROPERTY, Part::new);
    }

}
