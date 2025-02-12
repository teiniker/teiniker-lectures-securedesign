# JSON Schema

> JSON Schema is a vocabulary that allows you to define the structure, required 
> properties, data types, and other constraints for JSON documents. 
 
We can think of it as a blueprint or contract for your JSON data. 
Here are some key points to understand the concept:

* **Structure Definition:**  
   JSON Schema lets you describe the expected structure of your JSON data. For example, you can specify that an object must have properties like `"id"`, `"name"`, and `"email"`, and that `"id"` should be an integer, `"name"` a string, etc.

* **Validation:**  
   By defining a JSON Schema, you can automatically validate incoming JSON data against the schema. This ensures that the data meets the required format and constraints before processing it further. Many libraries (like networknt JSON Schema Validator or everit-org JSON Schema) can perform this validation.

* **Data Types and Constraints:**  
   You can enforce data types (such as string, number, boolean, object, or array) and constraints like minimum/maximum values, string patterns (using regular expressions), and allowed enumerations. This helps catch errors early by ensuring that the data conforms to expected rules.

* **Documentation and Communication:**  
   JSON Schema serves as a form of documentation for your API or data format. It clearly communicates to developers what data is expected, which improves collaboration and reduces integration errors.

* **Standardization:**  
   JSON Schema is defined by a formal specification (currently in several drafts, like Draft-07 or later). This standardization ensures consistency in how schemas are written and interpreted across different tools and platforms.

* **Extensibility:**  
   The schema can include features such as combining schemas (using `allOf`, `anyOf`, `oneOf`, etc.), referencing other schemas (`$ref`), and adding metadata like titles and descriptions. This makes it a powerful tool not only for validation but also for building complex and reusable data definitions.

_Example:_ JSON document representing a book

```json
{
  "id": 1,
  "author": "Joshua Bloch",
  "title": "Effective Java",
  "isbn": "978-0134685991"
}
```

A JSON Schema for this might look like:

```json
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "title": "Book",
  "type": "object",
  "properties": {
    "id": { "type": "integer" },
    "author": { "type": "string" },
    "title": { "type": "string" },
    "isbn": { "type": "string" }
  },
  "required": ["id", "author", "title", "isbn"]
}
```

This schema tells us that:
- The JSON document must be an object.
- It must have the properties `"id"`, `"author"`, `"title"`, and `"isbn"`.
- The `"id"` must be an integer, and the others must be strings.
- All listed properties are required.


## References

* [JSON SChema Specification](https://json-schema.org/)

*Egon Teiniker, 2017-2025, GPL v3.0*
