package form;

import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class d3장바구니 extends aframe {

    String tn[] = "번호,이미지,상품명,구매 사이즈,판매자명,가격".split(",");

    int pno[];

    public d3장바구니() {
        dcard.p[2].add(p0 = new JPanel(new FlowLayout(2)), n);
        p0.add(jb = new JButton("구매하기"));
        sz(jb, 100, 30);
        bl(jb);

        dcard.p[2].add(jsp = new JScrollPane(jta = new JTable(dtm = new DefaultTableModel(null, tn)) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 1 ? ImageIcon.class : Object.class;
            }
        }));
        jta.setRowHeight(120);
        try {
            rs = db.rs("select p.p_no, p_img, p_name, s.p_s, s.p_m, s.p_l, s.p_xl, p_price, (p_price * (1 - sa_sale / 100)), if(start_date <= '" + daf.format(new Date()) + "' and end_date >= '" + daf.format(new Date()) + "', 1, 0), u_name from shoppingbasket s, user u, productlist p left join salelist sa on sa.p_no = p.p_no where s.p_no = p.p_no and u.u_no = p.u_no and s.u_no = " + vq.uno);
            rs.last();
            pno = new int[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                int count = (rs.getInt(7) + rs.getInt(4) + rs.getInt(5) + rs.getInt(6));
                int amount = rs.getInt(10) == 1 ? rs.getInt(9) * count : rs.getInt(8) * count;
                dtm.addRow(new Object[] { rs.getInt(1), blob(rs.getBinaryStream(2), 100, 100), rs.getString(3), rs.getInt(4) + "," + rs.getInt(5) + "," + rs.getInt(6) + "," + rs.getInt(7), rs.getString(11), def.format(amount) + "원" });
                pno[i] = rs.getInt(1);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cell.setHorizontalAlignment(0);
        int w[] = { 80, 100, 200, 80, 80, 80 };
        for (int i = 0; i < jta.getColumnCount(); i++) {
            if (i != 1) {
                jta.getColumnModel().getColumn(i).setCellRenderer(cell);
            }
            jta.getColumnModel().getColumn(i).setPreferredWidth(w[i]);
        }
        sz(jsp, 0, 0);
    }
}
