package org.se.lab;

public class Product
{
    public Product(String name, String quantity)
    {
        this.name = name;
        this.quantity = quantity;
    }

    private String name;
    public String getName()
    {
        return name;
    }

    private String quantity;
    public String getQuantity()
    {
        return quantity;
    }

    @Override
    public String toString()
    {
        return "Product{name='" + name + "', quantity='" + quantity + "'}";
    }
}
