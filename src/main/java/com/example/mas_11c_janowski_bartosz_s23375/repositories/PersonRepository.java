package com.example.mas_11c_janowski_bartosz_s23375.repositories;

import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository  extends CrudRepository<Person, Long> {



}
