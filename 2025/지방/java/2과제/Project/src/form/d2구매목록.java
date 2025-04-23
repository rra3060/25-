package form;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import aframe.aframe;
import jdbc.*;

public class d2구매목록 extends aframe {

    JComboBox jc[] = new JComboBox[2];

    String tn[] = "번호,이미지,상품명,구매사이즈,구매날짜,가격".split(",");

    int puno[];

    public d2구매목록() {
        dcard.p[1].add(p0 = new JPanel(new FlowLayout(0, 0, 10)), n);
        p0.add(jl = new JLabel("상품명"));
        sz(jl, 70, 30);
        ft(jl, 1, 15);
        p0.add(jt = new JTextField());
        sz(jt, 230, 30);
        p0.add(jl = new JLabel("월", 0));
        sz(jl, 30, 30);
        ft(jl, 1, 15);
        p0.add(jc[0] = new JComboBox<>());
        sz(jc[0], 100, 30);
        p0.add(jl = new JLabel("~ 월", 0));
        sz(jl, 50, 30);
        ft(jl, 1, 15);
        p0.add(jc[1] = new JComboBox<>());
        sz(jc[1], 100, 30);
        p0.add(jl = new JLabel(""));
        sz(jl, 30, 30);
        p0.add(jb = new JButton("검색"));
        sz(jb, 100, 30);
        bl(jb);

        for (int i = 0; i < jc.length; i++) {
            for (int j = 1; j <= 12; j++) {
                jc[i].addItem(j);
            }
        }
        jc[1].setSelectedIndex(11);

        dcard.p[1].add(jsp = new JScrollPane(jta = new JTable(dtm = new DefaultTableModel(null, tn)) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 1 ? ImageIcon.class : Object.class;
            }
        }));
        jta.setRowHeight(120);
        try {
            rs = db.rs("select p.p_no, p_img, p_name, pu.p_s, pu.p_m, pu.p_l, pu.p_xl, pu_date, p_price, sa_sale, if(start_date <= pu_date and end_date >= pu_date, 1, 0), pu_no from purchase pu, productlist p left join salelist s on s.p_no = p.p_no where pu.p_no = p.p_no and pu.u_no = " + vq.uno);
            rs.last();
            puno = new int[rs.getRow()];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                int count = (rs.getInt(7) + rs.getInt(4) + rs.getInt(5) + rs.getInt(6));
                int amount = rs.getInt(11) == 1 ? rs.getInt(9) * (1 - rs.getInt(10) / 100) * count : rs.getInt(9) * count;
                dtm.addRow(new Object[] { rs.getInt(1), blob(rs.getBinaryStream(2), 100 ,100), rs.getString(3), rs.getInt(4) + "," + rs.getInt(5) + "," + rs.getInt(6) + "," + rs.getInt(7), rs.getString(8), def.format(amount) + "원" });
                puno[i] = rs.getInt(12);
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
        sz(jsp, 0, 350);
    }
}
