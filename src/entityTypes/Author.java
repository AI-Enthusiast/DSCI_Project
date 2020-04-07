package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Author extends Entity{

	/** possible parents = [incollection, www, book, inproceedings, article] **/
	Set<Entity>parent;

	/** the contents that defines this entity **/
	public static final String[] uniqueDescriptor = {"StringEntity"};

	/** no children, only contents **/
	public final boolean hasChildren = false;
	public StringEntity contents;

	/** equals depends on StringEntity contents **/
	public boolean equals(Author author) {
		return this.contents.equals(author.contents);
	}

	public static final SearchBy searchTier = SearchBy.StringEntity;

}
