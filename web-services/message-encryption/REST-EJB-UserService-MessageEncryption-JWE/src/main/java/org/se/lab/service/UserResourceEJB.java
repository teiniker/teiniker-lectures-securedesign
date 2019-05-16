package org.se.lab.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.text.ParseException;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.data.UserDAO;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectDecrypter;

@Path("/v1/users")
@Stateless
public class UserResourceEJB
{
	private final Logger LOG = Logger.getLogger(UserResourceEJB.class);
	private static String keyString = System.getProperty("jwe.key");
	
	@Inject
	private UserDAO dao;
		
	public UserResourceEJB()
	{
		LOG.debug(UserResourceEJB.class.getName() + " created");
	}
	
	
	@POST
	public Response insert(Reader reader, @HeaderParam("Content-Type") String contentType)
	{
		LOG.info("insert: " + contentType);

		if(!"application/jose+json".equals(contentType))
		    return Response.status(Status.NOT_ACCEPTABLE).build();
		    
		try
        {
		    // Read request content
		    BufferedReader br = new BufferedReader(reader);
		    String line; 
		    StringBuilder content = new StringBuilder();
            while ((line = br.readLine()) != null) 
            {
                content.append(line);
            }
            
            // Decrypt jwe string
            JWEObject jweObject = JWEObject.parse(content.toString());
            byte[] key = Hex.decodeHex(keyString.toCharArray());
            jweObject.decrypt(new DirectDecrypter(key));
            Payload payload = jweObject.getPayload();
            String xml = payload.toString();
            
            // Convert XML into UserDTO
            UserDTO userDTO = convertToUserDTO(xml);
            User u = userDTO.toUser();
            User user = dao.createUser(u.getUsername(), u.getPassword());
            return Response.created(URI.create("/users/" + user.getId())).build();
        }
        catch (IOException | ParseException | DecoderException | JOSEException e)
        {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } 
	}
	
    protected UserDTO convertToUserDTO(String xml)
    {
        JAXBContext context;
        try
        {
            context = JAXBContext.newInstance(UserDTO.class);
            StringReader reader = new StringReader(xml);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            UserDTO dto = (UserDTO) unmarshaller.unmarshal(reader);
            return dto;
        }
        catch (JAXBException e)
        {
            throw new IllegalStateException("Can't unmarshall XML!", e);
        }
    }
	

    @GET
	@Produces({"application/xml", "application/json"})
	public List<UserDTO> findAll()
	{
		LOG.debug("find all Users");
		
		List<User> list = dao.findAll();
		List<UserDTO> result = UserDTO.toUserDTOList(list);
		return result;
	}
	
	
	@GET
	@Path("{id}")
	@Produces({"application/xml", "application/json"})
	public UserDTO findById(@PathParam("id") int id) 
		throws WebApplicationException
	{
		LOG.debug("find User with id=" + id);

		User user = dao.findById(id);

		if(user == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		else
			return new UserDTO(user);
	}
}
