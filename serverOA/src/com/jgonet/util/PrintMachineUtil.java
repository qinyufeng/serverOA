package com.jgonet.util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PrintMachineUtil {
	    //595×842
	    static String dh;                     //单号
	    static String lxr;                   //联系人
	    static String tel;
	    static String date;
	    static String khm;                 //客户名
	    static String add;                   //地址
	    static String js="现金";                     //结算方式
	    static List<Object> kh=new ArrayList<Object>();        //客户信息list
	    static List<Object> sp=new ArrayList<Object>();           //主体list
	    static List<Object> hj=new ArrayList<Object>();             //尾部List
	    public static void setkhls(List<Object> kh){
	        PrintMachineUtil.kh=kh;
	        dh=kh.get(0).toString().trim();
	        khm=kh.get(1).toString().trim();
	        lxr=kh.get(2).toString().trim();
	        tel=kh.get(3).toString().trim();
	        add=kh.get(4).toString().trim();
	        Date d=new Date();
	        date=String.format("%tF",d);
	    }
	    public static void setsp(List<Object> sp){
	        PrintMachineUtil.sp=sp;
	    }
	    public static void sethj(List<Object> hj){
	        PrintMachineUtil.hj=hj;
	    }
	    public  PrintMachineUtil(){
	        JFrame jf=new JFrame();
	        jf.setSize(595,842);
	        Container c=jf.getContentPane();
	        c.add(new Draw());
	        jf.addKeyListener(new KeyListener(){
	            @Override
	            public void keyPressed(KeyEvent e) {
	                // TODO Auto-generated method stub
	                if(e.getKeyCode()==KeyEvent.VK_P){
	                    try {
	                        PrinterJob job = PrinterJob.getPrinterJob();
	                        if (!job.printDialog()){
	                            return;
	                        }else{
	                            job.setPrintable(new Printable() {
	                                public int print(Graphics graphics, PageFormat pageFormat,
	                                        int pageIndex) throws PrinterException {
	                                    if (pageIndex > 0){
	                                        return Printable.NO_SUCH_PAGE;
	                                    }else{
	                                        Graphics2D g2 = (Graphics2D) graphics;
	                                        Font font,font2,font3;
	                                        font=new Font("黑体",Font.PLAIN,20);
	                                        g2.setFont(font);
	                                        g2.setFont(font);
	                                        g2.drawString("*",190,35);//title
	                                        font2=new Font("宋体",Font.PLAIN,10);
	                                        g2.setFont(font2);
	                                        g2.drawString("地址:  电话:  Fax:  ",100,50);  
	                                        font3=new Font("宋体",Font.PLAIN,10);
	                                        g2.setFont(font3);
	                                        g2.drawString("单号:",20,65);g2.drawString("日期:",240,65);g2.drawString("结算:",420,65);
	                                        g2.drawString("客户:",20,85);g2.drawString("联系人:",240,85);g2.drawString("联系电话:",420,85);
	                                        g2.drawString("地址:",20,105);
	                                        g2.drawString(dh,50,65);g2.drawString(date,270,65);g2.drawString(js,445,65);
	                                        g2.drawString(khm,50,85);g2.drawString(lxr,280,85);g2.drawString(tel,470,85);
	                                        g2.drawString(add,50,105);
	                                        List<String> ls=new ArrayList<String>();
	                                        ls.add("序号");ls.add("商品种类");ls.add("商品名称");ls.add("单位");ls.add("折扣");ls.add("单价");ls.add("数量");ls.add("金额");ls.add("备注");
	                                        int n[]=new int[]{0,2,4,10,3,3,4,3,4,4};
	                                        int s=0;
	                                        int x=20;
	                                        int y=115;
	                                        int row=0;
	                                        int count=0;
	                                        List<Object> lsx=new ArrayList<Object>();
	                                        lsx=sp;
	                                        System.out.println(lsx.size());
	                                        for(int i=0;i<lsx.size()/9+3;i++){    //画横线
	                                            y=115+row*18;
	                                            if(i==1){
	                                                for(int j=0;j<ls.size();j++){
	                                                    s=n[j]*14;
	                                                    x=x+s;
	                                                    g2.drawString(ls.get(j),x+3,y-4); //写入表头数据
	                                                }
	                                            }
	                                            s=0;
	                                            x=20;
	                                            if(i>1&&i<lsx.size()/9+2){
	                                                for(int j=0;j<9;j++){
	                                                    s=n[j]*14;
	                                                    x=x+s;
	                                                    System.out.println(j+"    "+x);
	                                                    g2.drawString(lsx.get(j+count*9).toString().trim(),x+3,y-4);    // 写入当行数据
	                                                }
	                                                count++;
	                                            }
	                                            if(i==lsx.size()/9+2){
	                                                g2.drawString(hj.get(0).toString().trim(),51,y-4);
	                                                g2.drawString(hj.get(1).toString().trim(),387,y-4);
	                                                g2.drawString(hj.get(2).toString().trim(),429,y-4);
	                                            }
	                                            g2.drawLine(20,y,538,y);//横线
	                                            row++;
	                                        }
	                                        count=0;
	                                        row=0;
	                                        s=0;
	                                        x=20;
	                                        for(int i=0;i<ls.size()+1;i++){  //画竖线
	                                            s=n[i]*14;
	                                            x=x+s;
	                                            if(i<2||i>5){
	                                                g2.drawLine(x,115,x,y);//竖线
	                                            }else{
	                                                g2.drawLine(x,115,x,y-18);//竖线
	                                            }
	                                            if(i==0){
	                                                g2.drawString("合计",x+3,y-4);
	                                            }
	                                        }
	                                        System.out.println(y);
	                                        g2.drawString("开单人:                                   经手人:                          收货人:",20,y+20);
	                                        g2.drawString("白色:存根联         红色:记账联      蓝色:收款联         黄色:收货联",20,y+40);
	                                        return Printable.PAGE_EXISTS;
	                                    }
	                                }
	                            });
	                        }
	                        job.setJobName("打印图形");
	                        job.print();
	                    } catch (PrinterException e1) {
	                        e1.printStackTrace();
	                    }
	                }
	            }
	            @Override
	            public void keyReleased(KeyEvent e) {
	                // TODO Auto-generated method stub
	            }
	            @Override
	            public void keyTyped(KeyEvent e) {
	                // TODO Auto-generated method stub
	            }
	        });
	        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        jf.setLocationRelativeTo(null);
	        jf.setVisible(true);
	    }
	    public static void main(String[] args) {
	        new PrintMachineUtil();
	    }
	    class Draw extends JPanel{
	        /**
	         * 
	         */
	        private static final long serialVersionUID = 1L;
	        public void paint(Graphics g){
	            super.paint(g);
	            Font font,font2,font3;
	            font=new Font("黑体",Font.PLAIN,20);
	            Graphics2D g2=(Graphics2D) g;
	            g2.setFont(font);
	            g2.drawString("*",170,35);//标题
	            font2=new Font("宋体",Font.PLAIN,10);
	            g2.setFont(font2);
	            g2.drawString("地址:   电话: Fax:",100,50);
	            font3=new Font("宋体",Font.PLAIN,10);
	            g2.setFont(font3);
	            g2.drawString("单号:",20,65);g2.drawString("日期:",240,65);g2.drawString("结算:",420,65);
	            g2.drawString("客户:",20,85);g2.drawString("联系人:",240,85);g2.drawString("联系电话:",420,85);
	            g2.drawString("地址:",20,105);
	            g2.drawString(dh,50,65);g2.drawString(date,270,65);g2.drawString(js,445,65);
	            g2.drawString(khm,50,85);g2.drawString(lxr,280,85);g2.drawString(tel,470,85);
	            g2.drawString(add,50,105);
	            List<String> ls=new ArrayList<String>();
	            ls.add("序号");ls.add("商品种类");ls.add("商品名称");ls.add("单位");ls.add("折扣");ls.add("单价");ls.add("数量");ls.add("金额");ls.add("备注");
	            int n[]=new int[]{0,2,4,10,3,3,4,3,4,4};
	            int s=0;
	            int x=20;
	            int y=115;
	            int row=0;
	            int count=0;
	            List<Object> lsx=new ArrayList<Object>();
	            lsx=sp;
	            System.out.println(lsx.size());
	            for(int i=0;i<lsx.size()/9+3;i++){    //画横线
	                y=115+row*18;
	                if(i==1){
	                    for(int j=0;j<ls.size();j++){
	                        s=n[j]*14;
	                        x=x+s;
	                        g2.drawString(ls.get(j),x+3,y-4); //写入表头数据
	                    }
	                }
	                s=0;
	                x=20;
	                if(i>1&&i<lsx.size()/9+2){
	                    for(int j=0;j<9;j++){
	                        s=n[j]*14;
	                        x=x+s;
	                        System.out.println(j+"    "+x);
	                        g2.drawString(lsx.get(j+count*9).toString().trim(),x+3,y-4);    // 写入当行数据
	                    }
	                    count++;
	                }
	                if(i==lsx.size()/9+2){
	                    g2.drawString(hj.get(0).toString().trim(),51,y-4);
	                    g2.drawString(hj.get(1).toString().trim(),387,y-4);
	                    g2.drawString(hj.get(2).toString().trim(),429,y-4);
	                }
	                g2.drawLine(20,y,538,y);//横线
	                row++;
	            }
	            count=0;
	            row=0;
	            s=0;
	            x=20;
	            for(int i=0;i<ls.size()+1;i++){  //画竖线
	                s=n[i]*14;
	                x=x+s;
	                if(i<2||i>5){
	                    g2.drawLine(x,115,x,y);//竖线
	                }else{
	                    g2.drawLine(x,115,x,y-18);//竖线
	                }
	                if(i==0){
	                    g2.drawString("合计",x+3,y-4);
	                }
	            }
	            System.out.println(y);
	            g2.drawString("开单人:                                   经手人:                          收货人:",20,y+20);
	            g2.drawString("白色:存根联         红色:记账联      蓝色:收款联         黄色:收货联",20,y+40);
	        }
	    }
	}
