package org.example.openapi.demo.converters;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.example.openapi.demo.Localizable;
import org.example.openapi.demo.serializer.LocalizableCollectionSerializer;

@JsonSerialize(using = LocalizableCollectionSerializer.class)
public class LocalizableLinkedList<E extends Localizable> extends java.util.LinkedList<E> {

    public LocalizableLinkedList() {
        super();
    }

    public LocalizableLinkedList(java.util.Collection<? extends E> c) {
        super(c);
    }

}
