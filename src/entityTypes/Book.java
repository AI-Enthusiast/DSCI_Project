package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Book extends Entity{

	/** possible parents = [dblp] **/
	Set<Dblp> parent;

	/** the children that define this entity **/
	public static final String[] uniqueDescriptor = {"year","publisher","title","url"};

	/** children = [ee, volume, editor, note, year, author, series, isbn, publisher, title, booktitle, url] **/
	public Ee ee;
	public Volume volume;
	public Set<Editor> editor;
	public Note note;
	public Year year;
	public Author author;
	public Series series;
	public Set<Isbn> isbn;
	public Publisher publisher;
	public Title title;
	public Booktitle booktitle;
	public Url url;

	/** Equality depends on DescriptorChildren **/
	public boolean equals(Book book) {
		return this.year.equals(book.year) && this.publisher.equals(book.publisher) && this.title.equals(book.title) && this.url.equals(book.url);
	}

	public static final SearchBy searchTier = SearchBy.DescriptorChildren;

}
