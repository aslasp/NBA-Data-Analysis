package newui.tables;

import javax.swing.JTable;
import javax.swing.table.TableModel;

public class MyBaseTable extends JTable{

	/**
	 * 所有显示基础信息的表格都继承此类（如球员A至Z检索界面，搜索结果界面）
	 */
	private static final long serialVersionUID = 1L;
	public MyBaseTable(TableModel model) {
		super(model);
	}
	
}
