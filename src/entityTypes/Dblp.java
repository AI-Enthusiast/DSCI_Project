package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Dblp extends Entity{

	/** no parents, this is the root **/
	

	/** the contents that defines this entity **/
	public static final String[] uniqueDescriptor = {"StringEntity"};

	/** no children, only contents **/
	public final boolean hasChildren = false;
	public StringEntity contents;

	/** equals depends on StringEntity contents **/
	public boolean equals(Dblp dblp) {
		return this.contents.equals(dblp.contents);
	}

	public static final SearchBy searchTier = SearchBy.StringEntity;

}
