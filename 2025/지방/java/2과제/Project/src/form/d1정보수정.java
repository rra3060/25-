package form;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import aframe.aframe;
import jdbc.*;

public class d1정보수정 extends aframe {

	String ln[] = "이름,아이디,비밀번호,보유 돈,생년월일,이미지".split(",");
	JTextField jt[] = new JTextField[4];
	JButton jb[] = new JButton[3];
	JTextField date[] = new JTextField[3];

	public d1정보수정() {
		try {
			rs = db.rs("select u_img from user where u_no = " + vq.uno);
			if (rs.next()) {
				dcard.p[0].add(img = new JLabel(blob(rs.getBinaryStream(1), 300, 350)), w);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		emp(img, 0, 20, 0, 0);

		dcard.p[0].add(p0 = new JPanel(new GridLayout(0, 1, 0, 25)));
		for (int i = 0; i < ln.length; i++) {
			p0.add(p1 = new JPanel(new FlowLayout(0, 10, 0)));
			p1.add(jl = new JLabel(ln[i]));
			sz(jl, 80, 30);
			ft(jl, 1, 15);
			if (i == 3) {
				p1.add(jt[i] = new JTextField());
				sz(jt[i], 140, 30);
				p1.add(jb[0] = new JButton("충전하기"));
				sz(jb[0], 100, 30);
				bl(jb[0]);
			} else if (i == 4) {
				p1.add(date[0] = new JTextField());
				sz(date[0], 80, 30);
				p1.add(jl = new JLabel("-"));
				ft(jl, 1, 15);
				p1.add(date[1] = new JTextField());
				sz(date[1], 80, 30);
				p1.add(jl = new JLabel("-"));
				ft(jl, 1, 15);
				p1.add(date[2] = new JTextField());
				sz(date[2], 80, 30);
			} else if (i == 5) {
				p1.add(jb[1] = new JButton("이미지 바꾸기"));
				bl(jb[1]);
			} else {
				p1.add(jt[i] = new JTextField());
				emp(p1, 0, 0, 0, 20);
				sz(jt[i], 230, 30);
			}
		}
		jt[1].setEnabled(false);
		emp(p0, 20, 10, 0, 0);

		dcard.p[0].add(p0 = new JPanel(new FlowLayout(2, 0, 0)), s);
		p0.add(jb[2] = new JButton("저장하기"));
		jb[2].addActionListener(e -> {
			dispose();
			new a메인();
		});
		bl(jb[2]);
		sz(jb[2], 120, 30);

		try {
			rs = db.rs("select u_name, u_id, u_pw, u_price, u_birth from user where u_no = " + vq.uno);
			if (rs.next()) {
				jt[0].setText(rs.getString(1));
				jt[1].setText(rs.getString(2));
				jt[2].setText(rs.getString(3));
				jt[3].setText(rs.getString(4));
				String birth[] = rs.getString(5).split("-");
				date[0].setText(birth[0]);
				date[1].setText(birth[1]);
				date[2].setText(birth[2]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
