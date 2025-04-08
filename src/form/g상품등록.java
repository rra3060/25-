package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import aframe.aframe;
import jdbc.db;

public class g상품등록 extends aframe{
	TitledBorder tb;
	JLabel im;
	String jlist[] = "상품명,가격,카테고리,서브 카테고리".split(",");
	JPanel p[] = new JPanel[4];
	JPanel csp,ccp,cnp;
	JButton jbs;
	JButton jb1[] = new JButton[4];
	JTextField jt1[] = new JTextField[5];
	JComboBox jc1[] = new JComboBox[4];
	String a[];
	boolean check;
	JFileChooser choo = new JFileChooser();
	BufferedImage buf;
	
	public g상품등록() {
		fs("상품등록");
		wp.add(jp = new JPanel());
		jp.setBorder(tb = new TitledBorder(new LineBorder(Color.BLACK), ""));
		sz(wp, 250, 300);
		emp(wp, 10, 10, 10, 10);
		jp.add(im = new JLabel());
		jp.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				imageupload();
			}
		});
		sz(im,250,300);
		
		check = false;
		
		cp.add(cnp = new JPanel(new BorderLayout()),n);
		cp.add(ccp = new JPanel(new BorderLayout()),c);
		cp.add(csp = new JPanel(new BorderLayout()),s);
		
		sz(cp,355,200);
		emp(cp,10,0,0,0);
		
		sz(cnp,355,180);
		cnp.setLayout(new BoxLayout(cnp,BoxLayout.Y_AXIS));
		
		load();
		
		csp.setLayout(new FlowLayout(2));
		csp.add(jb = new JButton("저장"));
		bl(jb);
		sz(jb,100,30);
		emp(csp,0,0,5,5);
		jb.addActionListener(e->{
			
		});
		shp();
	}
	
	private void imageupload() {
		FileNameExtensionFilter f= new FileNameExtensionFilter("JPG image","jpg");
		choo.setFileFilter(f);
		choo.showOpenDialog(null);
		File selectedFile = choo.getSelectedFile();
		try {
			buf = ImageIO.read(selectedFile);
			im.setIcon(new ImageIcon(buf.getScaledInstance(250, 320, 4)));
		}catch(Exception e) {
		}
	}
	
	private void load() {
		for (int i = 0; i < jlist.length; i++) {
			cnp.add(p[i] = new JPanel());
			p[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			p[i].add(jl = new JLabel(jlist[i]));

			sz(jl, 80, 10);
			if(i>=2) {
				if(check == false) {
					p[i].add(jc1[i] = new JComboBox());
					sz(jc1[i], 160, 24);
					for(int j=0;j<jlist.length;j++) {
						try {
							if(i==2) {
								rs = db.rs("select * from category where c_no = " + (j + 1));
							}else {
								rs = db.rs("select * from subcategory where c_no = " + (j + 1));
							}
							while (rs.next()) {
								jc1[i].addItem(rs.getString(2));
							}
						}catch(Exception e) {
						
						}
					}
					if(i==2) {
						p[i].add(jbs = new JButton("직접 입력"));
						sz(jbs, 90, 24);
						bl(jbs);
						jbs.addActionListener(e->{
							boolean check = true;
							load();
						});
					}
				}else {
					for(int j=2;j<jlist.length;j++) {
						cnp.remove(p[j]);
						cnp.add(p[j] = new JPanel());
						p[j].setLayout(new FlowLayout(FlowLayout.LEFT));
						p[j].add(jl = new JLabel(jlist[j]));
					}
					for(int j=2;j<jlist.length;j++) {
						p[j].add(jt1[j] = new JTextField(12));
						p[j].add(jb1[j] = new JButton("등록"));
						sz(jb1[j],80,24);
						bl(jb1[j]);
					}
				}
			}else {
				p[i].add(jt1[i] = new JTextField(23));
				sz(jt1[i],200,24);
			}
		}
		cnp.revalidate();
		cnp.repaint();
	}
}
