package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Crossref extends Entity{

	/** possible parents = [incollection, inproceedings, article] **/
	Set<Entity>parent;

	/** the contents that defines this entity **/
	public static final String[] uniqueDescriptor = {"StringEntity"};

	/** no children, only contents **/
	public final boolean hasChildren = false;
	public StringEntity contents;

	/** equals depends on StringEntity contents **/
	public boolean equals(Crossref crossref) {
		return this.contents.equals(crossref.contents);
	}

	public static final SearchBy searchTier = SearchBy.StringEntity;

}
