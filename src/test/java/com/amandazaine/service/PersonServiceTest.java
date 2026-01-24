package com.amandazaine.service;

import com.amandazaine.dto.v1.PersonDTO;
import com.amandazaine.exception.RequiredObjectIsNullException;
import com.amandazaine.mapper.PersonMapper;
import com.amandazaine.mock.MockPerson;
import com.amandazaine.modelOrEntity.Person;
import com.amandazaine.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    private MockPerson mockPerson;

    @InjectMocks
    private PersonService personService;

    @InjectMocks
    private PersonMapper personMapper;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        mockPerson = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Person person = mockPerson.mockEntity(1);
        person.setId(1L);

        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        PersonDTO personDTO = personService.findById(1L);

        assertNotNull(personDTO);

        assertNotNull(
                personDTO
                        .getLinks()
                        .stream()
                        .anyMatch(link -> link.getRel().value().equals("self") &&
                                    link.getHref().endsWith("/api/person/v1/1") &&
                                    link.getType().equals("GET")
                        )

        );

        assertEquals(person.getId(), personDTO.getId());
        assertEquals(person.getFirstName(), personDTO.getFirstName());
        assertEquals(person.getLastName(), personDTO.getLastName());
        assertEquals(person.getAddress(), personDTO.getAddress());
        assertEquals(person.getGender(), personDTO.getGender());
    }

    @Test
    void save() {
        Person person = mockPerson.mockEntity(1);
        person.setId(1L);

        PersonDTO personDTOmock = mockPerson.mockDTO(1);

        when(personRepository.save(person)).thenReturn(person);

        PersonDTO personDTOresult = personService.save(personDTOmock);

        assertNotNull(personDTOresult);

        assertNotNull(
                personDTOresult
                        .getLinks()
                        .stream()
                        .anyMatch(link -> link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("GET")
                        )

        );

        assertEquals(person.getId(), personDTOresult.getId());
        assertEquals(person.getFirstName(), personDTOresult.getFirstName());
        assertEquals(person.getLastName(), personDTOresult.getLastName());
        assertEquals(person.getAddress(), personDTOresult.getAddress());
        assertEquals(person.getGender(), personDTOresult.getGender());
    }

    @Test
    void saveNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> personService.save(null));

        String expectedMessage = "It is not allowed to persist a null object";
        assertEquals(expectedMessage, exception.getMessage());
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void update() {
        Person person = mockPerson.mockEntity(1);
        person.setId(1L);

        PersonDTO personDTOmock = mockPerson.mockDTO(1);

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);

        PersonDTO personDTOresult = personService.update(personDTOmock);

        assertNotNull(personDTOresult);

        assertNotNull(
                personDTOresult
                        .getLinks()
                        .stream()
                        .anyMatch(link -> link.getRel().value().equals("self") &&
                                link.getHref().endsWith("/api/person/v1/1") &&
                                link.getType().equals("GET")
                        )

        );

        assertEquals(person.getId(), personDTOresult.getId());
        assertEquals(person.getFirstName(), personDTOresult.getFirstName());
        assertEquals(person.getLastName(), personDTOresult.getLastName());
        assertEquals(person.getAddress(), personDTOresult.getAddress());
        assertEquals(person.getGender(), personDTOresult.getGender());
    }

    @Test
    void updateNullPerson() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> personService.update(null));

        String expectedMessage = "It is not allowed to persist a null object";
        assertEquals(expectedMessage, exception.getMessage());
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void deleteById() {
        Person person = mockPerson.mockEntity(1);
        person.setId(1L);

        when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

        personService.deleteById(person.getId());

        verify(personRepository, times(1)).findById(person.getId());
        verify(personRepository, times(1)).deleteById(person.getId());
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void findAll() {
        List<Person> personList = mockPerson.mockEntityList();

        when(personRepository.findAll()).thenReturn(personList);

        List<PersonDTO> personDTOList = personService.findAll();

        assertNotNull(personDTOList);
        assertEquals(personDTOList.size(), personList.size());

        var personOne = personList.get(0);
        var personOneMock  = personList.get(0);
        assertEquals(personOne.getId(), personOneMock.getId());

        var personTwo = personList.get(5);
        var personTwoMock  = personList.get(5);
        assertEquals(personTwo.getId(), personTwoMock.getId());

        var personThree = personList.get(11);
        var personThreeMock  = personList.get(11);
        assertEquals(personThree.getId(), personThreeMock.getId());
    }
}