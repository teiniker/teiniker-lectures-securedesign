package org.se.lab;

import java.util.UUID;

import org.junit.Test;

/*
 * UUID is a class that represents an immutable universally unique identifier 
 * (UUID). A UUID represents a 128-bit value.
 * 
 * public static UUID randomUUID()
 *  Static factory to retrieve a type 4 (pseudo randomly generated) UUID. 
 *  The UUID is generated using a cryptographically strong pseudo random 
 *  number generator.
 */

public class UUIDTest
{
    @Test
    public void testUUID()
    {
        UUID uuid = UUID.randomUUID();
        
        System.out.println("UUID String : " + uuid);
        System.out.println("Size of UUID: " + uuid.toString().length());
    }
}
