package newui.tables;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import newui.Style;

public class MyTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> rowList;
	private ArrayList<ImageIcon> imageIcons;
	private int columnToHighlight = -1;

	// ==直接用Arraylist的话，会不会后面容易乱，毕竟是直接根据下标来取图片的，考虑一下用什么来存图片的列表并且跟名字绑定

	/**
	 * 无参构造函数只是区分奇偶行的颜色
	 * 
	 */
	public MyTableCellRenderer() {
		super();
		this.rowList = new ArrayList<Integer>();
		this.imageIcons = new ArrayList<ImageIcon>();
	}

	/**
	 * 区分奇偶行的颜色，并且设置每行的图片
	 * 
	 * @paramimageIcons 每行的图片
	 */
	public MyTableCellRenderer(ArrayList<ImageIcon> imageIcons) {
		super();
		this.rowList = new ArrayList<Integer>();
		this.imageIcons = new ArrayList<ImageIcon>();
		this.imageIcons = imageIcons;
	}

	/**
	 * @param rowList
	 *            需要设为特别颜色的特定行和每行的图片
	 * @param imageIcons
	 *            每行的图片
	 */
	public MyTableCellRenderer(ArrayList<Integer> rowList,
			ArrayList<ImageIcon> imageIcons) {
		super();
		this.rowList = rowList;
		this.imageIcons = imageIcons;
	}

	public MyTableCellRenderer(int column) {
		super();
		this.rowList = new ArrayList<Integer>();
		this.imageIcons = new ArrayList<ImageIcon>();
		this.columnToHighlight = column;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		table.setFont(new Font("微软雅黑", 0, 14));
		setHorizontalAlignment(SwingConstants.CENTER);
		if (table.getModel().toString().contains("PlayerBaseInfoTableModel")) {
			// 设置列宽自己设置
			String columnName1 = table.getColumnName(0);
			table.getColumn(columnName1).setPreferredWidth(50);
			table.getColumn(columnName1).setMinWidth(50);
			table.getColumn(columnName1).setMaxWidth(50);
			String columnName2 = table.getColumnName(1);
			table.getColumn(columnName2).setPreferredWidth(130);

			// // 设置每行第一列显示图片
			// if (column == 0) {
			// if (imageIcons.size() != 0) {// 防止不传图片直接显示表格的那种
			// ImageIcon icon = imageIcons.get(row);
			// ImageIcon icon2 = new ImageIcon(icon.getImage()
			// .getScaledInstance(
			// table.getColumn(table.getColumnName(0))
			// .getWidth(),
			// table.getRowHeight(row),
			// Image.SCALE_DEFAULT));
			// JLabel label = new JLabel(icon2);
			// label.setOpaque(false);
			// return label;
			// }
			// }

		}

		if (table.getModel().toString().contains("PlayerHistoryTableModel")) {
			String columnName1 = table.getColumnName(0);
			table.getColumn(columnName1).setPreferredWidth(200);
		}

		if (table.getModel().toString().contains("TeamHistoryTableModel")
				|| table.getModel().toString().contains("MatchDetailModel")) {
			String columnName1 = table.getColumnName(0);
			table.getColumn(columnName1).setPreferredWidth(200);
		}

		// 设置行高
		table.setRowHeight(row, 40);
		// 去除单元格边框线
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);

		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(Style.FOCUS_BLUE);

		if (row % 2 == 1)
			setBackground(Color.white); // 设置奇数行底色
		else if (row % 2 == 0)
			// setBackground(new Color(246, 246, 246)); // 设置偶数行底色
			setBackground(new Color(230, 230, 250));

		for (int i = 0; i < rowList.size(); i++) {
			if (row == rowList.get(i)) {
				setBackground(new Color(255, 255, 0));// 设置某些特定的行的底色
			}
		}
		if (column == columnToHighlight) {
			setBackground(new Color(158, 184, 199));
		}

		if (table.getModel().toString().contains("HotTodayModel")
				|| table.getModel().toString().contains("HotSeasonModel")
				|| table.getModel().toString().contains("HotTeamModel")
				|| table.getModel().toString().contains("ProgressModel")) {
			// 设置列宽自己设置
			String columnName1 = table.getColumnName(0);
			table.getColumn(columnName1).setPreferredWidth(50);
			table.getColumn(columnName1).setMinWidth(50);
			table.getColumn(columnName1).setMaxWidth(50);
			String columnName2 = table.getColumnName(1);
			table.getColumn(columnName2).setPreferredWidth(100);
			table.getColumn(columnName2).setMinWidth(100);
			table.getColumn(columnName2).setMaxWidth(100);

			table.setRowHeight(row, 80);
		}

		if (table.getModel().toString().contains("TeamBaseInfoTableModel")) {
			table.setRowHeight(row, 80);
			String columnName1 = table.getColumnName(0);
			table.getColumn(columnName1).setPreferredWidth(80);
			table.getColumn(columnName1).setMinWidth(80);
			table.getColumn(columnName1).setMaxWidth(80);
		}

		return super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
	}

	public Component setPlayerIcon(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// 设置列宽自己设置
		String columnName1 = table.getColumnName(0);
		table.getColumn(columnName1).setPreferredWidth(50);
		table.getColumn(columnName1).setMinWidth(50);
		table.getColumn(columnName1).setMaxWidth(50);
		// String columnName2 = table.getColumnName(1);
		// table.getColumn(columnName2).setPreferredWidth(130);

		// 设置每行第一列显示图片
		if (column == 0) {
			if (imageIcons.size() != 0) {// 防止不传图片直接显示表格的那种
				ImageIcon icon = imageIcons.get(row);
				ImageIcon icon2 = new ImageIcon(icon.getImage()
						.getScaledInstance(
								table.getColumn(table.getColumnName(0))
										.getWidth(), table.getRowHeight(row),
								Image.SCALE_DEFAULT));
				JLabel label = new JLabel(icon2);
				label.setOpaque(false);
				return label;
			}
		}
		return super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
	}

	/**
	 * 自动调整列宽
	 * 
	 * @param table
	 *            表格名称
	 */
	public static void adjustTableColumnWidths(JTable table) {
		JTableHeader header = table.getTableHeader(); // 表头
		int rowCount = table.getRowCount(); // 表格的行数
		TableColumnModel cm = table.getColumnModel(); // 表格的列模型
		for (int i = 0; i < cm.getColumnCount(); i++) { // 循环处理每一列
			TableColumn column = cm.getColumn(i); // 第i个列对象
			int width = (int) header
					.getDefaultRenderer()
					.getTableCellRendererComponent(table,
							column.getIdentifier(), false, false, -1, i)
					.getPreferredSize().getWidth(); // 用表头的绘制器计算第i列表头的宽度
			for (int row = 0; row < rowCount; row++) { // 循环处理第i列的每一行，用单元格绘制器计算第i列第row行的单元格宽度
				int preferedWidth = (int) table
						.getCellRenderer(row, i)
						.getTableCellRendererComponent(table,
								table.getValueAt(row, i), false, false, row, i)
						.getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth); // 取最大的宽度
			}
			column.setPreferredWidth(width + table.getIntercellSpacing().width); // 设置第i列的首选宽度
		}

		table.doLayout(); // 按照刚才设置的宽度重新布局各个列
	}

	public void setHighlightColumn(int column) {
		this.columnToHighlight = column;
	}
}
