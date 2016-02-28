package newui.tables;

import java.text.Collator;
import java.util.Comparator;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class TableSorter<M extends TableModel> extends
		TableRowSorter<TableModel> {
	private static final Comparator<TableModel> COMPARABLE_COMPARATOR = new ComparableComparator();

	public TableSorter(TableModel model) {
		super(model);
	}

	@Override
	public Comparator<?> getComparator(int column) {
		if (column == 6) {
			return new PlayerTableComparator();

		} else {
			Comparator comparator = super.getComparator(column);
			if (comparator != null) {
				return comparator;
			}
			Class columnClass = ((M) getModel()).getColumnClass(column);
			if (columnClass == String.class) {
				return Collator.getInstance();
			}
			if (Comparable.class.isAssignableFrom(columnClass)) {
				return COMPARABLE_COMPARATOR;
			}
			return Collator.getInstance();
		}
	}

	private static class ComparableComparator implements Comparator {
		@SuppressWarnings("unchecked")
		public int compare(Object o1, Object o2) {
			return ((Comparable) o1).compareTo(o2);
		}
	}

}
