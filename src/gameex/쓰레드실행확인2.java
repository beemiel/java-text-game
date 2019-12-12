package gameex;


import java.util.Scanner;




public class 쓰레드실행확인2 {
	public static void main(String[] args) {
		System.out.println("왜 안나와");
		
		
		Scanner scanner = new Scanner(System.in);
		String a = scanner.nextLine();
		
		if(a.equals("안녕")) {
			System.out.println("gg");
		} else {
			System.out.println("잘못입력했습니다.");
		}
		
		System.out.println("왜 안나와");
	}
}

