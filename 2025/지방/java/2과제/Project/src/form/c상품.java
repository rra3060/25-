package form;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import aframe.aframe;
import jdbc.*;

public class c상품 extends aframe {
	JLabel jll[] = new JLabel[5];
	String cn[] = "제품명 : ,평점 : ,판매자 : ,카테고리 : ,상품 금액 : ".split(",");
	JPanel pp[] = new JPanel[4];
	String ssz[] = "S,M,L,XL".split(",");
	JLabel jlc[] = new JLabel[4];
	String cn1[] = "구매,장바구니".split(",");
	JButton jb1[] = new JButton[2];
	JLabel star = new JLabel();
	JLabel up[] = new JLabel[4];
	JLabel qty[] = new JLabel[4];
	JLabel down[] = new JLabel[4];
	boolean choose[] = new boolean[4];
	boolean chdown[] = new boolean[4];
	boolean chup[] = new boolean[4];
	int cnt[] = new int[4];
	int tot = 0;
	JLabel subtot = new JLabel();

	public c상품() {
		fs("상품");
		cp.setLayout(new FlowLayout(0));
		for (int i = 0; i < cn.length; i++) {
			cp.add(jll[i] = new JLabel(cn[i]));
			sz(jll[i], 250, 30);
			if (i == 1) {
				sz(jll[i], 40, 30);
				cp.add(Starsup());
				sz(star, 180, 30);
				ft(star, 1, 20);
				fk(star, Color.orange);
			}
		}

		try {
			rs = db.rs("SELECT us.u_name,pr.p_name FROM clothingstore.productlist pr,clothingstore.user us where p_no = '" + vq.pno + "' and us.u_no = pr.u_no");
			if (rs.next()) {
				jll[2].setText("판매자 : " + rs.getString(1));
				jll[0].setText("제품명 : " + name(rs.getString(2),20));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			rs = db.rs("SELECT su.sb_name FROM clothingstore.productlist pr,clothingstore.subcategory su where p_no = '" + vq.pno + "' and su.sb_no = pr.sb_no");
			if (rs.next()) {
				jll[3].setText("카테고리 : " + rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			rs = db.rs("SELECT p_price,p_s,p_m,p_l,p_xl FROM clothingstore.productlist where p_no = '" + vq.pno + "'");
			if (rs.next()) {
				jll[4].setText("상품 금액 : " + def.format(rs.getInt(1)));
				tot = rs.getInt(1);
				cnt[0] = rs.getInt(2);
				cnt[1] = rs.getInt(3);
				cnt[2] = rs.getInt(4);
				cnt[3] = rs.getInt(5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		cp.add(p0 = new JPanel(new GridLayout(0,4,5,5)));

		for (int i = 0; i < pp.length; i++) {
			p0.add(pp[i] = new JPanel());
			sz(pp[i], 60, 50);
			pp[i].add(jlc[i] = new JLabel(ssz[i]));
			line(pp[i], Color.black);
			ft(jlc[i], 1, 15);

			int F = i;
			pp[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					choose[F] = !choose[F];
					updateSelection(F);
					pricrUp();
				}

				private void updateSelection(int index) {
					bk(pp[index], choose[index] ? Color.blue : Color.white);
					fk(jlc[index], choose[index] ? Color.white : Color.black);
					up[index].setEnabled(choose[index]);
					qty[index].setEnabled(choose[index]);
					down[index].setEnabled(choose[index]);
					qty[index].setText("0");
				}
			});
		}

		cp.add(p0 = new JPanel(new GridLayout(0,4,5,5)));

		for (int i = 0; i < pp.length; i++) {
			int Fs = i;
			p0.add(p1 = new JPanel(new FlowLayout(1)));
			sz(p1, 60, 30);
			p1.add(down[i] = new JLabel("-"));
			down[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (choose[Fs]) {
						if (chdown[Fs]) {
							chup[Fs] = true;
							qty[Fs].setText((rei(qty[Fs].getText()) - 1)+"");
							if ((rei(qty[Fs].getText())) == 0) {
								chdown[Fs] = false;
								down[Fs].setEnabled(false);
							}
						}
					}
					pricrUp();
				}
			});
			ft(down[i], 1, 15);
			down[i].setEnabled(false);
			p1.add(qty[i] = new JLabel("0"));
			ft(qty[i], 1, 15);
			qty[i].setEnabled(false);
			p1.add(up[i] = new JLabel("+"));
			up[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (choose[Fs]) {
						if (chup[Fs]) {
							chdown[Fs] = true;
							qty[Fs].setText((rei(qty[Fs].getText()) + 1)+"");
							if ((rei(qty[Fs].getText())) == cnt[Fs]) {
								chup[Fs] = false;
								up[Fs].setEnabled(false);
							}
						}
					}
					pricrUp();
				}
			});
			ft(up[i], 1, 15);
			up[i].setEnabled(false);
			choose[i] = false;
			chdown[i] = false;
			chup[i] = true;
		}

		cp.add(subtot = new JLabel("총 금액 : "));
		sz(subtot, 250, 20);

		for (int i = 0; i < cn1.length; i++) {
			cp.add(jl = new JLabel());
			sz(jl, 12, 1);
			cp.add(jb1[i] = new JButton(cn1[i]));
			sz(jb1[i], 100, 30);
			bk(jb1[i], Color.blue);
			fk(jb1[i], Color.white);
			ft(jb1[i], 1, 15);

			int fint = i;
			jb1[i].addActionListener(e-> {
				if (fint == 0) {
					if(rei(qty[0].getText()) + rei(qty[1].getText()) + rei(qty[2].getText()) + rei(qty[3].getText()) == 0) {
						wmsg("사이즈를 선택해 주세요.");
						return;
					}

					try {
						db.stmt.execute("INSERT INTO shoppingbasket (p_no,u_no,p_s,p_m,p_l,p_xl) VALUES ('" + vq.pno + "', '" + vq.uno + "', '" + rei(qty[0].getText()) + "', '" + rei(qty[1].getText()) + "', '" + rei(qty[2].getText()) + "', '" + rei(qty[3].getText()) + "')");
						rs = db.rs("");
					} catch (Exception e2) {
						e2.printStackTrace();
					}

					dispose();
				} else {
				}
			});
		}
		sz(cp, 265, 320);

		wp.setLayout(new FlowLayout());
		wp.add(jl = new JLabel());
		try {
			rs = db.rs("SELECT p_img FROM productlist where p_no = '" + vq.pno + "'");
			if (rs.next()) {
				jl.setIcon(blob(rs.getBinaryStream(1), 250, 320));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		line(jl, Color.black);

		ep.add(jsp = new JScrollPane(p2 = new JPanel(new GridLayout(0,1))));
		sz(jsp, 225, 305);
		emp(ep, 0, 10, 10, 10);

		jspup();
		shp();
	}

	public void jspup() {
		p2.removeAll();
		try {
			rs = db.rs("SELECT p2.p_name, p2.p_img, p2.p_no FROM clothingstore.productlist p1, clothingstore.productlist p2 WHERE p1.p_no = '" + vq.pno + "' AND p2.sb_no = p1.sb_no");

			if (rs.last()) {
				int rowCount = rs.getRow();
				JPanel[] panels = new JPanel[rowCount];
				int[] productIds = new int[rowCount];
				rs.beforeFirst();

				int index = 0;
				while (rs.next()) {
					if (rs.getInt(3) != vq.pno) {
						panels[index] = new JPanel(new BorderLayout());
						sz(panels[index], 200, 150);
						panels[index].add(new JLabel(blob(rs.getBinaryStream(2), 200, 150)), c);
						panels[index].add(new JLabel(rs.getString(1)), s);
						productIds[index] = rs.getInt(3);
						p2.add(panels[index]);
						index++;
					}
				}

				p2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						for (int i = 0; i < panels.length; i++) {
							if (panels[i].getBounds().contains(e.getPoint())) {
								vq.pno = productIds[i];
								dispose();
								new c상품();
								return;
							}
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}
	
	public void pricrUp() {
		int subcnt = rei(qty[0].getText()) + rei(qty[1].getText()) + rei(qty[2].getText()) + rei(qty[3].getText());
		int subvalue = tot * subcnt;
		subtot.setText("상품 금액 : " + def.format(subcnt));
	}

	private JPanel Starsup() {
		double average = 0;
		try {
			rs = db.rs("SELECT AVG(re.r_star) AS average_rating FROM clothingstore.purchase pu, clothingstore.review re WHERE pu.p_no = '" + vq.pno + "' AND re.pu_no = pu.pu_no");
			if (rs.next()) {
				average = rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JPanel result = new JPanel(new FlowLayout(0));

		int fullStars = (int) (average / 0.5);
		boolean hasHalfStar = (average % 0.5) >= 0.25;

		for (int i = 0; i < fullStars / 2; i++) {
			result.add(addStar("별점이미지/1"));
		}

		if (hasHalfStar) {
			result.add(addStar("별점이미지/3"));
		}

		int remainingStars = 5 - (fullStars / 2 + (hasHalfStar ? 1 : 0));
		for (int i = 0; i < remainingStars; i++) {
			result.add(addStar("별점이미지/2"));
		}
		return result;
	}

	private JLabel addStar(String image) {
		JLabel jl = new JLabel();
		jpg(jl, image, 25, 25);
		return jl;
	}
}
