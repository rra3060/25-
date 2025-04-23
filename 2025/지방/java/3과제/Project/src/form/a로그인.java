package form;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class a로그인 extends aframe {

	JTextField jt[] = new JTextField[2];
	String ln[] = "ID,PW".split(",");
	
	JLabel msg;
	
	int ck = 0;
	
	public a로그인() {
		fs("로그인");
		
		emp(pc, 20, 20, 20, 20);
		
		np.add(jl = new JLabel("Question",0));
		ft(jl, 1, 20);
		
		
		cp.add(p0 = new JPanel(new GridLayout(0,1,10,10)));
		for (int i = 0; i < jt.length; i++) {
			p0.add(p1 = new JPanel(new FlowLayout()));
			
			p1.add(jl = new JLabel(ln[i]));
			sz(jl, 40, 30);
			
			
			p1.add(jt[i] = new JTextField());
			sz(jt[i], 200, 30);
		}
		
		ep.add(jb = new JButton("로그인"));
		emp(ep, 5, 0, 5, 0);
		bl(jb);
		jb.addActionListener(e -> {
			try {
				for (int i = 0; i < jt.length; i++) {
					if (jt[i].getText().equals("")) {
						msg.setText("빈칸이 있습니다.");
						msg.setVisible(true);
						return;
					}
				}
				
				if (jt[0].getText().equals("admin") && jt[1].getText().equals("1234")) {
					imsg("관리자님 환영합니다.");
					new l랭킹();
					dispose();
					return;
				}
				
				if (jt[0].getText().contains("tjstodsla")) {
					rs = db.rs("select * from teacher where id ='" + jt[0].getText() + "'");
					ck = 1;
				} else {
					rs = db.rs("select * from user where id ='" + jt[0].getText() + "'");
					ck = 0;
				}
				
				if (rs.next()) {
					String pw = rs.getString(4);
					if (!pw.equals(jt[1].getText())) {
						msg.setText("비밀번호가 일치하지 않습니다.");
						msg.setVisible(true);
						return;
					}
					
					vq.uno = rs.getInt(1);
					vq.uname = rs.getString(2);
					vq.phone = rs.getString(5);
					vq.birth = rs.getString(6);
					msg.setVisible(false);
					vq.role = ck;
					System.out.println(ck);
					if (ck == 0) {
						vq.score = rs.getInt(8);
						imsg(vq.uname + " 학생님 환영합니다.");
						new e학생메인();
					} else {
						imsg(vq.uname + " 선생님 환영합니다.");
						new b선생님메인();
					}
					dispose();
					
				} else {
					msg.setText("존재하지 않는 회원입니다.");
					msg.setVisible(true);
					return;
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		sp.setLayout(new FlowLayout(0));
		sz(sp, 200, 40);
		emp(sp, 10, 45, 0, 0);
		sp.add(msg = new JLabel("빈칸이 있습니다."));
		ft(msg, 0, 13);
		fk(msg, Color.red);
		msg.setBorder(new MatteBorder(0,0,1,0,Color.red));
		
		msg.setVisible(false);
		
		shp();
	}
}
