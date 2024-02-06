
package org.se.lab.service;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    public ObjectFactory()
    {
    }

    public Order createOrder()
    {
        return new Order();
    }

    public OrderLine createOrderLine()
    {
        return new OrderLine();
    }

    public Product createProduct()
    {
        return new Product();
    }
}
