package com.amandazaine.repository;

import com.amandazaine.modelOrEntity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
