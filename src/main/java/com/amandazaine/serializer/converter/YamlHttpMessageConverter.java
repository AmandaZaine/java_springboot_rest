package com.amandazaine.serializer.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

public final class YamlHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private final YAMLMapper yamlMapper;

    // Construtor - configura o YAMLMapper e os tipos MIME suportados
    public YamlHttpMessageConverter() {
        super(
            MediaType.parseMediaType("application/yaml"),
            MediaType.parseMediaType("application/x-yaml")
        );
        YAMLMapper mapper = new YAMLMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.yamlMapper = mapper;
    }

    // Define quais classes podem ser convertidas (true = todas)
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    // Converte YAML (entrada) para Object (Java)
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return yamlMapper.readValue(inputMessage.getBody(), clazz);
    }

    // Converte Object (Java) para YAML (saída)
    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        yamlMapper.writeValue(outputMessage.getBody(), o);
    }
}