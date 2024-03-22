package org.se.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Article
{
	Article(Long id, String description, long price)
	{
		setDescription(description);
		setPrice(price);
		setId(id);
	}

	Article()
	{
	}

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
        return Objects.equals(id, article.id);
    }

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}
}