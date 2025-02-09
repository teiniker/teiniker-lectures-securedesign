package org.se.lab;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>
{
    // Additional query methods (if needed) can be defined here.
}
