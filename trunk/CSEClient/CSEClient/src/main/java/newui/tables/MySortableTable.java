package newui.tables;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class MySortableTable extends JTable {

	/**
	 * 计划让所有需要排序的表格都继承此类
	 */
	private static final long serialVersionUID = 1L;
	TableRowSorter<TableModel> sorterTeam;
	TableSorter<TableModel> sorterPlayer;

	/**
	 * 
	 * @param model
	 * @param mode
	 *            mode为0代表球员，mode为1代表球队
	 */
	public MySortableTable(TableModel model, int mode) {
		super(model);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		if (mode == 0) {
			sorterPlayer = new TableSorter<TableModel>(model);
			setRowSorter(sorterPlayer);
		} else {
			sorterTeam = new TableRowSorter<TableModel>(model);
			setRowSorter(sorterTeam);
		}

	}

}
