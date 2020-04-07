package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Sub extends Entity{

	/** possible parents = [sub, i, title, sup] **/
	Set<Entity>parent;

	/** the children that define this entity **/
	public static final String[] uniqueDescriptor = {"sub","i","sup"};

	/** children = [sub, i, sup] **/
	public Sub sub;
	public Set<I> i;
	public Sup sup;

	/** Equality depends on AllChildren **/
	public boolean equals(Sub sub) {
		return this.sub.equals(sub.sub) && this.i.equals(sub.i) && this.sup.equals(sub.sup);
	}

	public static final SearchBy searchTier = SearchBy.AllChildren;

}
