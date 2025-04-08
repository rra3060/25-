package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import aframe.aframe;

public class e충전 extends aframe{
	JPanel p[] = new JPanel[5];
	JPanel scp,ssp;
	TitledBorder tb;
	String jbl[] = "충전,취소".split(",");
	JButton jb1[] = new JButton[5];
	JLabel jl1[] = new JLabel[5];
	
	public e충전(){
		fs("충전");
		cp.setLayout(new GridLayout(0,5,10,10));
		sz(cp, 585, 205);
		emp(cp,50,10,10,10);
		for(int i=0;i<p.length;i++) {
			cp.add(p[i] = new JPanel());
			p[i].setBorder(tb = new TitledBorder(new LineBorder(Color.BLACK), ""));
			p[i].setLayout(new BorderLayout());
			p[i].add(scp = new JPanel(new BorderLayout()),c);
			p[i].add(ssp = new JPanel(new BorderLayout()),s);
			scp.setLayout(new FlowLayout());
			scp.add(jl = new JLabel(""));
			jpg2(jl,"충전이미지",(i+1),80,80);
			emp(jl,10,5,5,5);
			ssp.setLayout(new GridLayout(0,3));
			
		}
		
		sp.setLayout(new FlowLayout(2,10,10));
		sz(sp,585,50);
		emp(sp,0,0,0,15);
		for(int i=0;i<jbl.length;i++) {
			sp.add(jb1[i] = new JButton(jbl[i]));
			bl(jb1[i]);
			sz(jb1[i],82,30);
			jb1[i].addActionListener(e->{
				if(e.getSource()==jb1[0]) {
					imsg("충전하시겠습니까?");
				}else if(e.getSource()==jb1[1]) {
					dispose();
				}
			});
		}
		shp();
	}
}