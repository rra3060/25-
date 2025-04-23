package form;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class h내질문 extends aframe {

	 String tn[] = "번호,선생님,타이틀,질문,답변,문제이미지".split(",");
	 String cn[] = "전체,답변완료,답변 미완료".split(",");
	 
	 JPopupMenu pop = new JPopupMenu();
	 JMenuItem jm;
	 
	 String exp[];
	 int cno[];
	 
	public h내질문() {
		fs("내 질문");
		
		emp(pc, 10, 20, 20, 20);
		
		np.add(jl = new JLabel(),w);
		png(jl, "icon/logo", 40, 40);
		
		np.add(jl = new JLabel("내 질문",0),c);
		ft(jl, 1, 20);
		
		cp.add(p0 = new JPanel(new FlowLayout(4)), n);
		p0.add(jc = new JComboBox<>(cn));
		sz(jc, 150, 30);
		emp(p0, 50, 0, 0, 10);
		jc.addActionListener(e -> {
			dup();
			revalidate();
			repaint();
		});
		
		cp.add(jsp = new JScrollPane(jta = new JTable(dtm = new DefaultTableModel(null,tn) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 5 ? Icon.class : Object.class;
			}
		})));
		sz(jsp, 650, 350);
		jta.setRowHeight(150);
		DefaultTableCellRenderer ce = new DefaultTableCellRenderer();
		ce.setVerticalAlignment(1);
		
		int w[] = {50,85,100,100,100,200};
		for (int i = 0; i < w.length; i++) {
			
			jta.getColumnModel().getColumn(i).setPreferredWidth(w[i]);
			if (i == 2 || i == 3 || i == 4	) {
				jta.getColumnModel().getColumn(i).setCellRenderer(ce);
			} else if (i != 5) {
				jta.getColumnModel().getColumn(i).setCellRenderer(cell);
			} 
		}
		jta.add(pop);
		pop.add(jm = new JMenuItem("변경"));
		jm.addActionListener(e2 -> {
			if (exp[jta.getSelectedRow()] != null) {
				wmsg("답변이 있는 경우는 선생님을 변경할 수 없습니다.");
				return;
			} else {
				System.out.println("O");
				vq.cno = cno[jta.getSelectedRow()];
				dispose();
				new g선생목록();
			}
		});
		
		jta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (jta.getSelectedRow() == -1) {
					System.out.println("X");
					return;
				}
				if (e.getButton() == 3) {
					pop.show(jta,e.getX(),e.getY());
				}
			}
		});
		
		
		dup();
		shp();
	}
	 @Override
	  	public void windowClosing(WindowEvent e) {
	  		new e학생메인();
	  	}
	private String str(String a) {
		return "<html>" + a + "<td style='WORD-BREAK:break-all;'>";
	}
	
	private void dup() {
		dtm.setRowCount(0);
		try {
			
			String sql = jc.getSelectedIndex() == 1 ? " and c.explan != ''" : "";
			
			rs = db.rs("SELECT c.cno,t.name,c.title,c.questionexplan,c.explan,c.type FROM catalog c,teacher t where c.tno = t.tno and c.uno = " + vq.uno + sql + " order by c.type asc");
			
			rs.last();
			cno = new int[rs.getRow()];
			exp = new String[rs.getRow()];
			rs.beforeFirst();
			
			int i = 0;
			while (rs.next()) {
				cno[i] = rs.getInt(1);
				exp[i] = rs.getString(5);
				if (jc.getSelectedIndex() == 2) {
					if (rs.getString(5) == null) {
						String b = rs.getString(5) == null ? "" : rs.getString(5);
						ImageIcon icon = new ImageIcon(new ImageIcon("datafiles/question/" + rs.getInt(6) + ".jpg").getImage().getScaledInstance(200, 150, 4));
						Object ob[] = {(i + 1),rs.getString(2),str(rs.getString(3)),str(rs.getString(4)),str(b),icon};
						dtm.addRow(ob);
						i++;
					}
				} else {
					String b = rs.getString(5) == null ? "" : rs.getString(5);
					ImageIcon icon = new ImageIcon(new ImageIcon("datafiles/question/" + rs.getInt(6) + ".jpg").getImage().getScaledInstance(200, 150, 4));
					Object ob[] = {(i + 1),rs.getString(2),str(rs.getString(3)),str(rs.getString(4)),str(b),icon};
					dtm.addRow(ob);
					i++;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		revalidate();
		repaint();
	}
}
