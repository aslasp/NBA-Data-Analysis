package newui.tables;

import java.awt.Dimension;

import javax.swing.JTable;

/**
 * 用于显示RowHeader的JTable，只需要将其加入JScrollPane的RowHeaderView即可为JTable生成行标题
 */
public class RowHeaderTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// refTable:需要添加rowHeader的JTable

	/**
	 * 为JTable添加RowHeader，
	 * 
	 * @param refTable
	 *            需要添加rowHeader的JTable
	 * @param columnWideth
	 *            rowHeader的宽度
	 */
	public RowHeaderTable(JTable refTable, int columnWidth) {
		super(new RowHeaderTableModel(refTable.getRowCount()));
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 不可以调整列宽
		this.getColumnModel().getColumn(0).setPreferredWidth(columnWidth);
		this.setRowHeight(40);
		this.setDefaultRenderer(Object.class, new RowHeaderRenderer(refTable,
				this));// 设置渲染器
		this.setPreferredScrollableViewportSize(new Dimension(columnWidth, 0));
	}
}
