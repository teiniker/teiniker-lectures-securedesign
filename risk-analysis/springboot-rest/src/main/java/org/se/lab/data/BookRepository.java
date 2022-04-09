package org.se.lab.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>
{
    List<Book> findByTitle(String title);
}