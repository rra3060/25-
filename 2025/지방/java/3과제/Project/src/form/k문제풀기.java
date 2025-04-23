package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class k문제풀기 extends aframe {

	JTextArea jte[] = new JTextArea[2];
	
	boolean ck;
	
	public k문제풀기() {
		fs("문제");
		emp(pc, 15, 15, 15, 30);
		try {
			rs = db.rs("SELECT u.name,c.questionexplan,c.type FROM catalog c,user u where c.uno = u.uno and c.cno = " + vq.cno);
			if (rs.next()) {
				np.add(jl = new JLabel(),w);
				sz(jl, 75, 50);
				png(jl, "icon/logo", 50, 50);
				jl.setVerticalAlignment(1);
				
				np.add(p0 = new JPanel(new BorderLayout()), c);
				emp(p0, 15, 0, 0, 0);
				p0.add(p1 = new JPanel(new BorderLayout()),c);
				emp(p1, 10, 0, 0, 0);
				p0.add(jl = new JLabel(rs.getString(1),0),n);
				jl.setOpaque(true);
				bk(jl, Color.black);
				fk(jl, Color.white);
				sz(jl, 250, 60);
				ft(jl, 0, 25);
					
				p1.add(jte[0] = new JTextArea());
				line(jte[0], Color.black);
				sz(jte[0], 250, 225);
				jte[0].setText(rs.getString(2));
				
				np.add(p0 = new JPanel(new BorderLayout()), e);
				p0.add(img = new JLabel());
				sz(img, 250, 250);
				line(img, Color.black);
				emp(p0, 15, 10, 0, 0);
				jpg(img, "question/" + rs.getInt(3), 250, 250);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		cp.add(jl = new JLabel("설명 : ",4),c);
		jl.setVerticalAlignment(1);
		ft(jl, 0, 13);
		emp(jl, 10, 0, 0, 0);
		
		cp.add(p0 = new JPanel(new BorderLayout()), e);
		p0.add(jte[1] = new JTextArea());
		emp(p0, 10, 0, 10, 0);
		line(jte[1], Color.black);
		sz(jte[1], 510, 125);
		
		emp(np, 0, 0, 0, 50);
		emp(cp, 0, 0, 0, 50);
		
		sp.setLayout(new FlowLayout(4));
		sp.add(jb = new JButton("등록하기"));
		bl(jb);
		ft(jb, 0, 13);
		sz(jb, 90, 35);
		jb.addActionListener(e -> {
			try {
				if (jte[1].getText().equals("")) {
					wmsg("1글자 이상 텍스트를 입력하세요.");
					return;
				}
				
				char ab = 'ㅏ';
				for (int i = 0; i < 21; i++) {
					ck = false;
					if (jte[1].getText().contains(ab + "")) {
						ck = true;
					}
					ab++;
				}

				boolean kor = Pattern.matches("^[ㄱ-ㅎ]*$", jte[1].getText());
				if (kor || ck == true) {
					wmsg("완성된 문장으로 등록해주세요.");
					return;
				}
				
				db.stmt.execute("update catalog set explan = '" + jte[1].getText() + "' where cno = " + vq.cno);
				imsg("답변이 정상적으로 제출되었습니다.");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		
		jte[0].setEditable(false);
		jte[0].setLineWrap(true);
		jte[1].setLineWrap(true);
		jte[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (jte[1].getText().length() > 500) {
					jte[1].setText(jte[1].getText().substring(0,500));
					System.out.println(jte[1].getText().length());
					return;
				}
			}
		});
		
		shp();
	}
}
