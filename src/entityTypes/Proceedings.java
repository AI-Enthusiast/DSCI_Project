package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Proceedings extends Entity{

	/** possible parents = [dblp] **/
	Set<Dblp> parent;

	/** the children that define this entity **/
	public static final String[] uniqueDescriptor = {"year","title","booktitle","url"};

	/** children = [volume, ee, editor, year, series, isbn, publisher, title, booktitle, url] **/
	public Volume volume;
	public Ee ee;
	public Set<Editor> editor;
	public Year year;
	public Series series;
	public Isbn isbn;
	public Publisher publisher;
	public Title title;
	public Booktitle booktitle;
	public Url url;

	/** Equality depends on DescriptorChildren **/
	public boolean equals(Proceedings proceedings) {
		return this.year.equals(proceedings.year) && this.title.equals(proceedings.title) && this.booktitle.equals(proceedings.booktitle) && this.url.equals(proceedings.url);
	}

	public static final SearchBy searchTier = SearchBy.DescriptorChildren;

}
