package form;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import aframe.aframe;
import jdbc.*;

public class dcard extends aframe {
    CardLayout card = new CardLayout();
    static JPanel p[] = new JPanel[3];
    String ln[] = "정보수정,구매 목록,장바구니".split(",");
    JLabel lb[] = new JLabel[3];
    BufferedImage buf;

    public dcard() {
        fs("마이페이지");
        emp(pc, 10, 10, 10, 10);

        wp.add(p0 = new JPanel(new BorderLayout()), n);
        emp(p0, 0, 0, 30, 0);
        try {
            rs = db.rs("select * from user where u_no = " + vq.uno);
            if (rs.next()) {
                p0.add(img = new JLabel(blob(rs.getBinaryStream(7), 30, 30)), w);
                p0.add(p1 = new JPanel(new GridLayout(0, 1)));
                emp(p1, 0, 10, 0, 0);
                p1.add(jl = new JLabel(rs.getString(4)));
                p1.add(jl = new JLabel(def.format(rs.getInt(5)) + "원"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        wp.add(p0 = new JPanel(new GridLayout(0, 1, 0, 15)));
        for (int i = 0; i < ln.length; i++) {
            p0.add(lb[i] = new JLabel(ln[i]));
            ft(lb[i], 1, 18);
            int a = i;
            lb[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (int j = 0; j < lb.length; j++) {
                        fk(lb[j], Color.black);
                    }
                    fk(lb[a], Color.blue);
                    card.show(cp, a + "");
                }
            });
        }
        emp(p0, 0, 0, 150, 0);

        cp.setLayout(card);
        for (int i = 0; i < p.length; i++) {
            cp.add(p[i] = new JPanel(new BorderLayout()), i + "");
        }

        new d1정보수정();
        new d2구매목록();
        new d3장바구니();

        card.show(cp, (vq.move - 1) + "");
        fk(lb[0], Color.blue);
        shp();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        new i관리();
    }
    
    public void choseget(int a) {
    	
    }
}
