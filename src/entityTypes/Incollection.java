package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Incollection extends Entity{

	/** possible parents = [dblp] **/
	Set<Dblp> parent;

	/** the children that define this entity **/
	public static final String[] uniqueDescriptor = {"ee","year","title","booktitle","url"};

	/** children = [ee, pages, year, author, cite, title, booktitle, crossref, url] **/
	public Ee ee;
	public Pages pages;
	public Year year;
	public Set<Author> author;
	public Set<Cite> cite;
	public Title title;
	public Booktitle booktitle;
	public Crossref crossref;
	public Url url;

	/** Equality depends on DescriptorChildren **/
	public boolean equals(Incollection incollection) {
		return this.ee.equals(incollection.ee) && this.year.equals(incollection.year) && this.title.equals(incollection.title) && this.booktitle.equals(incollection.booktitle) && this.url.equals(incollection.url);
	}

	public static final SearchBy searchTier = SearchBy.DescriptorChildren;

}
