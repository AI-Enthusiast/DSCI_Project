package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Url extends Entity{

	/** possible parents = [incollection, www, book, proceedings, inproceedings, article] **/
	Set<Entity>parent;

	/** the contents that defines this entity **/
	public static final String[] uniqueDescriptor = {"StringEntity"};

	/** no children, only contents **/
	public final boolean hasChildren = false;
	public StringEntity contents;

	/** equals depends on StringEntity contents **/
	public boolean equals(Url url) {
		return this.contents.equals(url.contents);
	}

	public static final SearchBy searchTier = SearchBy.StringEntity;

}
