package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Www extends Entity{

	/** possible parents = [dblp] **/
	Set<Dblp> parent;

	/** the children that define this entity **/
	public static final String[] uniqueDescriptor = {"year","author","title","url"};

	/** children = [year, author, title, url] **/
	public Year year;
	public Author author;
	public Title title;
	public Url url;

	/** Equality depends on DescriptorChildren **/
	public boolean equals(Www www) {
		return this.year.equals(www.year) && this.author.equals(www.author) && this.title.equals(www.title) && this.url.equals(www.url);
	}

	public static final SearchBy searchTier = SearchBy.DescriptorChildren;

}
