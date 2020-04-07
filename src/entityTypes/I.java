package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class I extends Entity{

	/** possible parents = [sub, i, title, sup] **/
	Set<Entity>parent;

	/** the children that define this entity **/
	public static final String[] uniqueDescriptor = {"sub","i","sup"};

	/** children = [sub, i, sup] **/
	public Sub sub;
	public I i;
	public Sup sup;

	/** Equality depends on AllChildren **/
	public boolean equals(I i) {
		return this.sub.equals(i.sub) && this.i.equals(i.i) && this.sup.equals(i.sup);
	}

	public static final SearchBy searchTier = SearchBy.AllChildren;

}
