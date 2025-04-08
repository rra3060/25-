package form;

import java.awt.GridLayout;

import javax.swing.JButton;

import aframe.aframe;

//import jdbc.*;
import form.*;

public class i관리 extends aframe{
	JButton jb1[] = new JButton[5];
	String jlist[] = "로그아웃,정보수정,구매목록,장바구니,상품등록".split(",");
	
	public i관리() {
		fs("관리");
		cp.setLayout(new GridLayout(5,0,15,15));
		sz(cp,315,395);
		emp(cp,55,60,45,60);
		for(int i=0;i<jb1.length;i++) {
			cp.add(jb1[i] = new JButton(jlist[i]));
			bl(jb1[i]);
			jb1[i].addActionListener(e->{
				if(e.getSource()==jb1[0]) {
					new b로그인();
				}else if(e.getSource()==jb1[1]) {
					new dcard();
				}else if(e.getSource()==jb1[2]) {
					new dcard();
				}else if(e.getSource()==jb1[3]) {
					
				}else if(e.getSource()==jb1[4]) {
					new g상품등록();
				}
			});
		}
		shp();
	}
}
