import java.awt.Color;  
import java.awt.GridLayout;  
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener; 
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JPanel;  
import javax.swing.JTextField;  
import java.math.BigDecimal; //��������� 
import java.util.Collections;
import java.util.Stack;
//import java.util.Scanner;//
import java.awt.*;
/** 
 * һ����������֧�ּ򵥵ļ���
 */  
public class Calculator2 extends JFrame implements ActionListener {  
	/***************************************************************************************************************/
	/********��������ذ������Ƽ���ť����*******/

	
    /*�򵥼������ϵļ�����ʾ���� */  
    private final String[] KEYS = { "7", "8", "9", "+" , "4", "5", "6",  
            "-", "1", "2", "3", "*", "0", ".", "=", "/" };  
    /*�򵥼������ϵ�����ȹ��ܼ�����ʾ���� */  
    private final String[] COMMAND = { "(",")", "C","��" };  //�����õ�Ӣ�İ棬�������ʿɸ�
    /*��ѧ�������ϵĺ���ͼ����ʾ����*/ 
    private final String[] FUNCTION = { "1/x", "x^2", "x^y", "x!", "��" ,"��(n&x)",
    		"sin","cos","tan","In","log","��","BMI/BFR","e^x","e"};
    /*�����������⹦��ͼ����ʾ����*/
    private final String[] SPECIAL_FUNTION = { "Cov", "��","s^2", "ave","$" };
    
    /*���������ť����ĳ��ȣ��������Ǵ�����Ӧ������*/
    int keysLen=KEYS.length;//���ڴ��KEYS�����еİ�ť����
    int commandLen=COMMAND.length;//���ڴ��COMMAND�����еİ�ť����
    int functionLen=FUNCTION.length;//���ڴ��FUNCTION�����еİ�ť����
    int special_functionLen=SPECIAL_FUNTION.length;//���ڴ��SPECIAL_FUNTION �����еİ�ť����
    
    /*�򵥼������ϼ��İ�ť */  
    private JButton keys[] = new JButton[keysLen];  
    /* �������ϵĹ��ܼ��İ�ť */  
    private JButton commands[] = new JButton[commandLen];  
    /*�������Ϲ���ͼ����ʾ����*/  
    private JButton function[] = new JButton[functionLen]; 
    /*�������ϳ��ú����İ�ť*/
    private JButton special_function[] = new JButton[special_functionLen]; 
    /*�������ı��� */  
    private JTextField resultText = new JTextField("");     //ȥ����Ĭ�ϵ��� 
  
    /*****************************************************************************************************/
    /****/
    
    private Stack<String> postfixStack  = new Stack<String>();//��׺ʽջ
    private Stack<Character> opStack  = new Stack<Character>();//�����ջ
    private int [] operatPriority  = new int[] {0,3,2,1,-1,1,0,2};//���������ASCII��-40����������������ȼ� 
    
   /** 
    ************ ���캯�� *************************************************************************
    *********************/  
    public Calculator2() {  
        super("MyCalculator");  
        // ��ʼ��������  
        init();  
        // ���ü������ı�����ɫ  
        this.setBackground(Color.LIGHT_GRAY);  
        this.setTitle("������");  
        // ����Ļ(500, 300)���괦��ʾ������  
        this.setLocation(500, 300);  
        // �����޸ļ������Ĵ�С  
        this.setResizable(false);             //ע�⣺�����С���Ըı� 
        // ʹ�������и������С����  
        this.pack();  
    }  
  
    /** 
     *******��ʼ�������� ***************************************************************************************** 
     */  
    private void init() {   	
    	
        // �ı����е����ݲ����Ҷ��뷽ʽ  
        resultText.setHorizontalAlignment(JTextField.RIGHT);  
        // �����޸Ľ���ı���  
        resultText.setEditable(true);            //����ı���ʵ��
        // �����ı��򱳾���ɫΪ��ɫ  
        resultText.setBackground(Color.white);  
  
        // ��ʼ�����������������֡�����ȼ��İ�ť����������һ��������  
        JPanel calckeysPanel = new JPanel();  
        // �����񲼾�����4�У�4�е� ��������֮���ˮƽ������Ϊ3�����أ���ֱ������Ϊ3������  
        calckeysPanel.setLayout(new GridLayout(4, 4, 3, 3));  
        for (int i = 0; i < keysLen; i++) {  
            keys[i] = new JButton(KEYS[i]);  
            calckeysPanel.add(keys[i]);  
            keys[i].setForeground(Color.blue);  
        }  
        // ��������ú�ɫ��ʾ������������ɫ��ʾ  
        keys[3].setForeground(Color.red);  
        keys[7].setForeground(Color.red);  
        keys[11].setForeground(Color.red);  
        keys[15].setForeground(Color.red);   
  
        // ��ʼ���������ɾ�ȹ��ܼ������ú�ɫ��ʾ�������ܼ�����һ��������  
        JPanel commandsPanel = new JPanel();  
        // �����񲼾�����1�У�3�е���������֮���ˮƽ������Ϊ3�����أ���ֱ������Ϊ3������  
        commandsPanel.setLayout(new GridLayout(1, 3, 3, 3));  
        for (int i = 0; i < commandLen; i++) {  
            commands[i] = new JButton(COMMAND[i]);  
            commandsPanel.add(commands[i]);  
            commands[i].setForeground(Color.red);  
        }  
  
        // ��ʼ����ѧ�������������ܼ����ú�ɫ��ʾ���������������ܼ�����һ��������  
        JPanel functionsPanel = new JPanel();  
        // �����񲼾ֹ�������5�У�3�е���������֮���ˮƽ������Ϊ3�����أ���ֱ������Ϊ3������  
        functionsPanel.setLayout(new GridLayout(5, 3, 3, 3));  
        for (int i = 0; i < functionLen; i++) {  
            function[i] = new JButton(FUNCTION[i]);  
            functionsPanel.add(function[i]);  
            function[i].setForeground(Color.red);  
        }  
        
        // ��ʼ�����������⺯�����ܼ����ú�ɫ��ʾ���������������ܼ�����һ��������  
        JPanel special_functionsPanel = new JPanel();  
        // �����񲼾ֹ�������5�У�3�е���������֮���ˮƽ������Ϊ3�����أ���ֱ������Ϊ3������  
        special_functionsPanel.setLayout(new GridLayout(5, 3, 3, 3));  
        for (int i = 0; i < special_functionLen; i++) {  
        	special_function[i] = new JButton(SPECIAL_FUNTION[i]);  
        	special_functionsPanel.add(special_function[i]);  
            function[i].setForeground(Color.red);  
        } 
  
        // ������м����������岼�֣���calckeys��commands������ڼ��������в���  
        // ���ı�����ڱ�������functions��special_functions������ڼ�������������  
  
        // �½�һ����Ļ��壬�����潨����command��calckeys������ڸû�����  
        JPanel panel1 = new JPanel();  
        // ������ñ߽粼�ֹ����������������֮���ˮƽ�ʹ�ֱ�����ϼ����Ϊ3����  
        panel1.setLayout(new BorderLayout(3, 3));  
        panel1.add("North", commandsPanel);  
        panel1.add("South", calckeysPanel);
          // ����һ��������ı���  
        JPanel top = new JPanel();  
        top.setLayout(new BorderLayout());  
        top.add("Center", resultText); 
        //�½�һ����Ļ��壬�����潨����functions��special_functions������ڸû�����  
        JPanel panel2 = new JPanel();  
        // ������ñ߽粼�ֹ����������������֮���ˮƽ�ʹ�ֱ�����ϼ����Ϊ3����  
        panel2.setLayout(new BorderLayout(3, 3));  
        panel2.add("East", functionsPanel);  
        panel2.add("West", special_functionsPanel);
 
  
        // ���岼��  
        getContentPane().setLayout(new BorderLayout(3, 5));  
        getContentPane().add("North", top);  
        getContentPane().add("Center", panel1);  
        getContentPane().add("West", panel2);  
        
        /***************Ϊ����ť����¼�������***********************************/   
        // ��ʹ��ͬһ���¼����������������󡣱������������implemennts ActionListener  
        for (int i = 0; i < keysLen; i++) {  
            keys[i].addActionListener(this);  
        }  
        for (int i = 0; i < commandLen; i++) {  
            commands[i].addActionListener(this);  
        }  
        for (int i = 0; i < functionLen; i++) {  
            function[i].addActionListener(this);  
        } 
        for (int i = 0; i < special_functionLen; i++) {  
            special_function[i].addActionListener(this);  
        } 
    }    
    /** 
     *********�����¼� *****************************************************************************************************
     */  
    public void actionPerformed(ActionEvent e) {  
        // ��ȡ�¼�Դ�ı�ǩ  
    	double result = 0.0;
        String label = e.getActionCommand();
        if (label.equals("=")) {  
//            // �û�����"="��  ,���������������
        	result = calculate(resultText.getText());
        	resultText.setText(String.valueOf(result));
        	
        }else if (label.equals(COMMAND[2])) {  
            // �û�����"C"��  ����ʼ��������
            resultText.setText("0");  //������������� 
        }else if (label.equals(COMMAND[3])) {  
        	//�û����ˡ������˻ؼ������ı������һ���ַ�ȥ��
             handleBackspace();
        }
        else if ("0123456789.()+-*/".indexOf(label) >= 0) {  
            // �û���������Ҫ����ı��ʽ
            resultText.setText(resultText.getText()+label);
  
        }  
        else{
        	//ȱʡ�����������������ӹ���
        } 
    }  
  
    /** 
     * ����Backspace�������µ��¼� 
     */  
    private void handleBackspace() {  
        String text = resultText.getText();  
        int i = text.length();  
        if (i > 0) {  
            // �˸񣬽��ı����һ���ַ�ȥ��  
            text = text.substring(0, i - 1);  
            if (text.length() == 0) {  
                // ����ı�û�������ݣ����ʼ���������ĸ���ֵ  
                resultText.setText("0");  
            } else {  
                // ��ʾ�µ��ı�  
                resultText.setText(text);  
            }  
        }  
    } 
    /** 
     * ����C�������µ��¼� 
     */  
    private void handleC() {  
        // ��ʼ���������ĸ���ֵ  
        resultText.setText("0");    
    }
    /******/
    /**
     * ���ո����ı��ʽ����
     * @param expression Ҫ����ı��ʽ����:5+12*(3+5)/7
     * @return
     */
    public double calculate(String expression) {
        Stack<String> resultStack  = new Stack<String>();
        prepare(expression);
        Collections.reverse(postfixStack);//����׺ʽջ��ת
        String firstValue  ,secondValue,currentValue;//�������ĵ�һ��ֵ���ڶ���ֵ�����������
        while(!postfixStack.isEmpty()) {
            currentValue  = postfixStack.pop();
            if(!isOperator(currentValue.charAt(0))) {//����������������������ջ��
                resultStack.push(currentValue);
            } else {//������������Ӳ�����ջ��ȡ����ֵ�͸���ֵһ���������
                 secondValue  = resultStack.pop();
                 firstValue  = resultStack.pop();
                 String tempResult  = calculate(firstValue, secondValue, currentValue.charAt(0));
                 resultStack.push(tempResult);
            }
        }
        return Double.valueOf(resultStack.pop());
    }
    /**
     * ����׼���׶ν����ʽת����Ϊ��׺ʽջ
     * @param expression
     */
    private void prepare(String expression) {
        opStack.push(',');//���������ջ��Ԫ�ض��ţ��˷������ȼ����
        char[] arr  = expression.toCharArray();
        int currentIndex  = 0;//��ǰ�ַ���λ��
        int count = 0;//�ϴ����������������������������ַ��ĳ��ȱ��ڻ���֮�����ֵ
        char currentOp  ,peekOp;//��ǰ��������ջ��������
        for(int i=0;i<arr.length;i++) {
            currentOp = arr[i];
            if(isOperator(currentOp)) {//�����ǰ�ַ��������
                if(count > 0) {
                    postfixStack.push(new String(arr,currentIndex,count));//ȡ���������֮�������
                }
                peekOp = opStack.peek();
                if(currentOp == ')') {//�����������������ջ�е�Ԫ���Ƴ�����׺ʽջ��ֱ������������
                    while(opStack.peek() != '(') {
                        postfixStack.push(String.valueOf(opStack.pop()));
                    }
                    opStack.pop();
                } else {
                    while(currentOp != '(' && peekOp != ',' && compare(currentOp,peekOp) ) {
                        postfixStack.push(String.valueOf(opStack.pop()));
                        peekOp = opStack.peek();
                    }
                    opStack.push(currentOp);
                }
                count = 0;
                currentIndex = i+1;
            } else {
                count++;
            }
        }
        if(count > 1 || (count == 1 && !isOperator(arr[currentIndex]))) {//���һ���ַ��������Ż��������������������׺ʽջ��
            postfixStack.push(new String(arr,currentIndex,count));
        } 
        
        while(opStack.peek() != ',') {
            postfixStack.push(String.valueOf( opStack.pop()));//��������ջ�е�ʣ���Ԫ����ӵ���׺ʽջ��
        }
    }
    /**
     * �ж��Ƿ�Ϊ��������
     * @param c
     * @return
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' ||c == ')';
    }
    
    /**
     * ����ASCII��-40���±�ȥ�����������ȼ�
     * @param cur
     * @param peek	
     * @return
     */
    public  boolean compare(char cur,char peek) {// �����peek���ȼ�����cur������true��Ĭ�϶���peek���ȼ�Ҫ��
        boolean result  = false;
        if(operatPriority[(peek)-40] >= operatPriority[(cur) - 40]) {
           result = true;
        }
        return result;
    }
    
    /**
     * ���ո��������������������
     * @param firstValue
     * @param secondValue
     * @param currentOp
     * @return
     */
    private String calculate(String firstValue,String secondValue,char currentOp) {
    	double fV = Double.valueOf(firstValue).doubleValue();
    	double sV = Double.valueOf(secondValue).doubleValue();
        String result  = "";
        switch(currentOp) {
            case '+':
                result = String.valueOf(fV+sV);
                break;
            case '-':
                result = String.valueOf(fV-sV);
                break;
            case '*':
                result = String.valueOf(fV*sV);
                break;
            case '/':
                result = String.valueOf(fV/sV);
                break;
        }
        return result;
    }
    
//  
//    /** 
//     * �������ּ���С����������µ��¼� 
//     *  
//     * @param key 
//     */  
//    private void handleNumber(String key) {  
//        if (firstDigit) {  
//            // ����ĵ�һ������  
//            resultText.setText(key);  
//        } else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)) {  
//            // �������С���㣬����֮ǰû��С���㣬��С���㸽�ڽ���ı���ĺ���  
//            resultText.setText(resultText.getText() + ".");  
//        } else if (!key.equals(".")) {  
//            // �������Ĳ���С���㣬�����ָ��ڽ���ı���ĺ���  
//            resultText.setText(resultText.getText() + key);  
//        }  
//        // �Ժ�����Ŀ϶����ǵ�һ��������  
//        firstDigit = false;  
//    }  
//  
  
//  
//    /** 
//     * ����������������µ��¼� 
//     *  
//     * @param key 
//     */  
//    private void handleOperator(String key) {  
//        if (operator.equals("/")) {  
//        	// ��������  
//            // �����ǰ����ı����е�ֵ����0  
//            if (getNumberFromText() == 0.0) {  
//                // �������Ϸ�  
//                operateValidFlag = false;  
//                resultText.setText("��������Ϊ��");  
//            } else {  
//                resultNum /= getNumberFromText();  
//                resultText.setText("just for����");  
//
//            }   
//       }else if (operator.equals("1/x")) {  
//        // ��������  
//            if (resultNum == 0.0) {  
//                // �������Ϸ�  
//                operateValidFlag = false;  
//                resultText.setText("��û�е���");  
//            } else {  
//                resultNum = 1 / resultNum;
//                resultText.setText(String.valueOf(resultNum));  
//            }  
//       }else if (operator.equals("+")) {  
//        // �ӷ�����  
//            resultNum += getNumberFromText();  
//        } else if (operator.equals("-")) {  
//            // ��������  
//            resultNum -= getNumberFromText();  
//        } else if (operator.equals("*")) {  
//            // �˷�����  
//            resultNum *= getNumberFromText();  
//        } 
//        else if (operator.equals("��")) {  
//            // ƽ��������  
//            resultNum = Math.sqrt(resultNum);  
//        } 
//        else if (operator.equals("x!")) {  
//            // �׳�����  
//        	resultNum = factorial(resultNum);
//        } 
////        else if (operator.equals("%")) {  
////            // �ٷֺ����㣬����100  
////            resultNum = resultNum / 100;  
////        } 
////        else if (operator.equals("+/-")) {  
////             // ������������  
////            resultNum = resultNum * (-1);  
////        } 
//        else if (operator.equals("=")) {  
//            // ��ֵ����   
//            resultNum = getNumberFromText();  
//        }  
//        if (operateValidFlag) {  
//            // ˫���ȸ�����������  
////            long t1;  
////            double t2;  
////            t1 = (long) resultNum;  
////            t2 = resultNum - t1;  
////            if (t2 == 0) {  
//                resultText.setText(String.valueOf(resultNum));  
////            } else {  
////                resultText.setText(String.valueOf(resultNum));  
////            }  
//        }  
//        // ����������û����İ�ť  
//        operator = key;  
//        firstDigit = true;  
//        operateValidFlag = true;  
//    }  
    
    
    /**
     * ���㺯��************************************************************
     */
    /*����׳�*/
 
//    private static double factorial(double factorial) {
//            if (factorial == 1) {
//    	       return 1;
//    	    }
//    	    return factorial * factorial(factorial - 1);
//    }
 
    
    /** 
     * �ӽ���ı����л�ȡ���� 
     *  
     * @return 
     */  
    private Double getNumberFromText() {  
        double result = 0;  
        try {  
            result = Double.valueOf(resultText.getText()).doubleValue();  
        } catch (NumberFormatException e) {  
        }          return result;  
    }  
  /********���࣬����һ��Calculator2����****************************************************************************************************/
    public static void main(String args[]) {  
        Calculator2 calculator = new Calculator2();  
        calculator.setVisible(true);  
        calculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
}  
