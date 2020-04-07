package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Series extends Entity{

	/** possible parents = [book, proceedings] **/
	Set<Entity>parent;

	/** the contents that defines this entity **/
	public static final String[] uniqueDescriptor = {"StringEntity"};

	/** no children, only contents **/
	public final boolean hasChildren = false;
	public StringEntity contents;

	/** equals depends on StringEntity contents **/
	public boolean equals(Series series) {
		return this.contents.equals(series.contents);
	}

	public static final SearchBy searchTier = SearchBy.StringEntity;

}
