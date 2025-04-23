package form;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class j���� extends aframe {

	JLabel score,count,matter;
	
	int ans = 0,sec = 20;
	
	String sign[] = new String[3];
	
	Thread th = new Thread();

	String aaa= "";
	
	public j����() {
		fs("����");
		emp(pc, 20, 20, 20, 20);
		
		np.add(jl = new JLabel(),w);
		png(jl, "icon/logo", 45, 45);
		emp(jl, 10, 0, 0, 0);
		
		np.add(p0 = new JPanel(new FlowLayout()), c);
		p0.add(jl = new JLabel());
		png(jl, "icon/coin", 40, 40);
		p0.add(score = new JLabel("Score : " + vq.score));
		ft(score, 1, 25);
		
		np.add(count = new JLabel("20s"),e);
		ft(count, 0, 15);
		sz(count, 50, 30);
		emp(count, 0, 0, 20, 0);
		
		cp.add(p0 = new JPanel(new FlowLayout(0,0,0)), n);
		p0.add(jb = new JButton("���ΰ�ħ"));
		sz(jb, 250, 25);
		line(jb, Color.LIGHT_GRAY);
		bk(jb, Color.white);
		jb.addActionListener(e -> {
			reset();
		});
		
		cp.add(p0 = new JPanel(new FlowLayout(0,0,0)), c);
		p0.add(matter = new JLabel("",0));
		sz(matter, 250, 175);
		line(matter, Color.black);
		ft(matter, 1, 20);
		
		emp(cp, 20, 100, 0, 100);
		
		sp.setLayout(new FlowLayout(4,25,0));
		sp.add(jl = new JLabel("���� : "));
		ft(jl, 1, 13);
		sp.add(jt = new JTextField());
		sz(jt, 200, 30);
		jt.setBorder(new MatteBorder(0,0,1,0,Color.black));
		sp.add(jb = new JButton("Ȯ��"));
		bl(jb);
		sz(jb, 75, 30);
		jb.addActionListener(e -> {
			try {
				System.out.println(ans);
				if (jt.getText().equals("")) {
					wmsg("��ĭ�� �����մϴ�.");
					return;
				}
				if (jt.getText().equals(ans + "")) {
					vq.score = vq.score + 30;
					score.setText("Score : " + vq.score);
					imsg("�����Դϴ�.");
					db.stmt.execute("update user set score = " + vq.score + " where uno =" + vq.uno );
					reset();
				} else {
					if (vq.score > 30) {
						vq.score = vq.score - 30;
					} else {
						vq.score = 0;
					}
					
					score.setText("Score : " + vq.score);
					wmsg("�����Դϴ�.");
					jt.setText("");
					db.stmt.execute("update user set score = " + vq.score + " where uno =" + vq.uno );
					db.stmt.execute("insert into wrong values('0','" + vq.uno + "','" + aaa + "','" + ans + "')");
					rnd();
					return;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		emp(sp, 10, 0, 125, 0);
		
		rnd();
		th = new Thread(this);
		th.start();
		shp();
	}
	
	private void reset() {
		th.interrupt();
		sec = 20;
		rnd();
		th = new Thread(j����.this);
		th.start();
	}
	
	private void rnd() {
		Random rd = new Random();
		
		double num[] = new double[4];
		
		String sg[] = "+,-,*,/".split(",");
		for (int i = 0; i < sign.length; i++) {
			int a = rd.nextInt(4);
			sign[i] = sg[a];
			if (sign[i].equals("/")) {
				num[i + 1] = rd.nextInt(9) + 1;
			} else {
				num[i + 1] = rd.nextInt(9);
			}
		}
		num[0] = rd.nextInt(9);
		aaa = "";
		for (int i = 0; i < sign.length; i++) {
			aaa = aaa + String.format("%.0f", num[i]) + sign[i];
		}
		aaa = aaa + String.format("%.0f", num[3]);
		
		matter.setText(aaa);
		
		try {
			rs = db.rs("select round(" + aaa + ")");
			if (rs.next()) {
				ans = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			for (;;) {
				count.setText(sec + "s");
				th.sleep(1000);
				sec--;
				if (sec == 0) {
					count.setText(sec + "s");
					int a = JOptionPane.showConfirmDialog(null, "�־��� �ð��� �������ϴ�. ���ΰ�ħ �ұ��?","����",JOptionPane.YES_NO_OPTION);
					if (a == JOptionPane.YES_OPTION) {
						System.out.println("�ٽ�");
						th.interrupt();
						sec = 20;
						count.setText(sec + "s");
						rnd();
					} else {
						th.interrupt();
						dispose();
						new e�л�����();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		th.interrupt();
		new e�л�����();
	}
	
	
	
}
