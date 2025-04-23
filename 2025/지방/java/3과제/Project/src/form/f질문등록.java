package form;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class f������� extends aframe {
    JFileChooser choo = new JFileChooser(System.getProperty("user.dir") + "/datafiles/question/");
    BufferedImage buf;
    JLabel tc;
   
   public f�������() {
      fs("���� ���");      
      emp(pc, 10, 15, 15, 15);      
      np.add(jl = new JLabel("���� ���",0));
      ft(jl, 1, 20);
      emp(np, 0, 0, 15, 0);
      
      wp.add(img = new JLabel());
      sz(img, 350, 250);
      line(img, Color.black);
      img.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            FileNameExtensionFilter f = new FileNameExtensionFilter("JPG Images", "jpg");
            choo.setFileFilter(f);
            choo.showOpenDialog(null);

            try {
                buf = ImageIO.read(new File(choo.getSelectedFile().getPath()));
                img.setIcon(new ImageIcon(new ImageIcon(buf).getImage().getScaledInstance(350, 250, 4)));
                vq.type = choo.getSelectedFile().getName().replace(".jpg", "");
                vq.buf = buf;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
         }
      });
      
      cp.add(p0 = new JPanel(new BorderLayout()));
      emp(cp, 0, 15, 0, 0);
      p0.add(jt = hint("����"),n);
      sz(jt, 300, 30);
      line(jt, Color.black);
      
      p0.add(jte = ahint("��������"),c);
      sz(jte, 300, 200);
      Border bf = BorderFactory.createCompoundBorder(new EmptyBorder(10, 0, 10, 0), new LineBorder(Color.black, 1));
      jte.setBorder(bf);
      jte.setLineWrap(true);
      
      p0.add(p1 = new JPanel(new BorderLayout()),s);
      
      p1.add(tc = new JLabel(vq.teacher == null ? "�������� �������ּ���." : vq.teacher + " ������",0),w);
      ft(tc, 0, 12);
      sz(tc, 150, 30);
      tc.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
        	 tc.setText("�������� �������ּ���."); 
        	 vq.tt = jt.getText();
             vq.exp = jte.getText();
             vq.buf = buf;
             vq.move = 1; // ���� �� ���ƿ��� ���� ����
             dispose();
             new g�������();
         }
      });
      line(tc, Color.black);
      
      if (vq.buf != null) {
         buf = vq.buf;
         img.setIcon(new ImageIcon(new ImageIcon(vq.buf).getImage().getScaledInstance(350, 250, 4)));
      }
      
      p1.add(jb = new JButton("���� ���"),e);
      ft(jb, 0, 13);
      bl(jb);
      sz(jb, 120, 30);
      jt.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
        	 if (jt.getText().length() > 30) jt.setText(jt.getText().substring(0, 30));
         }
      });
      
      jte.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
        	 if (jte.getText().length() > 500) jte.setText(jte.getText().substring(0, 500));
         }
      });
      
      jb.addActionListener(e -> {
         try {
            if (vq.teacher == null) {
               int a = JOptionPane.showConfirmDialog(null, "�������� �������� �ʾҽ��ϴ�. ������ ������ �̵��Ͻðڽ��ϱ�?","����",JOptionPane.YES_NO_OPTION);
               if (a == JOptionPane.YES_OPTION) {
                  vq.move = 1;
                  vq.tt = jt.getText();
                  vq.exp = jte.getText();
                  dispose();
                  new g�������();
               } 
               return;
            }
            
            if (jt.getText().equals("") || jte.getText().equals("")) {
               wmsg("������ �Է��� �ּ���.");
               return;
            }
            
            if (buf == null) {
               wmsg("������ �������ּ���.");
               return;
            }
            String X[] = "����,�ù�,��,����,����,�Ƕ���,������,����".split(",");
            for (int i = 0; i < X.length; i++) {
               if (jt.getText().contains(X[i]) || jte.getText().contains(X[i])) {
                  wmsg("��Ӿ�� ����Ͻ� �� �����ϴ�.");
                  return;
               }
            }
            
            db.stmt.execute("insert into catalog values('0','" + vq.uno + "','" + vq.tno + "','" + vq.type + "','" + jt.getText() + "','" + daf.format(new Date()) + "','" + jte.getText() + "',null)");
            imsg("������ ��ϵǾ����ϴ�.");
            vq.buf = null;
            vq.move = 0; // ���� ��� �� move �ʱ�ȭ
            dispose();
            new e�л�����();
            
         } catch (Exception e2) {
            e2.printStackTrace();
         }
      });
      
      if (vq.move == 1) { 
	    // ������ ���� �� ���ƿ� ��� (move == 1�̸� ����)
	    jt.setText(vq.tt);
	    jte.setText(vq.exp);
    	    
     } else { 
	    // ���� ���� �� ��� (move == 0�̸� �ʱ�ȭ)
	    vq.tt = "";
	    vq.exp = "";
	    vq.buf = null;
	    vq.type = "";
	    jt.setText("");
	    jte.setText("");
	    img.setIcon(null);
	    vq.teacher = null; 
	    tc.setText("�������� �������ּ���."); // �ʱ� �޽����� ����
     }

      shp();
   }
    @Override
    public void windowClosing(WindowEvent e) {
       new e�л�����();
    }
}
