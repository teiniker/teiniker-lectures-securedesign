package org.se.lab.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long>
{
    List<Book> findByTitle(String title);
}