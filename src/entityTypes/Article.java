package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Article extends Entity{

	/** possible parents = [dblp] **/
	Set<Dblp> parent;

	/** the child that defines this entity **/
	public static final String[] uniqueDescriptor = {"Title"};

	/** children = [ee, note, year, author, title, cdrom, url, volume, number, journal, pages, month, cite, booktitle, crossref] **/
	public Set<Ee> ee;
	public Note note;
	public Year year;
	public Set<Author> author;
	public Title title;
	public Cdrom cdrom;
	public Url url;
	public Volume volume;
	public Number number;
	public Journal journal;
	public Pages pages;
	public Month month;
	public Set<Cite> cite;
	public Booktitle booktitle;
	public Crossref crossref;

	/** Equality depends on DescriptorChildren **/
	public boolean equals(Article article) {
		return this.title.equals(article.title);
	}

	public static final SearchBy searchTier = SearchBy.DescriptorChildren;

}
