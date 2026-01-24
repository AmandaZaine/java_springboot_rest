package com.amandazaine.service;

import com.amandazaine.controller.PersonController;
import com.amandazaine.dto.v1.PersonDTO;
import com.amandazaine.dto.v2.PersonDTOv2;
import com.amandazaine.exception.RequiredObjectIsNullException;
import com.amandazaine.exception.ResourceNotFoundException;
import com.amandazaine.modelOrEntity.Person;
import com.amandazaine.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.amandazaine.mapper.PersonMapper.*;

@Service
public class PersonService {
    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    public List<PersonDTO> findAll() {
        logger.info("findAll()");
        List<PersonDTO> personDTOList = mapToListPersonDTO(personRepository.findAll());

        personDTOList.forEach(PersonService::addHateoasLinks);

        return personDTOList;
    }

    public PersonDTO findById(Long id) {
        logger.info("findById(" + id + ")");
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found!"));
        var personDTO = mapToPersonDTO(person);

        //Implementando HATEOAS
        addHateoasLinks(personDTO);

        return personDTO;
    }

    public PersonDTO save(PersonDTO personDTO) {
        logger.info("save(" + personDTO + ")");

        if(personDTO == null) throw new RequiredObjectIsNullException();

        Person person =  mapToPerson(personDTO);
        PersonDTO mapToPersonDTO = mapToPersonDTO(personRepository.save(person));
        addHateoasLinks(mapToPersonDTO);

        return mapToPersonDTO;
    }

    public PersonDTO update(PersonDTO personDTO) {
        logger.info("update(" + personDTO + ")");

        if(personDTO == null) throw new RequiredObjectIsNullException();

        Person entity = personRepository.findById(personDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found!"));
        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setAddress(personDTO.getAddress());
        entity.setGender(personDTO.getGender());

        PersonDTO mapToPersonDTO = mapToPersonDTO(personRepository.save(entity));
        addHateoasLinks(mapToPersonDTO);
        return mapToPersonDTO;
    }

    public void deleteById(Long id) {
        logger.info("deleteById(" + id + ")");
        personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found!"));
        personRepository.deleteById(id);
    }

    private static void addHateoasLinks(PersonDTO personDTO) {
        personDTO.add(
                WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findById(personDTO.getId()))
                        .withSelfRel()
                        .withType("GET")
        );

        personDTO.add(
                WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).delete(personDTO.getId()))
                        .withRel("delete")
                        .withType("DELETE")
        );

        personDTO.add(
                WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).findAll())
                        .withRel("findAll")
                        .withType("GET")
        );

        personDTO.add(
                WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).save(personDTO))
                        .withRel("save")
                        .withType("POST")
        );

        personDTO.add(
                WebMvcLinkBuilder
                        .linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).update(personDTO))
                        .withRel("update")
                        .withType("PUT")
        );
    }

    // ----------------------- V2 ----------------------- //

    public PersonDTOv2 saveV2(PersonDTOv2 personDTOv2) {
        logger.info("saveV2(" + personDTOv2 + ")");
        Person person = personRepository.save(mapToPerson(personDTOv2));
        return mapToPersonDTOv2(person);
    }
}
