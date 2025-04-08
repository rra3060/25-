package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class a메인 extends aframe {
	
	JLabel jn[] = new JLabel[2];
	JTree tree;
	JButton jb[] = new JButton[2];
	String bn[] = "검색,로그인".split(",");
	JButton jb1[] = new JButton[3];
	String bn1[] = "가격순(↓),가격순(↑),별점순(↓)".split(",");

	public a메인() {
		fs("메인");
		emp(pc, 10, 10, 10, 10);

		np.add(p0 = new JPanel(new FlowLayout(0, 20, 0)));
		p0.add(jl = new JLabel("ClothingStore"));
		ft(jl, 1, 30);
		p0.add(jt = new JTextField());
		sz(jt, 250, 30);
		for (int i = 0; i < jb.length; i++) {
			p0.add(jb[i] = new JButton(bn[i]));
			sz(jb[i], 80, 30);
			bl(jb[i]);
		}
		emp(p0, 10, 0, 10, 0);

		np.add(p0 = new JPanel(new BorderLayout()), e);
		emp(p0, 0, 0, 15, 0);
		p0.add(img = new JLabel(), w);
		emp(img, 0, 0, 0, 10);
		p0.add(p1 = new JPanel(new GridLayout(0, 1)));
		for (int i = 0; i < jn.length; i++) {
			p1.add(jn[i] = new JLabel());
		}
		emp(p1, 5, 0, 5, 0);
		try {
			if (vq.uno != 0) {
				rs = db.rs("select * from user where u_no = " + vq.uno);
				if (rs.next()) {
					img.setIcon(blob(rs.getBinaryStream(7), 30, 30));
					jn[0].setText(rs.getString(4));
					jn[1].setText(def.format(rs.getInt(5)) + "원");
				}
				jb[1].setVisible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("전체"), node[] = new DefaultMutableTreeNode[5];
		try {
			String rt[] = "상의,하의,신발,아우터,액세서리".split(",");
			for (int i = 0; i < node.length; i++) {
				node[i] = new DefaultMutableTreeNode(rt[i]);
				rs = db.rs("select * from subcategory where c_no = " + (i + 1));
				while (rs.next()) {
					node[i].add(new DefaultMutableTreeNode(rs.getString(2)));
				}
				root.add(node[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		wp.add(jsp = new JScrollPane(tree = new JTree(root)));
		tree.collapseRow(0);
		tree.addTreeSelectionListener(e -> {
			javax.swing.tree.TreePath ph = tree.getSelectionPath();
			if (ph != null && ph.getPathCount() == 1) {
				tree.expandRow(0);
			}
		});
		sz(jsp, 100, 0);

		cp.add(jsp = new JScrollPane(p0 = new JPanel(new BorderLayout())));
		emp(p0, 0, 10, 10, 10);
		sz(jsp, 900, 515);

		p0.add(p1 = new JPanel(new FlowLayout(0)), n);
		for (int i = 0; i < bn1.length; i++) {
			p1.add(jb1[i] = new JButton(bn1[i]));
			sz(jb1[i], 90, 30);
			bk(jb1[i], Color.white);
		}

		p0.add(mp = new JPanel(new GridLayout(0, 4, 10, 10)), c);

		dUP();
		shp();
	}

	private void dUP() {
		mp.removeAll();
		try {
			int i = 0;
			String c = "";
			rs = db.rs("select p.p_no, p_name, p_price, (p_price * (1 - sa_sale / 100)), "
					+ "if(start_date <= '" + daf.format(new Date()) + "' and end_date >= '" + daf.format(new Date())
					+ "', 1, 0), p_img from productlist p left join salelist s on s.p_no = p.p_no where p_name like '%"
					+ jt.getText() + "%'");
			while (rs.next()) {
				mp.add(p0 = new JPanel(new BorderLayout()));
				line(p0, Color.black);
				p0.add(img = new JLabel(blob(rs.getBinaryStream(6), 150, 170)));
				emp(img, 10, 0, 0, 0);
				p0.add(p1 = new JPanel(new BorderLayout()), s);
				emp(p1, 0, 10, 0, 0);
				p1.add(jl = new JLabel(name(rs.getString(2), 15)));
				p1.add(p2 = new JPanel(new FlowLayout(0)), s);
				emp(p2, 0, 35, 0, 0);
				if (rs.getInt(5) == 1) {
					p2.add(jl = new JLabel("<html><strike>" + rs.getString(3) + "원</strike></html>"));
					p2.add(jl = new JLabel(rs.getInt(4) + "원"));
					fk(jl, Color.red);
				} else {
					p2.add(jl = new JLabel(rs.getString(3) + "원"));
					p2.add(jl = new JLabel());
				}
				i++;
			}
			for (int j = 0; j < 8 - i; j++) {
				mp.add(new JPanel());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}