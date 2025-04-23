package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class e학생메인 extends aframe {

   int cnt = 1;
   
   String ln[] = "질문 등록,내 질문,문제퀴즈,오답노트".split(",");
   
   Thread th = new Thread();
   
   
   public e학생메인() {
      fs("학생메인");
      
      np.add(img = new JLabel());
      png(img, "main/1", 650, 350);
      img.setLayout(new BorderLayout());
      
      img.add(jl = new JLabel(" <"),w);
      ft(jl, 1, 35);
      fk(jl, Color.white);
      jl.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            if (cnt == 1) {
               cnt = 3;
            } else {
               cnt--;
            }
            png(img, "main/" + cnt, 650, 350);
         }
      });
      
      img.add(jl = new JLabel("> "),e);
      ft(jl, 1, 35);
      fk(jl, Color.white);
      jl.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            if (cnt == 3) {
               cnt = 1;
            } else {
               cnt++;
            }
            png(img, "main/" + cnt, 650, 350);
         }
      });

      try {
         rs = db.rs("SELECT * FROM catalog where uno = " + vq.uno);
         
         int i = 0;
         while (rs.next()) {
            if (rs.getString(8) == null   || rs.getString(8).equals("")) {
               i++;
            }
         }
         
         cp.setLayout(new FlowLayout());
         cp.add(jl = new JLabel("대기중인 질문 :"));
         jl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if (e.getClickCount() == 2) {
                  dispose();
                  new h내질문();
               }
            }
         });
         ft(jl, 0, 14);
         cp.add(jl = new JLabel(i +""));
         ft(jl, 0, 14);
         fk(jl, Color.red);
         cp.add(jl = new JLabel("개"));
         ft(jl, 0, 14);
         emp(jl, 1, 0, 0, 0);
         
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      emp(cp, 10, 0, 10, 0);
      
      sp.add(p0 = new JPanel(new GridLayout(1,4,25,5)));
      for (int i = 0; i < ln.length; i++) {
         p0.add(p1 = new JPanel(new FlowLayout()));
         p1.add(jl = new JLabel(ln[i],0));
         ft(jl, 0, 13);
         sz(jl, 100, 35);
         jl.setBorder(new MatteBorder(0,0,1,0,Color.black));
         
         int a = i;
         jl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if (a == 0) {
                  new f질문등록();
               } else if (a == 1) {
                  new h내질문();
               } else if (a == 2) {
                  new j퀴즈();
               } else {
                  new i오답노트();
               }
               th.interrupt();
               dispose();
            }
         });
      }
      
      th = new Thread(this);
      th.start();
      
      shp();
   }
   
   
   
   @Override
   public void run() {
      try {
         
         for (;;) {
            png(img, "main/" + cnt, 650, 350);
            th.sleep(3000);
            cnt++;
            if (cnt == 4) {
               cnt = 1;
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }



   @Override
   public void windowClosing(WindowEvent e) {
      th.interrupt();
      new a로그인();
   }

}
