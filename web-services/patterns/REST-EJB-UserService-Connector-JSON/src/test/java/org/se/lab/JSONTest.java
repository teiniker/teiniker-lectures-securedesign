package org.se.lab;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class JSONTest
{
	/*
	 * Using the org.json library
	 */
	
	@Test
	public void testReadJSONObject()
	{
		String json = "{\"id\":3,\"username\":\"bart\",\"password\":\"Ls4jKY8G2ftFdy/bHTgIaRjID0Q=\"}";
		JSONObject obj = new JSONObject(json);
		int id = obj.getInt("id");
		String username = obj.getString("username");
		String password = obj.getString("password");
		
		Assert.assertEquals(3, id);
		Assert.assertEquals("bart", username);
		Assert.assertEquals("Ls4jKY8G2ftFdy/bHTgIaRjID0Q=", password);
	}

    @Test
    public void testReadJSONArray()
    {
        String json = 
                "[{\"id\":1,\"username\":\"homer\",\"password\":\"ijD8qepbRnIsva0kx0cKRCcYysg=\"},"
                + "{\"id\":2,\"username\":\"marge\",\"password\":\"xCSuPDv2U6I5jEO1wqvEQ/jPYhY=\"},"
                + "{\"id\":3,\"username\":\"bart\",\"password\":\"Ls4jKY8G2ftFdy/bHTgIaRjID0Q=\"},"
                + "{\"id\":4,\"username\":\"lisa\",\"password\":\"xO0U4gIN1F7bV7X7ovQN2TlSUF4=\"}]";
        
        JSONArray array = new JSONArray(json);
        Assert.assertEquals(4, array.length());
        
        JSONObject obj0 = array.getJSONObject(0);
        Assert.assertEquals(1, obj0.getInt("id"));
        Assert.assertEquals("homer", obj0.getString("username"));
        Assert.assertEquals("ijD8qepbRnIsva0kx0cKRCcYysg=", obj0.getString("password"));

        JSONObject obj1 = array.getJSONObject(1);
        Assert.assertEquals(2, obj1.getInt("id"));
        Assert.assertEquals("marge", obj1.getString("username"));
        Assert.assertEquals("xCSuPDv2U6I5jEO1wqvEQ/jPYhY=", obj1.getString("password"));
    
        JSONObject obj2 = array.getJSONObject(2);
        Assert.assertEquals(3, obj2.getInt("id"));
        Assert.assertEquals("bart", obj2.getString("username"));
        Assert.assertEquals("Ls4jKY8G2ftFdy/bHTgIaRjID0Q=", obj2.getString("password"));
        
        JSONObject obj3 = array.getJSONObject(3);
        Assert.assertEquals(4, obj3.getInt("id"));
        Assert.assertEquals("lisa", obj3.getString("username"));
        Assert.assertEquals("xO0U4gIN1F7bV7X7ovQN2TlSUF4=", obj3.getString("password"));
    }
    
    @Test
    public void testJsonObjectBuilder()
    {
          // build JSON
        JsonObject json = 
            Json.createObjectBuilder()
                .add("id", 1)
                .add("username", "homer")
                .add("password", "ijD8qepbRnIsva0kx0cKRCcYysg=")
            .build();
        
        String expected = "{\"id\":1,\"username\":\"homer\",\"password\":\"ijD8qepbRnIsva0kx0cKRCcYysg=\"}";
        Assert.assertEquals(expected, json.toString());
    }

    @Test
    public void testJsonArrayBuilder()
    {
        JsonArrayBuilder jsonBuilder = Json.createArrayBuilder();
        jsonBuilder.add(
            Json.createObjectBuilder()
                .add("id", 1)
                .add("username", "homer")
                .add("password", "ijD8qepbRnIsva0kx0cKRCcYysg="));
        jsonBuilder.add(
            Json.createObjectBuilder()
                .add("id", 2)
                .add("username", "marge")
                .add("password", "xCSuPDv2U6I5jEO1wqvEQ/jPYhY="));
               
        JsonArray json = jsonBuilder.build();

        System.out.println(json.toString());
    }
}
