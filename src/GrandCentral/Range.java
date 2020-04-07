package GrandCentral;


	import java.util.HashSet;
	import java.util.List;
	import java.util.Set;

	/**add an integer and it can expand the range**/
	public class Range {
		int min;
		int max;
		boolean hasInput = false;
		public Range(int min, int max) {
			this.min = min;
			this.max = max;
		}
		public Range(Set<Integer>si) {
			add(si);
		}
		public Range(List<Integer>si) {
			add(si);
		}
		public void add(int a) {
			if (!hasInput) {
				this.min = a;
				this.max = a;
				hasInput = true;
			}
			else {
				if (a < min) {
					this.min = a;
				}
				else if (a > max) {
					this.max = a;
				}
			}
		}
		public void add(List<Integer>li) {
			add(new HashSet<Integer>(li));
		}
		public void add(Set<Integer>si) {
			int index = 0;
			for (int s : si) {
				if (index == 0 && !hasInput) {
					this.min = s;
					this.max = s;
					hasInput = true;
				}
				else {
					if (s < min) {
						this.min = s;
					}
					else if (s > max) {
						this.max = s;
					}
				}
				index += 1;
			}
			
		}
		public String toString() {
			if (hasInput) {
				if (this.min == this.max) {
					return "("+Integer.toString(this.min)+")";
				}
				else {
					return "("+this.min+" to "+this.max+")";
				}
			}
			else {
				return "(n/r)";
			} 
		}
		public static String toRangeString(Set<Integer>si) {
			return (new Range(si)).toString();
		}
		public static String toRangeString(List<Integer>li) {
			return (new Range(new HashSet<Integer>(li))).toString();
		}
	}


