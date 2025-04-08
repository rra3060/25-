package form;  

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import aframe.aframe;  
import jdbc.*;

public class f리뷰등록 extends aframe {  
    JLabel ST[] = new JLabel[5];  
    JComboBox<String> com[] = new JComboBox[2];  
    String SD[] = "상위 카테고리,하위카테고리".split(",");  

    JTextArea ja = new JTextArea();  
    JLabel NAME = new JLabel();  

    public f리뷰등록() {  
        fs("리뷰 등록");  

        wp.setLayout(new BorderLayout());  
        emp(wp, 5, 5, 5, 5);  
        wp.add(jl = new JLabel());  

        cp.setLayout(new FlowLayout(0));  
        cp.add(NAME = new JLabel(""));  
        ft(NAME, 1, 20);  
        sz(NAME, 350, 30);  

        try {  
            rs = db.rs("SELECT * FROM clothingstore.productlist WHERE p_no = '" + vq.pno + "'");  
            if (rs.next()) {  
                jl.setIcon(blob(rs.getBinaryStream(10), 175, 250));  
                NAME.setText(rs.getString(2));  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  

        cp.add(p0 = new JPanel(new FlowLayout(0)));  
        for (int i = 0; i < ST.length; i++) {  
            p0.add(ST[i] = new JLabel());  
            jpg(ST[i], "별점이미지/2", 30, 30);  
        }  
        sz(p0, 350, 30);  
        
        for (int i = 0; i < SD.length; i++) {  
            cp.add(jl = new JLabel(SD[i]));  
            sz(jl, 85, 35);  
            cp.add(com[i] = new JComboBox<>());  
            sz(com[i], 120, 30);  
            cp.add(jl = new JLabel());  
            sz(jl, 85, 1);  
            bk(com[i], Color.white);  
            com[i].setEditable(false);  
        }  

        try {  
            rs = db.rs("SELECT su.sb_name, ca.c_name FROM clothingstore.productlist pr, clothingstore.subcategory su, clothingstore.category ca WHERE pr.p_no = '" + vq.pno + "' AND su.sb_no = pr.sb_no AND ca.c_no = su.c_no");  
            if (rs.next()) {  
                com[0].addItem(rs.getString(2));  
                com[1].addItem(rs.getString(1));  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  

        cp.add(jl = new JLabel("리뷰 쓰기"));  
        sz(jl, 350, 30);  
        cp.add(ja = new JTextArea());  
        sz(ja, 350, 50);  
        line(ja, Color.black);  

        cp.add(p2 = new JPanel(new FlowLayout(2)));  
        sz(p2, 350, 35);  
        p2.add(jb = new JButton("저장"));  
        bk(jb, Color.blue);  
        fk(jb, Color.white);  

        sz(cp, 375, 275);  
        
        shp();  
    }  
}  