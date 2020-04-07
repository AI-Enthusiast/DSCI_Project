package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Title extends Entity{

	/** possible parents = [incollection, www, book, proceedings, inproceedings, article] **/
	Set<Entity>parent;

	/** the children that define this entity **/
	public static final String[] uniqueDescriptor = {"tt","sub","i","sup"};

	/** children = [tt, sub, i, sup] **/
	public Set<Tt> tt;
	public Set<Sub> sub;
	public Set<I> i;
	public Set<Sup> sup;

	/** Equality depends on AllChildren **/
	public boolean equals(Title title) {
		return this.tt.equals(title.tt) && this.sub.equals(title.sub) && this.i.equals(title.i) && this.sup.equals(title.sup);
	}

	public static final SearchBy searchTier = SearchBy.AllChildren;

}
