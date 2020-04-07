package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Tt extends Entity{

	/** possible parents = [title] **/
	Set<Title> parent;

	/** the contents that defines this entity **/
	public static final String[] uniqueDescriptor = {"StringEntity"};

	/** no children, only contents **/
	public final boolean hasChildren = false;
	public StringEntity contents;

	/** equals depends on StringEntity contents **/
	public boolean equals(Tt tt) {
		return this.contents.equals(tt.contents);
	}

	public static final SearchBy searchTier = SearchBy.StringEntity;

}
