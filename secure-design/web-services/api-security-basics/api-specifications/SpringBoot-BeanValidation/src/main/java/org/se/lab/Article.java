package org.se.lab;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class Article implements Serializable
{
    private static final long serialVersionUID = 1L;

    Article(Long id, String description, long price)
    {
        setId(id);
        setDescription(description);
        setPrice(price);
    }

    Article()
    {
    }


    @Min(value=0, message="id must be a positive number!")
    private Long id;
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }


    @NotNull
    @Size(min = 10,max = 255, message = "Description must have at least 10 characters!")
    private String description;
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    @Min(value=0, message="price must be a positive number!")
    private long price;
    public long getPrice()
    {
        return price;
    }
    public void setPrice(long price)
    {
        this.price = price;
    }


    /*
     * Object methods
     */
    @Override
    public String toString()
    {
        return getId() + "," + getDescription() + "," + getPrice();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}