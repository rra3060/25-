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
import jdbc.db;
import jdbc.vq;

public class b선생님메인 extends aframe {
   JButton jb[] = new JButton[2];
   String bn[] = "질문 목록,통계".split(",");
   
   public b선생님메인() {
      fs("선생님메인");      
      emp(pc, 0, 5, 5, 20);      
      wp.add(p0 = new JPanel(new BorderLayout()),n);
      sz(p0, 250, 65);
      p0.add(jl = new JLabel(),w);
      png(jl, "icon/logo", 60, 60);
      jl.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            vq.uno = 0;
            imsg("로그아웃 되었습니다.");
            dispose();
            new a로그인();
         }
      });
      
      p0.add(p1 = new JPanel(new GridLayout(0,1,0,0)),c);
      for (int i = 0; i < 2; i++) {
         p1.add(p2 = new JPanel(new FlowLayout(0)));
         if (i == 0) {
            p2.add(jl = new JLabel(vq.uname));
            ft(jl, 1, 15);
            p2.add(jl = new JLabel("선생님,"));
            ft(jl, 0, 13);
         } else {
            p2.add(jl = new JLabel("환영합니다"));
            ft(jl, 0, 13);
         }
      }
      emp(p1, 10, 5, 10, 0);
      
      wp.add(p0 = new JPanel(new GridLayout(0,1,10,50)), c);
      for (int i = 0; i < bn.length; i++) {
         p0.add(p1 = new JPanel(new FlowLayout()));
         p1.add(jb[i] = new JButton(bn[i]));
         sz(jb[i], 125, 40);
         bl(jb[i]);
         ft(jb[i], 0, 15);
         
         int a = i;
         jb[i].addActionListener(e -> {
            if (a == 0) {
               dispose();
               new c질문목록();
            } else {
               dispose();
               new d통계();
            }
         });
      }
      emp(p0, 30, 0, 100, 0);
      
      try {
         rs = db.rs("SELECT * FROM catalog where tno = " + vq.uno);         
         double i = 0,cnt = 0;
         while (rs.next()) {
            if (rs.getString(8) != null) {
               cnt++;
            }
            i++;
         }
         double per = cnt / i * 100;
         
         wp.add(jl = new JLabel("내 답변률 : " + String.format("%.0f", per) + "%"),s);
         ft(jl, 0, 20);
         emp(jl, 0, 50, 0, 0);
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      cp.add(jl = new JLabel("답변하지 않은 질문"),n);
      ft(jl, 1, 15);
      emp(jl, 0, 0, 25, 0);
      
      cp.add(jsp = new JScrollPane(jp = new JPanel(new GridLayout(0,1,10,10))), c);
      sz(jsp, 350, 250);
      line(jsp, Color.black);
      emp(cp, 20, 0, 40, 0);
      emp(jp, 5, 5, 0, 10);
      jsp.setHorizontalScrollBarPolicy(31);
      
      dup();
      shp();
   }
   
   private void dup() {
      jp.removeAll();
      try {
         rs = db.rs("SELECT c.cno,c.uno,c.title,u.name,u.grade,c.explan FROM catalog c,teacher t,user u where c.explan is null and c.uno = u.uno and c.tno = t.tno and c.tno = " + vq.uno);
         
         int i = 0;
         while (rs.next()) {
            jp.add(p0 = new JPanel(new BorderLayout()));
            sz(p0, 320, 135);
            line(p0, Color.LIGHT_GRAY);
            int cno = rs.getInt(1);
            
            p0.add(p1 = new JPanel(new GridLayout()), w);
            p1.add(img = new JLabel());
            img.addMouseListener(new MouseAdapter() {
               @Override
               public void mouseClicked(MouseEvent e) {
                  if (e.getClickCount() == 2   ) {
                     dispose();
                     vq.cno=cno;
                     new k문제풀기();
                  }
               }
            });
            
            line(img, Color.LIGHT_GRAY);
            jpg(img, "user/" + rs.getInt(2), 100, 100);
            emp(p1, 20, 20, 10, 10);
            
            p0.add(p1 = new JPanel(new BorderLayout()), c);
            emp(p1, 30, 0, 10, 0);
            
            String tt = rs.getString(3);
            p1.add(jl = new JLabel("<html>" + tt.substring(0,10) + "<br>" + tt.substring(10,tt.length())),n);
            ft(jl, 1, 15);
            
            p1.add(jl = new JLabel(rs.getString(4) + " 학생"),c);
            ft(jl, 0, 13);
            emp(jl, 10, 0, 0, 0);
            
            p1.add(jl = new JLabel("학년 : " + rs.getString(5)),s);
            ft(jl, 0, 13);
            i++;
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Override
   public void windowClosing(WindowEvent e) {
      new a로그인();
   }
}
