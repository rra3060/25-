package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JWindow;

import aframe.aframe;
import jdbc.db;

public class d통계 extends aframe {

   JFileChooser choo = new JFileChooser(System.getProperty("user.dir") + "/datafiles/question/");
    BufferedImage buf;    
   String cn[] = "인기많은 선생님,어려운 문제".split(",");
   
   Color col[] = {Color.red,Color.yellow,Color.green,Color.blue,Color.MAGENTA};
   String name[] = new String [5],per[] = new String[5];
   int num[] = new int[5],max;
   
   String tool[] = new String[5];
   
   
   public d통계() {
       fs("통계");
       setSize(600, 500);
       np.add(jl = new JLabel("통계", 0));
       ft(jl, 1, 30);

       JLayeredPane layeredPane = new JLayeredPane();
       cp.setLayout(null);
       cp.add(layeredPane);
       layeredPane.setBounds(0, 0, 550, 400);

       jc = new JComboBox<>(cn);
       jc.setBounds(350, 50, 150, 30);
       layeredPane.add(jc, Integer.valueOf(1)); // 콤보는 위 레이어

       jc.addActionListener(e -> {
           dup();
       });

       jp = new JPanel(new BorderLayout());
       jp.setBounds(0, 0, 550, 400);
       layeredPane.add(jp, Integer.valueOf(0)); // 차트는 아래 레이어

       dup();
       sh();
   }

   
   private void dup() {
      jp.removeAll();
      jp.add(p1 = new JPanel() {
         {
            addMouseMotionListener(new MouseMotionAdapter() {
               public void mouseMoved(java.awt.event.MouseEvent e) {
                  int mouseX = e.getX();
                  int mouseY = e.getY();
                  setToolTipText(null); 
                  
                  int barWidth = 50;
                  int barSpacing = 70;
                  int startX = 100;
                  
                  for (int j = 0; j < num.length; j++) {
                     int h = 300 * num[j] / max;
                     int x = startX + barSpacing * j;
                     int y = 350 - h;
                     
                     if (mouseX >= x && mouseX <= x + barWidth && mouseY >= y && mouseY <= 350) {
                        if (jc.getSelectedIndex() == 1) { // 어려운 문제 선택 시
                           
                           //String imagePath = num[j] +".jpg";
                           //String imagePath = "file:/question/5.jpg";
//                               String imagePath = "file:" + System.getProperty("user.dir") + "/datafiles/5.jpg";
                               	//String tooltipText = "<html><img src='" + imagePath + "' style='width: 400px; height: 400px; object-fit: contain;'></html>";
                        		String tooltipText = "<html><img src='" + num[j] +".jpg" + "' style='width: 400px; height: 400px; object-fit: contain;'></html>";
                        		setToolTipText(tooltipText);

                            } else {
                                setToolTipText(name[j] + ": " + num[j] + "개");
                            }
                            break;
                     }
                  }
               }
            });
         }
         
         @Override
         public void paint(Graphics g) {
            try {
               if (jc.getSelectedIndex() == 0) {
                  rs = db.rs("SELECT c.cno,count(c.tno),t.name,t.uni,t.grade FROM catalog c,teacher t where c.tno = t.tno group by c.tno order by count(c.tno) desc,t.name asc limit 5");
               } else {
                  rs = db.rs("SELECT c.cno,count(c.type),c.type FROM catalog c,teacher t where c.tno = t.tno group by c.type order by count(c.type) desc,c.type asc limit 5");
               }
               
               int i = 0;
               while (rs.next()) {
                  num[i] = rs.getInt(2);
                  name[i] = rs.getString(3);
                  if (jc.getSelectedIndex() == 0) {
                     tool[i] = rs.getString(2) + "/" + rs.getString(4) + "/" + rs.getString(5);
                  } else {
                     tool[i] = rs.getString(3);
                  }
                  System.out.println(num[i]);
                  i++;
               }
               
               int p = 100;
               for (int j = 0; j < per.length; j++) {
                  per[j] = p + "%";
                  p = p - 20;
               }
               
               max = num[0];
               
               
               
               int hh = 55;
               for (int j = 0; j < num.length; j++) {
                  
                  int w = 50;
                  int h = 300 * num[j] / max;
                  int x = 100 + 70 * j;
                  int y = 350 - h;
                  
                  
                  g.setColor(col[j]);
                  g.fillRect(x, y, w, h);
                  
                  g.setColor(Color.black);
                  g.drawString(name[j], x + 5, 365);
                  
                  g.drawString(per[j], 30, hh);
                  
                  g.drawLine(75, max + 40, 75, 350);
                  g.drawLine(75, 350,475, 350);
                  
                  hh = hh + 65;
               }
               g.drawString("개수", 30, 35);
               g.setFont(new Font("맑은 고딕",1,10));
               
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
      
      revalidate();
      repaint();
   }


   @Override
   public void windowClosing(WindowEvent e) {
      new b선생님메인();
   }
   
}
