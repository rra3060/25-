package form;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import aframe.aframe;
import jdbc.db;
import jdbc.vq;

public class b�α��� extends aframe {
	
	String ln[] = "���̵�,��й�ȣ".split(",");
	JTextField jt[] = new JTextField[2];

	public b�α���() {
		fs("�α���");
		setSize(400, 200);

		np.setLayout(new FlowLayout());
		np.add(jl = new JLabel("�α���"));
		ft(jl, 1, 30);

		cp.setLayout(new GridLayout(2, 1, 5, 5));
		for (int i = 0; i < jt.length; i++) {
			cp.add(jt[i] = new JTextField(15));
		}
		emp(cp, 10, 50, 30, 10);

		wp.setLayout(new GridLayout(2, 1));
		for (int i = 0; i < jt.length; i++) {
			wp.add(jl = new JLabel(ln[i]));
		}
		emp(wp, 0, 20, 20, 0);

		ep.setLayout(new FlowLayout());
		ep.add(jb = new JButton("�α���"));
		sz(jb, 70, 80);
		bl(jb);
		jb.addActionListener(this);

		jt[0].setText("user01");
		jt[1].setText("user01!");

		sh();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jb)) {
			String id = jt[0].getText().trim();
			String pw = jt[1].getText().trim();

			if (id.isEmpty() || pw.isEmpty()) {
				wmsg("��ĭ�� �ֽ��ϴ�.");
				return;
			}
			try {
				rs = db.rs("SELECT * FROM user WHERE u_id = '" + id + "' AND u_pw = '" + pw + "'");
				if (rs.next()) {
					vq.uno = rs.getInt(1);
					vq.uname = rs.getString(4);
					vq.price = rs.getInt(5);
					imsg(vq.uname + "�� ȯ���մϴ�.");
					dispose();
					new a����();
				} else {
					wmsg("���̵� �Ǵ� ��й�ȣ�� �ٽ� Ȯ�����ּ���.");
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
