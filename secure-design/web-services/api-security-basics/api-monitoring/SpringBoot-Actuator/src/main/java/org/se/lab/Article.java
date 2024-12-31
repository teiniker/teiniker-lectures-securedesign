package org.se.lab;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="article")
public class Article implements Serializable
{
    private static final long serialVersionUID = 1L;

    Article(String description, long price)
    {
        setDescription(description);
        setPrice(price);
    }

    Article()
    {
    }


    @Id
    @Column(name="ID")
    @GeneratedValue
    private Long id;
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id)
    {
        if(id < 0)
            throw new IllegalArgumentException("Invalid parameter id: " + id);
        this.id = id;
    }


    @Column(name="DESCRIPTION")
    private String description;
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        if(description == null)
            throw new IllegalArgumentException("Invalid parameter description!");
        this.description = description;
    }


    @Column(name="PRICE")
    private long price;
    public long getPrice()
    {
        return price;
    }
    public void setPrice(long price)
    {
        if(price < 0)
            throw new IllegalArgumentException("Invalid parameter price: " + price);
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