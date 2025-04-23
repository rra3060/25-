package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class c질문목록 extends aframe {

	JComboBox jc[] = new JComboBox[2];
	String ln[] = "정렬,학년".split(",");
	
	String cn[] = "전체,최근 등록순,문제번호순,회원번호순".split(",");
	
	public c질문목록() {
		fs("질문 목록");
		
		np.add(jl = new JLabel(),w);
		png(jl, "icon/logo", 45, 45);
	
		np.add(jl = new JLabel("질문 목록",0),c);
		ft(jl, 1, 20);
		
		np.setBorder(new MatteBorder(0,0,1,0,Color.black));
		
		cp.add(p0 = new JPanel(new FlowLayout(0)), n);
		for (int i = 0; i < jc.length; i++) {
			p0.add(jl = new JLabel(ln[i] + " : "));
			sz(jl, 50, 30);
			ft(jl, 1, 13);
			
			p0.add(jc[i] = new JComboBox());
			sz(jc[i], 150, 30);
		}
		for (int i = 0; i < cn.length; i++) {
			jc[0].addItem(cn[i]);
		}
		
		jc[1].addItem("전체");
		try {
			rs = db.rs("SELECT u.grade FROM catalog c,user u where c.uno = u.uno and c.tno = " + vq.uno + " group by u.grade order by u.grade asc");
			
			while (rs.next()) {
				jc[1].addItem(rs.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		cp.add(jsp = new JScrollPane(jp = new JPanel(new GridLayout(0,2,10,10))), c);
		sz(jsp, 600, 400);
		emp(cp, 20, 45, 15, 15);
		
		for (int i = 0; i < jc.length; i++) {
			jc[i].addActionListener(e -> {
				dup();
			});
		}
		
		dup();
		shp();
	}
	
	private void dup() {
		jp.removeAll();
		try {
			
			int sel = jc[0].getSelectedIndex();
			String sql = sel == 1 ? " c.date desc " : sel == 2 ? " c.type asc " : sel == 3 ? " c.uno asc " : " c.cno asc ";
			String sql2 = jc[1].getSelectedIndex() == 0 ? "" : " and u.grade ='" + jc[1].getSelectedItem() + "' ";
			
			rs = db.rs("SELECT c.cno,c.uno,u.name,u.grade,c.title,c.type,c.date,c.explan,c.questionexplan FROM catalog c,user u where c.uno = u.uno and c.tno = " + vq.uno + sql2 + " order by " + sql);
			
			while (rs.next()) {
				jp.add(p0 = new JPanel(new BorderLayout()));
				line(p0, Color.black);
				
				p0.add(jl = new JLabel(rs.getString(3) + "(" + rs.getString(4) + ")",0),n);
				ft(jl, 1, 20);
				emp(jl, 25, 0, 25, 0);
				
				p0.add(p1 = new JPanel(new BorderLayout()), c);
				p1.add(jl = new JLabel(rs.getString(5),0),n);
				ft(jl, 0, 12);
				emp(jl, 0, 0, 20, 0);
				
				p1.add(p2 = new JPanel(new FlowLayout()), c);
				p2.add(img = new JLabel());
				jpg(img, "question/" + rs.getInt(6), 200, 100);
				line(img, Color.black);
				
				p0.add(p1 = new JPanel(new FlowLayout(4)), s);
				emp(p1, 0, 0, 35, 35);
				p1.add(jb = new JButton("문제 풀어주기"));
				bl(jb);
				ft(jb, 0, 12);
				sz(jb, 110, 35);
				
				String exp = rs.getString(8);
				int cno = rs.getInt(1);
				jb.addActionListener(e -> {
					if (exp != null) {
						wmsg("이미 풀어준 문제입니다.");
						return;
					}
					vq.cno = cno;
					dispose();
					new k문제풀기();
				});
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		revalidate();
		repaint();
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		new b선생님메인();
	}
	
}
