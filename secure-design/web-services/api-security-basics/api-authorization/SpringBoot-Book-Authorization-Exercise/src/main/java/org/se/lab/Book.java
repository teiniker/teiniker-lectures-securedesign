package org.se.lab;
import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable
{
    private static final long serialVersionUID = 1L;

    Book(long id, String author, String title, String isbn)
    {
        setId(id);
        setAuthor(author);
        setTitle(title);
        setIsbn(isbn);
    }

    Book()
    {
    }


    private Long id;
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    private String author;
    public String getAuthor()
    {
        return author;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }

    private String title;
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }


    private String isbn;
    public String getIsbn()
    {
        return isbn;
    }
    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }


    /*
     * Object methods
     */
    @Override
    public String toString()
    {
        return getId() + "," + getAuthor() + "," + getTitle() + ","+ getIsbn();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book article = (Book) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}