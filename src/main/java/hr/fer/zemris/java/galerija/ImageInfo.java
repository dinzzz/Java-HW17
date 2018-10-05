package hr.fer.zemris.java.galerija;

import java.util.List;

/**
 * Class that represents an image in the context of the gallery web application.
 * The image holds its name, its description and tags.
 * 
 * @author Dinz
 *
 */
public class ImageInfo {

	/**
	 * Name of the file by which the image is saved.
	 */
	private String name;

	/**
	 * Description of the image.
	 */
	private String description;

	/**
	 * Tags.
	 */
	List<String> tags;

	/**
	 * Constructs a new instance of ImageInfo.
	 * 
	 * @param name
	 *            Name of the file.
	 * @param description
	 *            Description.
	 * @param tags
	 *            Tags.
	 */
	public ImageInfo(String name, String description, List<String> tags) {
		super();
		this.name = name;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Gets the name of the image.
	 * 
	 * @return Name of the image.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the image.
	 * 
	 * @param name
	 *            Name to be set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description of the image.
	 * 
	 * @return Description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the decription of the image.
	 * 
	 * @param description
	 *            Description to be set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets tags.
	 * 
	 * @return Tags.
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Sets tags.
	 * 
	 * @param tags
	 *            Tags to be set.
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
