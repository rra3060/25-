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

public class f질문등록 extends aframe {
    JFileChooser choo = new JFileChooser(System.getProperty("user.dir") + "/datafiles/question/");
    BufferedImage buf;
    JLabel tc;
   
   public f질문등록() {
      fs("질문 등록");      
      emp(pc, 10, 15, 15, 15);      
      np.add(jl = new JLabel("질문 등록",0));
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
      p0.add(jt = hint("제목"),n);
      sz(jt, 300, 30);
      line(jt, Color.black);
      
      p0.add(jte = ahint("질문내용"),c);
      sz(jte, 300, 200);
      Border bf = BorderFactory.createCompoundBorder(new EmptyBorder(10, 0, 10, 0), new LineBorder(Color.black, 1));
      jte.setBorder(bf);
      jte.setLineWrap(true);
      
      p0.add(p1 = new JPanel(new BorderLayout()),s);
      
      p1.add(tc = new JLabel(vq.teacher == null ? "선생님을 선택해주세요." : vq.teacher + " 선생님",0),w);
      ft(tc, 0, 12);
      sz(tc, 150, 30);
      tc.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
        	 tc.setText("선생님을 선택해주세요."); 
        	 vq.tt = jt.getText();
             vq.exp = jte.getText();
             vq.buf = buf;
             vq.move = 1; // 선택 후 돌아오면 내용 유지
             dispose();
             new g선생목록();
         }
      });
      line(tc, Color.black);
      
      if (vq.buf != null) {
         buf = vq.buf;
         img.setIcon(new ImageIcon(new ImageIcon(vq.buf).getImage().getScaledInstance(350, 250, 4)));
      }
      
      p1.add(jb = new JButton("질문 등록"),e);
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
               int a = JOptionPane.showConfirmDialog(null, "선생님을 선택하지 않았습니다. 선생님 폼으로 이동하시겠습니까?","질문",JOptionPane.YES_NO_OPTION);
               if (a == JOptionPane.YES_OPTION) {
                  vq.move = 1;
                  vq.tt = jt.getText();
                  vq.exp = jte.getText();
                  dispose();
                  new g선생목록();
               } 
               return;
            }
            
            if (jt.getText().equals("") || jte.getText().equals("")) {
               wmsg("내용을 입력해 주세요.");
               return;
            }
            
            if (buf == null) {
               wmsg("사진을 선택해주세요.");
               return;
            }
            String X[] = "씨발,시발,썅,병신,새끼,또라이,개새끼,존나".split(",");
            for (int i = 0; i < X.length; i++) {
               if (jt.getText().contains(X[i]) || jte.getText().contains(X[i])) {
                  wmsg("비속어는 사용하실 수 없습니다.");
                  return;
               }
            }
            
            db.stmt.execute("insert into catalog values('0','" + vq.uno + "','" + vq.tno + "','" + vq.type + "','" + jt.getText() + "','" + daf.format(new Date()) + "','" + jte.getText() + "',null)");
            imsg("질문이 등록되었습니다.");
            vq.buf = null;
            vq.move = 0; // 질문 등록 후 move 초기화
            dispose();
            new e학생메인();
            
         } catch (Exception e2) {
            e2.printStackTrace();
         }
      });
      
      if (vq.move == 1) { 
	    // 선생님 선택 후 돌아온 경우 (move == 1이면 유지)
	    jt.setText(vq.tt);
	    jte.setText(vq.exp);
    	    
     } else { 
	    // 새로 폼을 연 경우 (move == 0이면 초기화)
	    vq.tt = "";
	    vq.exp = "";
	    vq.buf = null;
	    vq.type = "";
	    jt.setText("");
	    jte.setText("");
	    img.setIcon(null);
	    vq.teacher = null; 
	    tc.setText("선생님을 선택해주세요."); // 초기 메시지로 복귀
     }

      shp();
   }
    @Override
    public void windowClosing(WindowEvent e) {
       new e학생메인();
    }
}
