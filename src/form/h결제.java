package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import aframe.aframe;

public class h결제 extends aframe{
	JPanel cnp,csp;
	JPanel p[];
	
	public h결제(){
		np.setLayout(new FlowLayout());
		np.add(jck = new JCheckBox("전체 선택"));
		
		cp.add(cnp = new JPanel(new BorderLayout()),n);
		emp(cnp,10,10,10,10);
		cp.add(csp = new JPanel(new BorderLayout()),s);
		emp(csp,10,10,10,10);
		
		cnp.setBorder(tb = new TitledBorder(new LineBorder(Color.BLACK), ""));
		
	}
}
