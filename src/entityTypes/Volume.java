package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Volume extends Entity{

	/** possible parents = [book, proceedings, article] **/
	Set<Entity>parent;

	/** the contents that defines this entity **/
	public static final String[] uniqueDescriptor = {"StringEntity"};

	/** no children, only contents **/
	public final boolean hasChildren = false;
	public StringEntity contents;

	/** equals depends on StringEntity contents **/
	public boolean equals(Volume volume) {
		return this.contents.equals(volume.contents);
	}

	public static final SearchBy searchTier = SearchBy.StringEntity;

}
