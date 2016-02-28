package newui.playerui.details;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import blService.MatchBLService;
import newui.Service;
import newui.Style;
import newui.mainui.MainFrame;
import newui.matchui.MatchDetailPanel;
import newui.tables.MyTableCellRenderer;
import newui.tables.PlayerHistoryTableModel;
import vo.MatchVO;
import vo.PlayerVO;

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
	MyBox seasonBox,seasonTypeBox;
	PlayerVO vo;

	public PlayerDetailHistoryPanel(final PlayerVO vo) {
		this.vo = vo;
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		setLayout(gbl);
		setBackground(Color.white);
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
		MatchBLService match=Service.match;
		ArrayList<String> seasons=new ArrayList<String>();
		seasons=match.getAllSeasons();
		int size=seasons.size();
		String[] seasonText = (String[])seasons.toArray(new String[size]);
		seasonBox = new MyBox(seasonText);
		funcPnl.add(seasonBox);
		String[] seasonTypeText = {  "常规赛","季后赛", "季前赛"  };// 这是什么
		seasonTypeBox = new MyBox(seasonTypeText);
		funcPnl.add(seasonTypeBox);
		// ----------------------
		phtm = new PlayerHistoryTableModel();
		table = new JTable(phtm);

		// table 渲染器，设置文字内容居中显示，设置背景色等
		table.setSelectionBackground(new Color(225, 255, 255));// 设置选择行的颜色——淡蓝色
		table.setFont(new Font("微软雅黑", 0, 12));
		table.getTableHeader().setFont(new Font("微软雅黑", 0, 14));
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(Style.FOCUS_BLUE);		DefaultTableCellRenderer tcr = new MyTableCellRenderer();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
		}
		gbc.insets=new Insets(0, 2, 1, 2);
		jsp = new JScrollPane(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		gbc.gridy = 1;
		gbc.gridheight = 10;
		gbc.weighty = 10;
		gbl.setConstraints(jsp, gbc);
		add(jsp);
		phtm.Refresh(vo.getName());
		table.revalidate();
		table.repaint();
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount()==2){
					int row=table.getSelectedRow();
					MatchVO vo=phtm.match.get(row);
					MainFrame.getInstance().setContentPanel(new MatchDetailPanel(vo));
				}
			}
		});
		seasonBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String season = seasonBox.getSelectedItem().toString();
				String seasonType = seasonTypeBox.getSelectedItem().toString();
				ArrayList<MatchVO> matches=new ArrayList<MatchVO>();
				matches=Service.player.getMatches(season, seasonType,vo.getName(),30);
				phtm.Refresh(matches,vo.getName());
				table.revalidate();
				table.repaint();
                table.validate();
			}

		});
		seasonTypeBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String season = seasonBox.getSelectedItem().toString();
				String seasonType = seasonTypeBox.getSelectedItem().toString();
				ArrayList<MatchVO> matches=new ArrayList<MatchVO>();
				matches=Service.player.getMatches(season, seasonType,vo.getName(),30);
				phtm.Refresh(matches,vo.getName());
				table.revalidate();
				table.repaint();

			}
		});

	}

	class MyBox extends JComboBox<String> {

		private static final long serialVersionUID = 1L;

		public MyBox(String[] arr) {
			super(arr);
			setFont(font);
			this.setBackground(Color.white);
			this.setForeground(Style.DEEP_BLUE);
		}
	}

	class MyLabel extends JLabel {

		private static final long serialVersionUID = 1L;

		public MyLabel(String text) {
			super(text);
			setFont(font);
			setForeground(Style.DEEP_BLUE);
		}
	}
}
