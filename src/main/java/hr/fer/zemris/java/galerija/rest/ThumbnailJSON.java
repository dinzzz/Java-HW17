package hr.fer.zemris.java.galerija.rest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.zemris.java.galerija.ImageInfo;
import hr.fer.zemris.java.galerija.ImageLoad;

/**
 * Class that represents retrievement of the thumbnails for the context of the
 * gallery web application.
 * 
 * @author Dinz
 *
 */
@Path("/thumbnails")
public class ThumbnailJSON {
	
	 @Context
	 private ServletContext context;

	/**
	 * Method which gets and forwards thumbnails based on the tag provided.
	 * 
	 * @param tag
	 *            Provided tag.
	 * @return Response.
	 * @throws IOException
	 */
	@Path("{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getThumbnails(@PathParam("tag") String tag) throws IOException {
		String tPath = context.getRealPath("/WEB-INF/thumbnails/");

		List<ImageInfo> imagesToShow = new ArrayList<>();
		List<ImageInfo> images = ImageLoad.getImages();

		for (ImageInfo img : images) {
			if (img.getTags().contains(tag)) {
				imagesToShow.add(img);
			}
		}

		for (ImageInfo info : imagesToShow) {
			if (!Paths.get(tPath).toFile().exists()) {
				Paths.get(tPath).toFile().mkdir();
			}
			java.nio.file.Path path = Paths.get(tPath + info.getName());
			if (!path.toFile().exists()) {
				java.nio.file.Path original = Paths.get(context.getRealPath(("/WEB-INF/slike/" + info.getName())))
						.toAbsolutePath();
				BufferedImage originalImg = ImageIO.read(original.toFile());
				BufferedImage resizedImg = resize(originalImg);
				ImageIO.write(resizedImg, "jpg", path.toFile());
			}

		}
		JSONObject result = new JSONObject();
		JSONArray thumbs = new JSONArray();
		for (ImageInfo img : imagesToShow) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			BufferedImage originalImg = ImageIO.read(Paths.get(getAbsoluteThumbPath(img.getName(), tPath)).toFile());
			ImageIO.write(originalImg, "jpg", bos);
			byte[] imageBytes = bos.toByteArray();

			String header = "data:image/jpg;base64, ";
			String imgString = java.util.Base64.getEncoder().encodeToString(imageBytes);

			header += imgString;
			JSONObject jo = new JSONObject();
			jo.put("thumbnail", header);
			jo.put("img", img.getName());
			thumbs.put(jo);
		}
		result.put("thumbs", thumbs);

		return Response.status(Status.OK).entity(result.toString()).build();

	}

	/**
	 * Method which resizes an original image to the desired thumbnail size.
	 * 
	 * @param originalImg
	 *            Original image.
	 * @return Resized thumbnail image.
	 */
	private BufferedImage resize(BufferedImage originalImg) {
		BufferedImage resized = new BufferedImage(150, 150, originalImg.getType());
		Graphics2D g = resized.createGraphics();
		g.drawImage(originalImg, 0, 0, 150, 150, null);
		g.dispose();
		return resized;
	}

	/**
	 * Method which gets the absolute path of created thumbnail.
	 * 
	 * @param image
	 *            Thumbnail name.
	 * @param tPath
	 *            Thumbnail folder path.
	 * @return Absolute path.
	 */
	private String getAbsoluteThumbPath(String image, String tPath) {
		java.nio.file.Path path = Paths.get(tPath + image).toAbsolutePath();
		return path.toString();
	}
}
