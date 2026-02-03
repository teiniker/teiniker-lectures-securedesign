package org.se.lab;

// {"id":1,"author":"Joshua Bloch","title":"Effective Java","isbn":"978-0134685991"}
public record Book(long id, String author, String title, String isbn) {};
