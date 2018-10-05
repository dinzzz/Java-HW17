package hr.fer.zemris.java.galerija.rest;

import java.io.IOException;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import hr.fer.zemris.java.galerija.ImageLoad;

/**
 * Class that represents tags retrievement for the usage of the gallery web
 * application and button creation.
 * 
 * @author Dinz
 *
 */
@Path("/tags")
public class TagJSON {

	/**
	 * Method which gets all the available tags of the predefined pictures.
	 * 
	 * @return Response.
	 * @throws IOException
	 */
	@GET
	@Produces("application/json")
	public Response getTags() throws IOException {
		Set<String> lista = ImageLoad.getTags();

		JSONObject result = new JSONObject();

		result.put("tags", lista);

		return Response.status(Status.OK).entity(result.toString()).build();
	}
}
