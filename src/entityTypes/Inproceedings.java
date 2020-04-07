package entityTypes;
import java.util.Set;
import java.util.HashSet;
import GrandCentral.SearchBy;

public class Inproceedings extends Entity{

	/** possible parents = [dblp] **/
	Set<Dblp> parent;

	/** the children that define this entity **/
	public static final String[] uniqueDescriptor = {"pages","year","title","booktitle","url"};

	/** children = [ee, note, number, pages, month, year, author, title, booktitle, crossref, cdrom, url] **/
	public Ee ee;
	public Note note;
	public Number number;
	public Pages pages;
	public Month month;
	public Year year;
	public Set<Author> author;
	public Title title;
	public Booktitle booktitle;
	public Crossref crossref;
	public Cdrom cdrom;
	public Url url;

	/** Equality depends on DescriptorChildren **/
	public boolean equals(Inproceedings inproceedings) {
		return this.pages.equals(inproceedings.pages) && this.year.equals(inproceedings.year) && this.title.equals(inproceedings.title) && this.booktitle.equals(inproceedings.booktitle) && this.url.equals(inproceedings.url);
	}

	public static final SearchBy searchTier = SearchBy.DescriptorChildren;

}
