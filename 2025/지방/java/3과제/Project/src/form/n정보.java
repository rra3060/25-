package form;

import java.awt.FlowLayout;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class n정보 extends aframe {

	String tn[] = "이름,학년,점수".split(",");
	
	public n정보() {
		fs("정보");
		
		cp.add(jsp = new JScrollPane(jta = new JTable(dtm = new DefaultTableModel(null,tn))));
		for (int i = 0; i < tn.length; i++) {
			jta.getColumnModel().getColumn(i).setCellRenderer(cell);
		}
		
		sz(jsp, 450, 200);
		
		
		sp.setLayout(new FlowLayout());
		sp.add(jb = new JButton("프린트하기"));
		bl(jb);
		ft(jb, 0, 20);
		sz(jb, 200, 35);
		
		emp(sp, 50, 0, 50, 0);
		
		jb.addActionListener(e -> {
			try {
				jta.print();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		});
		
		dup();
		shp();
	}
	
	private void dup() {
		try {
			rs = db.rs("select * from user where name ='" + vq.name + "'");
			
			if (rs.next()) {
				Object ob[] = {rs.getString(2),rs.getString(7),rs.getString(8)};
				dtm.addRow(ob);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 @Override
	  	public void windowClosing(WindowEvent e) {
		 new l랭킹();
	 }
}
