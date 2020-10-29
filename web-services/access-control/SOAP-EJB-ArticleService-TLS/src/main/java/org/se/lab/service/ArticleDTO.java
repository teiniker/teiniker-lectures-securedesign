package org.se.lab.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.se.lab.data.Article;



@XmlRootElement(name="article")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArticleDTO
{
	/*
	 * A default constructor is needed to make the JAXB marshaler happy.
	 */
	protected ArticleDTO()
	{
		this(0, "", 0L);
	}
	
	public ArticleDTO(int id, String description, long price)
	{
		setId(id);
		setDescription(description);
		setPrice(price);
	}
	
	
	/*
	 * Converter methods: Article <=> ArticleDTO
	 */
	
	public ArticleDTO(Article article)
	{
		this(article.getId(), article.getDescription(), article.getPrice());
	}
	
	public Article toArticle()
	{
		return new Article(getId(), getDescription(), getPrice());
	}
	
	public static List<Article> toUserList(List<ArticleDTO> dtoList)
	{
		if(dtoList == null)
			throw new IllegalArgumentException("Parameter List<UserDTO> is null!");
		
		List<Article> result = new ArrayList<Article>();
		for(ArticleDTO  dto : dtoList)
		{
			result.add(dto.toArticle());
		}
		return result;
	}
	
	public static List<ArticleDTO> toArticleDTOList(List<Article> list)
	{
		List<ArticleDTO> result = new ArrayList<ArticleDTO>();
		for(Article article : list)
		{
			result.add(new ArticleDTO(article));
		}
		return result;
	}
	
	
	/*
	 * Property: id:int
	 */
	@XmlAttribute
	private int id;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	
	/*
	 * Property: description:String
	 */
	@XmlElement
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
	
	
	/*
	 * Property: price:String
	 */
	@XmlAttribute
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
		return "User [id=" + getId() + ", description=" + getDescription() + ", price=" + getPrice() + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleDTO other = (ArticleDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}	
}
