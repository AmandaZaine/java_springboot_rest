package com.amandazaine.mapper;

import com.amandazaine.dto.v1.PersonDTO;
import com.amandazaine.dto.v2.PersonDTOv2;
import com.amandazaine.modelOrEntity.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonMapper {

    public PersonDTOv2 mapToPersonDTOv2(Person person) {
        PersonDTOv2 personDTOv2 = new PersonDTOv2();
        personDTOv2.setId(person.getId());
        personDTOv2.setFirstName(person.getFirstName());
        personDTOv2.setLastName(person.getLastName());
        personDTOv2.setAddress(person.getAddress());
        personDTOv2.setGender(person.getGender());
        personDTOv2.setBirthDate( new Date() );

        return personDTOv2;
    }

    public Person mapToPerson(PersonDTOv2 personDTOv2) {
        Person person = new Person();
        person.setId(personDTOv2.getId());
        person.setFirstName(personDTOv2.getFirstName());
        person.setLastName(personDTOv2.getLastName());
        person.setAddress(personDTOv2.getAddress());
        person.setGender(personDTOv2.getGender());

        return person;
    }

    public PersonDTO mapToPersonDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setFirstName(person.getFirstName());
        personDTO.setLastName(person.getLastName());
        personDTO.setAddress(person.getAddress());
        personDTO.setGender(person.getGender());

        return personDTO;
    }

    public Person mapToPerson(PersonDTO personDTO) {
        Person person = new Person();
        person.setId(personDTO.getId());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setAddress(personDTO.getAddress());
        person.setGender(personDTO.getGender());

        return person;
    }

    public List<PersonDTO> mapToListPersonDTO(List<Person> personList) {
        List<PersonDTO> personDTOList = new ArrayList<>();

        personList.forEach(person -> {
            PersonDTO personDTO = new PersonDTO();
            personDTO.setId(person.getId());
            personDTO.setFirstName(person.getFirstName());
            personDTO.setLastName(person.getLastName());
            personDTO.setAddress(person.getAddress());
            personDTO.setGender(person.getGender());

            personDTOList.add(personDTO);
        });

        return personDTOList;
    }
}
