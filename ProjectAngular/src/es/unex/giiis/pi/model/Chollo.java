package es.unex.giiis.pi.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Chollo {

	private long id;
	private String title;
	private String description;
	private String link;
	private String image;
	private float price;

	private long idu;
	private long ids;

	private int likes;
	private int soldout;

	public boolean validate(Map<String, String> messages){
		if(title.trim().isEmpty()||title==null) {
			messages.put("error", "Empty name");
		} else if(!title.trim().matches("[A-Za-z·ÈÌÛ˙Ò¡…Õ”⁄]{2,}([\\s][A-Za-z·ÈÌÛ˙Ò¡…Õ”⁄]{2,})*")) {
			messages.put("error", "Invalid name: " + title.trim());
		}
		if(messages.isEmpty()) return true; 
		else return false;
	}

	public int getSoldout() {
		return soldout;
	}
	public void setSoldout(int soldout) {
		this.soldout = soldout;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public long getIds() {
		return ids;
	}
	public void setIds(long ids) {
		this.ids = ids;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public long getIdu() {
		return idu;
	}
	public void setIdu(long idu) {
		this.idu = idu;
	}

	@Override
	public boolean equals(Object o) 
	{
		// If the object is compared with itself then return true   
		if (o == this) { 
			return true; 
		} 

		/* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
		if (!(o instanceof Chollo)) { 
			return false; 
		} 

		// typecast o to Complex so that we can compare data members  
		Chollo c = (Chollo) o; 

		// Compare the data members and return accordingly  
		return Long.compare(id, c.id) == 0
				&& Double.compare(id, c.id) == 0; 
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}



}
