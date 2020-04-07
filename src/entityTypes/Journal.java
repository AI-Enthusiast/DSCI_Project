package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Journal extends Entity{

	/** possible parents = [article] **/
	Set<Article> parent;

	/** the contents that defines this entity **/
	public static final String[] uniqueDescriptor = {"StringEntity"};

	/** no children, only contents **/
	public final boolean hasChildren = false;
	public StringEntity contents;

	/** equals depends on StringEntity contents **/
	public boolean equals(Journal journal) {
		return this.contents.equals(journal.contents);
	}

	public static final SearchBy searchTier = SearchBy.StringEntity;

}
