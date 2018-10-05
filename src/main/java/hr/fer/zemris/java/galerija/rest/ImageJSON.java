package hr.fer.zemris.java.galerija.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.JSONObject;

import hr.fer.zemris.java.galerija.ImageInfo;
import hr.fer.zemris.java.galerija.ImageLoad;

/**
 * Class that represents image retrievement in the context of the gallery web
 * application.
 * 
 * @author Dinz
 *
 */
@Path("/images")
public class ImageJSON {
	
	@Context
	private ServletContext context;

	/**
	 * Method that gets and forwards the image info via JSON format by the given
	 * image parameter in the URL.
	 * 
	 * @param img
	 *            Image parameter, name of the image.
	 * @return Response.
	 * @throws IOException
	 */
	@Path("{img}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImage(@PathParam("img") String img) throws IOException {
		String iPath = context.getRealPath("/WEB-INF/slike/");

		ImageInfo imgToShow = null;
		List<ImageInfo> images = ImageLoad.getImages();
		for (ImageInfo info : images) {
			if (info.getName().equals(img)) {
				imgToShow = info;
			}
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		BufferedImage originalImg = ImageIO.read(Paths.get(iPath + imgToShow.getName()).toAbsolutePath().toFile());
		ImageIO.write(originalImg, "jpg", bos);
		byte[] imageBytes = bos.toByteArray();
		String header = "data:image/jpg;base64, ";
		String imgString = java.util.Base64.getEncoder().encodeToString(imageBytes);
		header += imgString;

		int count = 0;
		StringBuilder sb = new StringBuilder();
		for (String tag : imgToShow.getTags()) {
			sb.append(tag);
			if (count < imgToShow.getTags().size() - 1) {
				sb.append(", ");
				count++;
			}
		}

		JSONObject jo = new JSONObject();
		jo.put("image", header);
		jo.put("description", imgToShow.getDescription());
		jo.put("tags", sb.toString());

		return Response.status(Status.OK).entity(jo.toString()).build();
	}

}
