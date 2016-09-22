//myCalculator.java
 import java.awt.*;
 import java.awt.event.*;

public class myCalculator extends Frame implements ActionListener{//本类实现Action事件监听器接口
	int flag=0;
	double x;  //运算所需的第1个操作数
	String s=new String("");//运算所需的第2个操作数
	Panel p1,p2,p3;
	Label label;
	TextField text;
	Button bpoint,bclean,badd,bsbb,bmul,bdiv,beq;
	Button[] b=new Button[10];
	
	public myCalculator(){ //构造方法，设计界面
		  super("My Calculator");//创建窗体
			
	   /*在构造方法中进行界面设计*/
	      p1=new Panel();//文本框所在的panel
		  p2=new Panel();//数字按钮所在的panel
		  p3=new Panel();//运算符所在的panel
		  setLayout(new FlowLayout());  //设置整个程序的界面布局为流式
		  p1.setLayout(new FlowLayout()); //文本框面板
		  p2.setLayout(new GridLayout(3,4));  //设置数字键面板为4x3的网格布局
		  p3.setLayout(new GridLayout(4,1)); //计算符面板
		  label=new Label("简易计算机");
		  text=new TextField(12);//显示计算过程和结果的文本框
	 	  bclean=new Button("清除");//清空文本框的按钮
		  add(label);
		  
		  this.setVisible(true);
			
		/*把文本框和清空按钮加在第一个panel上*/
	      p1.add(text);
		  p1.add(bclean);
		  bclean.addActionListener(this);
	
	               
			
		/*把所有数字按钮和"."及"="加在第二个panel上*/
	 	  for(int i=0;i<10;i++){
		    b[i]=new Button(Integer.toString(i));
		    p2.add(b[i]);
		    b[i].addActionListener(this);  //为数字按钮b[i]注册监听器
			}
	     bpoint=new Button(".");//小数点按钮
		  beq=new Button("=");//等号按钮
		  p2.add(bpoint);
		  p2.add(beq);
		  bpoint.addActionListener(this);
	      beq.addActionListener(this);
	       
	      	/*把运算符号按钮加在第三个panel上*/
	     badd=new Button("+");//加法按钮
	     bsbb=new Button("-");//减法按钮
	     bmul=new Button("*");//乘法按钮
	     bdiv=new Button("/");//除法按钮
	     p3.add(badd);
		  p3.add(bsbb);
		  p3.add(bmul);
		  p3.add(bdiv);
		 	
	    /*为运算符按钮注册监听器*/
		  badd.addActionListener(this);
		  bsbb.addActionListener(this);
		  bmul.addActionListener(this);
		  bdiv.addActionListener(this);
		  add(p1);
		  add(p2);
		  add(p3);
		  add(new Label(""));
	  }           
      /*构造方法结束，界面设计结束*/
	
     public void actionPerformed(ActionEvent e){   //所有按钮的action事件监听器
	    
		/*如果按下数字或者是小数点按钮，则记下该数,并在文本框中显示*/
	 	for(int i=0;i<10;i++){
	        if(e.getSource()==b[i]||e.getSource()==bpoint){ //判断按下的是否为数字按钮或小数点按钮
	        s=s+e.getActionCommand();//把输入的数字组合起来,并保存到s
	        text.setText(s);
	        break;
		 }}
	
	
	/*判断运算符号按钮，并作上标记 */
     	if(e.getSource()==badd){ // +号
            x=Double.parseDouble(s);//保存第一个操作数到x 
            flag=1;//运算符标记
            text.setText("+");
            s="";
        }
         if(e.getSource()==bsbb){// -号
            x=Double.parseDouble(s);
            flag=2;
            text.setText("-");
            s="";
        }
        if(e.getSource()==bmul){// *号
            x=Double.parseDouble(s);
            flag=3;
            text.setText("*");
            s="";
        }
        if(e.getSource()==bdiv){// /号
            x=Double.parseDouble(s);
            flag=4;
            text.setText("/");
            s="";
        }
        
	
        if(e.getSource()==bclean){//清空文本框
            text.setText("");
            s="";
            flag=0;
        }

        /*若是=号，则进行运算*/
        if(e.getSource()==beq){
	        switch(flag){
		        case 1: //加法运算算法
		                x=Double.parseDouble(s)+x; //s保存第二个操作数，令x=s+x;
		                s=String.valueOf(x);//将Double转换为string
		                text.setText(s);
		                break;
		                
		        case 2: //减法运算算法
		                x=x-Double.parseDouble(s); //s保存第二个操作数，令x=s+x;
		                s=String.valueOf(x);//将Double转换为string
		                text.setText(s);
		                break;//代码6. 在此填入减法运算代码 
		               
		                
			    case 3:  //乘法运算
		                x=Double.parseDouble(s)*x; //s保存第二个操作数，令x=s+x;
		                s=String.valueOf(x);//将Double转换为string
		                text.setText(s);
		                break;//代码7. 在此填入乘法运算代码
		              
		                
				case 4:    //除法运算
		                if(Double.parseDouble(s)==0){
		                   text.setText("除数不能为0！");break;}
		                x=x/Double.parseDouble(s);
		                s=String.valueOf(x);
		                text.setText(s);
		                break;
		                
           }
        }
 }
 
      //代码8; 填入注册匿名类的窗口事件监听器代码,响应窗口关闭操作
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
