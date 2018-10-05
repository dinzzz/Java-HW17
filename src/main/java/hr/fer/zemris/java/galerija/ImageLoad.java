package hr.fer.zemris.java.galerija;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class which provides loading of the data for the JSON usage.
 * 
 * @author Dinz
 *
 */
@WebListener
public class ImageLoad implements ServletContextListener{

	/**
	 * Set of tags.
	 */
	private static Set<String> tags;

	/**
	 * List of images.
	 */
	private static List<ImageInfo> images;

	/**
	 * Constructs a new instance of ImageLoad and stores all available images and
	 * tags.
	 * 
	 * @throws IOException
	 */
	public ImageLoad() throws IOException {
		
	}

	/**
	 * Returns the set of available tags.
	 * 
	 * @return Tags.
	 */
	public static Set<String> getTags() {
		return tags;
	}

	/**
	 * Returns the list of available images.
	 * 
	 * @return Images.
	 */
	public static List<ImageInfo> getImages() {
		return images;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();
		try {
		tags = new HashSet<>();
		images = new ArrayList<>();

		List<String> lines = Files.readAllLines(Paths.get(ctx.getRealPath("/WEB-INF/opisnik.txt")));

		for (int i = 0; i < lines.size(); i++) {
			String url = lines.get(i++);
			String description = lines.get(i++);
			String tagline = lines.get(i);

			String[] imageTags = tagline.split(",");
			List<String> imgTags = new ArrayList<>();
			for (String image : imageTags) {
				imgTags.add(image.trim());
			}

			ImageInfo img = new ImageInfo(url, description, imgTags);
			images.add(img);

			for (String tag : imgTags) {
				tags.add(tag.trim());
			}
			
			System.out.println(images.size());

		}
		} catch (IOException ex) {
			
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
