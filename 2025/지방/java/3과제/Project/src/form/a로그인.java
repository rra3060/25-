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

public class a�α��� extends aframe {

	JTextField jt[] = new JTextField[2];
	String ln[] = "ID,PW".split(",");
	
	JLabel msg;
	
	int ck = 0;
	
	public a�α���() {
		fs("�α���");
		
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
		
		ep.add(jb = new JButton("�α���"));
		emp(ep, 5, 0, 5, 0);
		bl(jb);
		jb.addActionListener(e -> {
			try {
				for (int i = 0; i < jt.length; i++) {
					if (jt[i].getText().equals("")) {
						msg.setText("��ĭ�� �ֽ��ϴ�.");
						msg.setVisible(true);
						return;
					}
				}
				
				if (jt[0].getText().equals("admin") && jt[1].getText().equals("1234")) {
					imsg("�����ڴ� ȯ���մϴ�.");
					new l��ŷ();
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
						msg.setText("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
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
						imsg(vq.uname + " �л��� ȯ���մϴ�.");
						new e�л�����();
					} else {
						imsg(vq.uname + " ������ ȯ���մϴ�.");
						new b�����Ը���();
					}
					dispose();
					
				} else {
					msg.setText("�������� �ʴ� ȸ���Դϴ�.");
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
		sp.add(msg = new JLabel("��ĭ�� �ֽ��ϴ�."));
		ft(msg, 0, 13);
		fk(msg, Color.red);
		msg.setBorder(new MatteBorder(0,0,1,0,Color.red));
		
		msg.setVisible(false);
		
		shp();
	}
}
