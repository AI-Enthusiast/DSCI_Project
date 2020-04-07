package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Sup extends Entity{

	/** possible parents = [sub, i, title, sup] **/
	Set<Entity>parent;

	/** the children that define this entity **/
	public static final String[] uniqueDescriptor = {"sub","i","sup"};

	/** children = [sub, i, sup] **/
	public Sub sub;
	public Set<I> i;
	public Sup sup;

	/** Equality depends on AllChildren **/
	public boolean equals(Sup sup) {
		return this.sub.equals(sup.sub) && this.i.equals(sup.i) && this.sup.equals(sup.sup);
	}

	public static final SearchBy searchTier = SearchBy.AllChildren;

}
