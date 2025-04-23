package aframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class aframe extends JFrame implements ActionListener, WindowListener, MouseListener, Runnable {
	
	public static JPanel np, wp, cp, sp, ep, p0, p1, p2, p3, p4, p5, jp, mp, pc;
	public static JPanel p[] = new JPanel[10];
	public static JLabel img, jl,jl1;
	public static JTextField jt;
	public static JTextArea jte;
	public static JButton jb;
	public static JComboBox jc;
	public static ResultSet rs;
	public static DefaultTableCellRenderer cell = new DefaultTableCellRenderer();
	public static DefaultTableModel dtm;
	public static String n = BorderLayout.NORTH;
	public static String e = BorderLayout.EAST;
	public static String c = BorderLayout.CENTER;
	public static String w = BorderLayout.WEST;
	public static String s = BorderLayout.SOUTH;
	public static JTable jta;
	public static JScrollPane jsp;
	public static JCheckBox jck;
	public static Thread th;
	public static JSpinner spin;
	public static SimpleDateFormat daf = new SimpleDateFormat("yyyy-MM-dd");
	public static DecimalFormat def = new DecimalFormat("#,##0");
	public static Color green = new Color(0,100,0);
	public static TitledBorder tb;
	
	public JTextField hint(String t) {
		return new JTextField() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if (getText().isEmpty()) {
					g.setColor(Color.LIGHT_GRAY);
					g.drawString(t, getInsets().left, getHeight() / 2 + 5);
				}
			}
		};
	}
	
	public void fs(String t) {
		setTitle(t);
		setDefaultCloseOperation(2);
		
		setIconImage(Toolkit.getDefaultToolkit().createImage("datafiles/icon/logo.png"));
		
		add(pc = new JPanel(new BorderLayout()));
		pc.add(np = new JPanel(new BorderLayout()),n);
		pc.add(wp = new JPanel(new BorderLayout()),w);
		pc.add(cp = new JPanel(new BorderLayout()),c);
		pc.add(sp = new JPanel(new BorderLayout()),s);
		pc.add(ep = new JPanel(new BorderLayout()),e);
		
		addWindowListener(this);
		cell.setHorizontalAlignment(0);
		th = new Thread(this);
	}
	
	//public void bl(JComponent c) {
	//	bk(c, Color.green);
	//	fk(c, Color.white);
	//}
	
	
	public static void gifup(JLabel c, String data, int w, int h) {
		try {
			c.setIcon(new ImageIcon(new ImageIcon("datafiles/image/icon/" + data + ".gif").getImage().getScaledInstance(w, h, 0)));
		} catch (Exception e) {
			c.setIcon(new ImageIcon("datatfiles/image/icon/img-logo.gif"));
		}
	}
	
	public void shp() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void sh() {
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void emp(JComponent c, int a, int b, int cc, int d) {
		c.setBorder(new EmptyBorder(a, b, cc, d));
	}
	
	public void sz(JComponent c, int w, int h) {
		c.setPreferredSize(new Dimension(w, h));
	}
	
	public void jpg(JLabel jl, String data, int w, int h) {
		jl.setIcon(new ImageIcon(new ImageIcon("datafiles/" + data + ".jpg").getImage().getScaledInstance(w, h, 4)));
	}
	
	public void jpg2(JLabel jl, String data, int a, int w, int h) {
		jl.setIcon(new ImageIcon(new ImageIcon("datafiles/" + data + "/" + a + ".jpg").getImage().getScaledInstance(w, h, 4)));
	}
	
	public void png(JLabel jl, String data, int w, int h) {
		jl.setIcon(new ImageIcon(new ImageIcon("datafiles/" + data + ".png").getImage().getScaledInstance(w, h, 4)));
	}
	
	public void wmsg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "°æ°í", 0);
	}
	
	public void imsg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Á¤º¸", 1);
	}
	
	public void ft(JComponent c, int a, int b) {
		c.setFont(new Font("¸¼Àº °íµñ", a, b));
	}
	
	public void ft2(JComponent c, int a, int b) {
		c.setFont(new Font("Calibri", a, b));
	}
	
	public void line(JComponent c, Color col) {
		c.setBorder(new LineBorder(col));
	}
	
	public void bk(JComponent c, Color col) {
		c.setBackground(col);
	}
	
	public void fk(JComponent c, Color col) {
		c.setForeground(col);
	}
	
	public int rei(String t) {
		try {
			return Integer.parseInt(t);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public boolean cknum(String t) {
		try {
			Integer.parseInt(t);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean cdate(String t) {
		try {
			if (daf.format(daf.parse(t)).equals(t))
				return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	public ImageIcon blob(InputStream is, int w, int h) {
		try {
			Image img = ImageIO.read(is);
			img = img.getScaledInstance(w, h, 0);
			return new ImageIcon(img);
		} catch (Exception e) {
		}
		return null;
	}
	
	public String name(String t, int count) {
		return t.length() > count + 1 ? t.substring(0,count)+"..." : t;
	}
	
	public void bl(JComponent c) {
		bk(c,Color.blue);
		fk(c,Color.white);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
