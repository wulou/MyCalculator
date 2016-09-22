//myCalculator.java
 import java.awt.*;
 import java.awt.event.*;

public class myCalculator extends Frame implements ActionListener{//����ʵ��Action�¼��������ӿ�
	int flag=0;
	double x;  //��������ĵ�1��������
	String s=new String("");//��������ĵ�2��������
	Panel p1,p2,p3;
	Label label;
	TextField text;
	Button bpoint,bclean,badd,bsbb,bmul,bdiv,beq;
	Button[] b=new Button[10];
	
	public myCalculator(){ //���췽������ƽ���
		  super("My Calculator");//��������
			
	   /*�ڹ��췽���н��н������*/
	      p1=new Panel();//�ı������ڵ�panel
		  p2=new Panel();//���ְ�ť���ڵ�panel
		  p3=new Panel();//��������ڵ�panel
		  setLayout(new FlowLayout());  //������������Ľ��沼��Ϊ��ʽ
		  p1.setLayout(new FlowLayout()); //�ı������
		  p2.setLayout(new GridLayout(3,4));  //�������ּ����Ϊ4x3�����񲼾�
		  p3.setLayout(new GridLayout(4,1)); //��������
		  label=new Label("���׼����");
		  text=new TextField(12);//��ʾ������̺ͽ�����ı���
	 	  bclean=new Button("���");//����ı���İ�ť
		  add(label);
		  
		  this.setVisible(true);
			
		/*���ı������հ�ť���ڵ�һ��panel��*/
	      p1.add(text);
		  p1.add(bclean);
		  bclean.addActionListener(this);
	
	               
			
		/*���������ְ�ť��"."��"="���ڵڶ���panel��*/
	 	  for(int i=0;i<10;i++){
		    b[i]=new Button(Integer.toString(i));
		    p2.add(b[i]);
		    b[i].addActionListener(this);  //Ϊ���ְ�ťb[i]ע�������
			}
	     bpoint=new Button(".");//С���㰴ť
		  beq=new Button("=");//�ȺŰ�ť
		  p2.add(bpoint);
		  p2.add(beq);
		  bpoint.addActionListener(this);
	      beq.addActionListener(this);
	       
	      	/*��������Ű�ť���ڵ�����panel��*/
	     badd=new Button("+");//�ӷ���ť
	     bsbb=new Button("-");//������ť
	     bmul=new Button("*");//�˷���ť
	     bdiv=new Button("/");//������ť
	     p3.add(badd);
		  p3.add(bsbb);
		  p3.add(bmul);
		  p3.add(bdiv);
		 	
	    /*Ϊ�������ťע�������*/
		  badd.addActionListener(this);
		  bsbb.addActionListener(this);
		  bmul.addActionListener(this);
		  bdiv.addActionListener(this);
		  add(p1);
		  add(p2);
		  add(p3);
		  add(new Label(""));
	  }           
      /*���췽��������������ƽ���*/
	
     public void actionPerformed(ActionEvent e){   //���а�ť��action�¼�������
	    
		/*����������ֻ�����С���㰴ť������¸���,�����ı�������ʾ*/
	 	for(int i=0;i<10;i++){
	        if(e.getSource()==b[i]||e.getSource()==bpoint){ //�жϰ��µ��Ƿ�Ϊ���ְ�ť��С���㰴ť
	        s=s+e.getActionCommand();//������������������,�����浽s
	        text.setText(s);
	        break;
		 }}
	
	
	/*�ж�������Ű�ť�������ϱ�� */
     	if(e.getSource()==badd){ // +��
            x=Double.parseDouble(s);//�����һ����������x 
            flag=1;//��������
            text.setText("+");
            s="";
        }
         if(e.getSource()==bsbb){// -��
            x=Double.parseDouble(s);
            flag=2;
            text.setText("-");
            s="";
        }
        if(e.getSource()==bmul){// *��
            x=Double.parseDouble(s);
            flag=3;
            text.setText("*");
            s="";
        }
        if(e.getSource()==bdiv){// /��
            x=Double.parseDouble(s);
            flag=4;
            text.setText("/");
            s="";
        }
        
	
        if(e.getSource()==bclean){//����ı���
            text.setText("");
            s="";
            flag=0;
        }

        /*����=�ţ����������*/
        if(e.getSource()==beq){
	        switch(flag){
		        case 1: //�ӷ������㷨
		                x=Double.parseDouble(s)+x; //s����ڶ�������������x=s+x;
		                s=String.valueOf(x);//��Doubleת��Ϊstring
		                text.setText(s);
		                break;
		                
		        case 2: //���������㷨
		                x=x-Double.parseDouble(s); //s����ڶ�������������x=s+x;
		                s=String.valueOf(x);//��Doubleת��Ϊstring
		                text.setText(s);
		                break;//����6. �ڴ��������������� 
		               
		                
			    case 3:  //�˷�����
		                x=Double.parseDouble(s)*x; //s����ڶ�������������x=s+x;
		                s=String.valueOf(x);//��Doubleת��Ϊstring
		                text.setText(s);
		                break;//����7. �ڴ�����˷��������
		              
		                
				case 4:    //��������
		                if(Double.parseDouble(s)==0){
		                   text.setText("��������Ϊ0��");break;}
		                x=x/Double.parseDouble(s);
		                s=String.valueOf(x);
		                text.setText(s);
		                break;
		                
           }
        }
 }
 
      //����8; ����ע��������Ĵ����¼�����������,��Ӧ���ڹرղ���
    public static void main(String args[]){
          myCalculator cal=new myCalculator();//
          cal.addWindowListener(new WindowAdapter(){
          	public void windowClosing(WindowEvent e){
     		System.exit(0);
     		}});
          cal.setVisible(true);
          cal.pack();
      }   
}
