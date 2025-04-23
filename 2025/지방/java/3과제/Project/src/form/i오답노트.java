package form;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class i�����Ʈ extends aframe {

    String que[], ans[];
    int cnt = 0, wno[], ct = 1;
    JLabel matter[] = new JLabel[2];
    Integer a[] = {0, 1};
    JButton jb[] = new JButton[3];
    String bn[] = "<,Ȯ���ϱ�,>".split(",");
    DrawingPanel p2;

    public i�����Ʈ() {
        fs("�����Ʈ");

        try {
            rs = db.rs("SELECT * FROM wrong where uno = " + vq.uno);
            rs.last();
            wno = new int[rs.getRow()];
            que = new String[rs.getRow()];
            ans = new String[rs.getRow()];
            rs.beforeFirst();

            while (rs.next()) {
                wno[cnt] = rs.getInt(1);
                que[cnt] = rs.getString(3);
                ans[cnt] = rs.getString(4);
                cnt++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        np.add(jl = new JLabel("�����Ʈ", 0));
        np.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
        ft(jl, 1, 20);

        if (cnt == 0) {
            cp.add(jl = new JLabel("������ �����ϴ�.", 0));
            ft(jl, 0, 20);
            fk(jl, Color.red);
        } else {
            cp.add(p0 = new JPanel(new FlowLayout(0)), n);
            p0.add(jl = new JLabel("Ǯ��� �� ������"));
            p0.add(jl = new JLabel(cnt + "��"));
            fk(jl, Color.red);
            p0.add(jl = new JLabel(" ���ҽ��ϴ�."));

            cp.add(p0 = new JPanel(new GridLayout(0, 2, 15, 0)), c);
            for (int i = 0; i < matter.length; i++) {
                p0.add(p1 = new JPanel(new FlowLayout()));
                p1.add(matter[i] = new JLabel("���� : ", i == 1 ? 2 : 0));
                sz(matter[i], 275, 75);
                ft(matter[i], 1, 13);
                line(matter[i], Color.black);
            }
            matter[0].setText(que[0]);
            matter[1].setVerticalAlignment(1);

            cp.add(p0 = new JPanel(new FlowLayout(4)), s);
            p0.add(jc = new JComboBox<>(a));
            combo com = new combo();
            jc.setRenderer(com);
            sz(jc, 125, 25);
            
            p2 = new DrawingPanel();
            sp.add(p2, n);
            sz(p2, 500, 200);
            line(p2, Color.black);
            jc.addActionListener(e -> p2.setMode(jc.getSelectedIndex()));
            jc.setSelectedIndex(1); // �ʱ� ���ð��� "�����"�� ����
            p2.setMode(1);
            
            sp.add(p0 = new JPanel(new FlowLayout()),s);
			p0.add(jb[0] = new JButton("<"));
			sz(jb[0], 100, 30);
			p0.add(jl = new JLabel());
			sz(jl, 85, 30);
			p0.add(jb[1] = new JButton("Ȯ���ϱ�"));
			sz(jb[1], 175, 30);
			p0.add(jl = new JLabel());
			sz(jl, 85, 30);
			p0.add(jb[2] = new JButton(">"));
			sz(jb[2], 100, 30);
			
			jb[0].setEnabled(false);
			
			for (int i = 0; i < jb.length; i++) {
				bl(jb[i]);
				int a = i;
				jb[i].addActionListener(e -> {
					if (a == 0) {
						jb[2].setEnabled(true);
						ct--;
						if (ct == 1) {
							jb[0].setEnabled(false);
						}
						matter[0].setText(que[ct - 1]);
					} else if (a == 1) {
						System.out.println(cnt + "/" + que.length);
					} else {
						jb[0].setEnabled(true);
						ct++;
						if (ct == cnt) {
							jb[2].setEnabled(false);
						}
						matter[0].setText(que[ct - 1]);
						
					}
				});
			}
        }
        
        shp();
    }
    @Override
  	public void windowClosing(WindowEvent e) {
  		new e�л�����();
  	}
}


class DrawingPanel extends JPanel {
    private BufferedImage canvas;
    private Graphics2D g2;
    private int mode = 0;

    public DrawingPanel() {
        canvas = new BufferedImage(800, 350, 6);//BufferedImage.TYPE_INT_ARGB (������)
        g2 = canvas.createGraphics();
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (mode == 1) clearCanvas();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (mode == 0) {
                    g2.fillOval(e.getX() - 4, e.getY() - 4, 8, 8);
                    repaint();
                }
            }
        });
    }

    static boolean ck;
public void setMode(int mode) {
    this.mode = mode;
    if (mode == 1) {
    	if (ck == true) {
            // "�׷��� �׸��� ��� �������ϴ�." �޽����� ����� ������ ǥ��
            if (canvas != null) {
                ((i�����Ʈ) SwingUtilities.getWindowAncestor(this)).imsg("�׷��� �׸��� ��� �������ϴ�.");
            }
            clearCanvas(); // �޽��� Ȯ�� �� �ٷ� ĵ���� ����
    	}
    } else {
        // �׸��⸦ �����ϰڴٴ� �޽����� �׻� ǥ��
    	ck = true;
        ((i�����Ʈ) SwingUtilities.getWindowAncestor(this)).imsg("�׸��⸦ �����ϰڽ��ϴ�.");
    }
}

    

    public void clearCanvas() {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        g2.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }
}

class combo extends JLabel implements ListCellRenderer {

      @Override
      public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
         String cn[] = "�׸���,�����".split(",");
         String ln[] = "pencil,eraser".split(",");
         ImageIcon icon[] = new ImageIcon[2];
         
         int no = (Integer) value;
         for (int i = 0; i < cn.length; i++) {
            icon[i] = new ImageIcon(new ImageIcon("datafiles/icon/" + ln[i] + ".png").getImage().getScaledInstance(20, 20, 4));
         }
         setIcon(icon[no]);
         setText(cn[no]);
         return this;
      }
}
