package org.se.lab.data;

import javax.persistence.*;

@Entity
public class Book
{
    Book()
    {
    }

    public Book(long id, String title, String author)
    {
        setId(id);
        setTitle(title);
        setAuthor(author);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    @Column(nullable = false, unique = true)
    private String title;
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    @Column(nullable = false)
    private String author;
    public String getAuthor()
    {
        return author;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }
}
