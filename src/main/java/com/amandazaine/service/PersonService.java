package com.amandazaine.service;

import com.amandazaine.dto.v1.PersonDTO;
import com.amandazaine.dto.v2.PersonDTOv2;
import com.amandazaine.exception.ResourceNotFoundException;
import com.amandazaine.mapper.PersonMapper;
import com.amandazaine.modelOrEntity.Person;
import com.amandazaine.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    public List<PersonDTO> findAll() {
        logger.info("findAll()");
        return personMapper.mapToListPersonDTO(personRepository.findAll());
    }

    public PersonDTO findById(Long id) {
        logger.info("findById(" + id + ")");
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found!"));
        return personMapper.mapToPersonDTO(person);
    }

    public PersonDTO save(PersonDTO personDTO) {
        logger.info("save(" + personDTO + ")");
        Person person =  personMapper.mapToPerson(personDTO);
        return personMapper.mapToPersonDTO(personRepository.save(person));
    }

    public PersonDTO update(PersonDTO personDTO) {
        logger.info("update(" + personDTO + ")");

        Person entity = personRepository.findById(personDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found!"));
        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setAddress(personDTO.getAddress());
        entity.setGender(personDTO.getGender());

        return personMapper.mapToPersonDTO(personRepository.save(entity));
    }

    public void deleteById(Long id) {
        logger.info("deleteById(" + id + ")");
        personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found!"));
        personRepository.deleteById(id);
    }

    // ----------------------- V2 ----------------------- //

    public PersonDTOv2 saveV2(PersonDTOv2 personDTOv2) {
        logger.info("saveV2(" + personDTOv2 + ")");
        Person person = personRepository.save(personMapper.mapToPerson(personDTOv2));
        return personMapper.mapToPersonDTOv2(person);
    }
}
