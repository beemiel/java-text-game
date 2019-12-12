package gameex;

import javax.swing.JOptionPane;


//팝업창
public class 쓰레드실행확인 {
	public static void main(String[] args) {

//		int answer = (int)(Math.random()*100)+1;
//		int input = 0;
		String temp = "";
//		int count = 0;
		
		do {
//            count++;
            temp = JOptionPane.showInputDialog("15�� ���� ���� ���ڸ� �Է����ּ���." + "\"abcdefghijklmnop\"");
            
            temp = JOptionPane.showInputDialog("10초내에 문자를 입력해주세요." + "\"abcdefghijk\"");

            // ����ڰ� ��ҹ�ư�� �����ų� -1�� �Է��ϸ� do-while���� �����.
            if(temp==null || temp.equals("-1")) break;
            
            System.out.println("�Է°� : "+temp);
            
            if(temp.equals("abcdefghijklmnop")) {
            	System.out.println("Ż��!");
            	break;
            } else if(temp==null || temp.equals("-1")) {
            	System.out.println("���!");
            	break;
            } else {
            	System.out.println("���!");
            	break;
            }
            
            // ������Է��� ���ڿ��� �޾ƿ��� ������ int�� ��ȯ�� �־���Ѵ�.
//            input = Integer.parseInt(temp);
		} while(true);
		
	}
}
