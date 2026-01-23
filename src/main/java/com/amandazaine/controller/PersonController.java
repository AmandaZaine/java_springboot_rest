package com.amandazaine.controller;

import com.amandazaine.dto.v1.PersonDTO;
import com.amandazaine.dto.v2.PersonDTOv2;
import com.amandazaine.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE })
    public List<PersonDTO> findAll() {
        logger.debug("findAll()");
        return personService.findAll();
    }

    @GetMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE }
    )
    public PersonDTO findById(@PathVariable Long id) {
        logger.info("findById(" + id + ")");
        PersonDTO person = personService.findById(id);
        person.setDateOfBirth(new Date());
        person.setPhoneNumber("");
        return person;
    }

    @PostMapping(
            consumes =  {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            }
    )
    public PersonDTO save(@RequestBody PersonDTO personDTO) {
        logger.warn("save(" + personDTO + ")");
        return personService.save(personDTO);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE }
    )
    public PersonDTO update(@RequestBody PersonDTO personDTO) {
        logger.warn("update(" + personDTO + ")");
        return personService.update(personDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.error("delete(" + id + ")");
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ----------------------- V2 ----------------------- //

    @PostMapping(
            value = "/v2",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE }
    )
    public PersonDTOv2 save(@RequestBody PersonDTOv2 personDTOv2) {
        return personService.saveV2(personDTOv2);
    }
}
