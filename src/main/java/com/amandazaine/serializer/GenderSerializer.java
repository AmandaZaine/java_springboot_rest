package com.amandazaine.serializer;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class GenderSerializer extends ValueSerializer<String> {

    @Override
    public void serialize(String value, tools.jackson.core.JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        String formattedGender = "M".equals(value) ? "Male" : "Female";
        gen.writeString(formattedGender);
    }
}
