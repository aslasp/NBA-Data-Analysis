package newui.playerui.details;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import newui.tables.PlayerHistoryTableModel;

public class PlayerDetailHistoryPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	JScrollPane jsp;
	JTable table;
	PlayerHistoryTableModel phtm;
	// --------------
	Font font = new Font("微软雅黑", Font.PLAIN, 15);
	JPanel funcPnl;
	MyBox seasonBox;

	public PlayerDetailHistoryPanel(String pname) {
		name = pname;
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		// ------------------
		funcPnl = new JPanel();
		funcPnl.setOpaque(false);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 10;
		gbc.gridheight = 1;
		gbc.weightx = 10;
		gbc.weighty = 0.1;
		gbl.setConstraints(funcPnl, gbc);
		add(funcPnl);
		// ------------------------
		MyLabel seasonLbl = new MyLabel("赛季：");
		funcPnl.add(seasonLbl);
		//
		String[] seasonText = { "我要监听" };
		seasonBox = new MyBox(seasonText);
		funcPnl.add(seasonBox);
		// ----------------------
		phtm=new PlayerHistoryTableModel();
		table=new JTable(phtm);
		jsp = new JScrollPane(table);
		gbc.gridy = 1;
		gbc.gridheight = 10;
		gbc.weighty = 10;
		gbl.setConstraints(jsp, gbc);
		add(jsp);
		phtm.Refresh(pname);
		table.revalidate();

	}

	class MyBox extends JComboBox<String> {

		private static final long serialVersionUID = 1L;

		public MyBox(String[] arr) {
			super(arr);
			setFont(font);
		}
	}

	class MyLabel extends JLabel {

		private static final long serialVersionUID = 1L;

		public MyLabel(String text) {
			super(text);
			setFont(font);
		}
	}
}
