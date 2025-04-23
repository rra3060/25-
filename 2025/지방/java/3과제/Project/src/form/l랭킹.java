package form;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class l·©Å· extends aframe {

	String name[] = new String[5];
	int num[] = new int[5],max = 0;
	
	Random rd = new Random();
	
	public l·©Å·() {
		fs("Score·©Å·");
		
		
		np.add(jl = new JLabel("Score ·©Å·",0));
		ft(jl, 1, 25);
		emp(jl, 25, 0, 0, 0);
		
		sz(cp, 500, 350);
		
		dup();
		shp();
	}
	
	private int rc() {
		int aaa = rd.nextInt(135) + 100;
		return aaa;
	}
	
	int row = -1;
	
	private void dup() {
		cp.removeAll();
		cp.add(jp = new JPanel() {
			
			{
				addMouseMotionListener(new MouseMotionAdapter() {
					public void mouseMoved(MouseEvent e) {
						int mx = e.getX();
						int my = e.getY();

						int h = 10;
						int ps = 25;
						int start = 30;
						System.out.println(mx + "/" + my);
						
						row = -1;
						System.out.println(row);
						int aa = 0;
						for (int j = 0; j < name.length; j++) {
							
							int aaa = start + ps * j;
							
							System.out.println(aaa);
							
							if (mx >= 425 && mx <= 485 && aaa >= my && aaa - 10 <= my) {
								row = j;
								aa = j;
								System.out.println(row + "/" + aa);
								
								break;
							}
						}
					};
				});

				addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (row == -1) {
							return;
						}
						vq.name = name[row];
						System.out.println(vq.name);
						dispose();
						new nÁ¤º¸();
					};
				});
			}
			
			@Override
			public void paint(Graphics g) {
				try {
					rs = db.rs("SELECT score,name FROM question.user order by score desc limit 5");
					
					int i = 0;
					double a = 0;
					while (rs.next()) {
						num[i] = rs.getInt(1) / 10;
						name[i] = rs.getString(2);
						a = num[i] + a;
						i++;
					}

					int st = 0;
					for (int j = 0; j < num.length; j++) {
						g.setColor(new Color(rc(),rc(),rc(),rc()));
						
						int arc = (int) Math.round((double) num[j] / a * 361);
						System.out.println(arc);
						
						g.fillArc(100, 35, 225, 225, st, arc);
						
						int y = 15 + (25 * j);
						
						g.fillRect(375, y, 45, 15);
						
						g.setColor(Color.black);
						g.drawString(name[j] + " " + (j + 1) + "À§", 425, y + 15 );
						
						st += arc;
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void windowClosing(WindowEvent e) {
		new a·Î±×ÀÎ();
	}
	
	
}
