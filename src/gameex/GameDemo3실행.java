package gameex;
import java.util.Scanner;

//import javax.swing.JOptionPane;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;




public class GameDemo3실행 {
	
	public static void main(String[] args) {
		
		//맵 별 출현 몬스터
//		숲속 늪 파손된 리조트 파손된 배 동굴 해변
		
//		숲속 - 단데기 뱀 사람? 원숭이
//		늪 - 뱀 악어 박쥐  단데기
//		파손된 배 - 피라냐  쥐 고릴라 사람?
//		파손된 리조트 - 사람? 재규어 머리가 없는 사람? 	한 몸이 된 두 사람? 
//		해변 - 거북이 원숭이  고릴라 피라냐
		
		//쓰레드


		
		
		
		//객체생성
		User user = new User(); //선택한 캐릭터 객체를 저장할 객체
		Animal animal = new Animal(); //랜덤 몬스터의 객체를 저장할 객체 
		Animal randomFish = new Animal();//랜덤으로 생성된 물고기 객체를 저장할 객체
		시스템 시스템 = new 시스템();
		Event event = new Event();
		
		Item item = new Item(); //식사시 선택한 메뉴의 객체 저장할 객체
		장비아이템 무기 = new 장비아이템();
		장비아이템 갑옷 = new 장비아이템();
		
		SpecialMonkey specialmonkey = new SpecialMonkey(); //상점 캐릭터 객체 생성 
		
		// 아이템 객체 (해독제)(String name,boolean 약, boolean 해독)
		// 섭취아이템 (음식,약)(String name, int hpup, int mentalup, int 포만감상승, int 목마름감소,
		// boolean 음식, boolean 약, boolean 해독)
		섭취아이템 나무열매 = new 섭취아이템("나무열매", 20, 20, 20, 10, true, false, false);
		섭취아이템 블랙베리 = new 섭취아이템("블랙베리", 20, 20, 20, 10, true, false, false);
		섭취아이템 스노우베리 = new 섭취아이템("스노우베리", 20, 20, 20, 10, true, false, false);
		섭취아이템 통조림 = new 섭취아이템("통조림", 20, 20, 30, 0, true, false, false);
		섭취아이템 에너지바 = new 섭취아이템("에너지바", 20, 20, 20, 0, true, false, false);
		섭취아이템 약 = new 섭취아이템("약", 50, 50, 0, 0, false, true, false);
		섭취아이템 물 = new 섭취아이템("물", 5, 5, 5, 50, true, false, false);
		섭취아이템 빵 = new 섭취아이템("빵", 20, 20, 40, 0, true, false, false);
		섭취아이템 햄버거세트 = new 섭취아이템("햄버거세트", 30, 30, 50, 50, true, false, false);
		섭취아이템 라면 = new 섭취아이템("라면", 30, 30, 40, 20, true, false, false);
		섭취아이템 음료수 = new 섭취아이템("음료수", 20, 20, 20, 50, true, false, false);

		// 조합섭취
		섭취아이템 구운원숭이꼬치 = new 섭취아이템("구운원숭이꼬치", 20, 20, 40, 10, true, false, false);
		섭취아이템 구운쥐꼬치 = new 섭취아이템("구운쥐꼬치", 10, 10, 20, 10, true, false, false);
		섭취아이템 구운토끼꼬치 = new 섭취아이템("구운토끼꼬치", 20, 20, 30, 10, true, false, false);
		섭취아이템 구운너구리꼬치 = new 섭취아이템("구운너구리꼬치", 20, 20, 30, 10, true, false, false);
		섭취아이템 구운피라미꼬치 = new 섭취아이템("구운피라미꼬치", 10, 10, 15, 5, true, false, false);
		섭취아이템 토끼진흙구이 = new 섭취아이템("토끼진흙구이", 20, 20, 30, 10, true, false, false);
		섭취아이템 육지고기국 = new 섭취아이템("육지고기국", 40, 40, 60, 40, true, false, false);
		섭취아이템 물고기국 = new 섭취아이템("물고기국", 40, 40, 60, 40, true, false, false);
		섭취아이템 체력약 = new 섭취아이템("체력약", 80, 0, 0, 0, false, true, false);
		섭취아이템 정신력약 = new 섭취아이템("정신력약", 0, 80, 0, 0, false, true, false);
		섭취아이템 해독제 = new 섭취아이템("해독제", true, true);
		
		
		//섭취 불가 아이템
		Item 피라냐이빨 = new Item("피라냐이빨");
		Item 뱀허물 = new Item("뱀허물");
		Item 원숭이고기 = new Item("원숭이고기");
		Item 메기수염 = new Item("메기수염");
		Item 열대어지느러미 = new Item("열대어지느러미");
		Item 단데기허물 = new Item("단데기허물");
		Item 뼈 = new Item("뼈");
		Item 박쥐날개 = new Item("박쥐날개");
		Item 악어가죽 = new Item("악어가죽");
		Item 재규어이빨 = new Item("재규어이빨");
		Item 거북이등딱지 = new Item("거북이등딱지");
		Item 쥐고기 = new Item("쥐고기");
		Item 고릴라가죽 = new Item("고릴라가죽");
		Item 피라미 = new Item("피라미");
	
		Item 조개껍질 = new Item("조개껍질");
		Item 나무가지 = new Item("나무가지");
		Item 돌 = new Item("돌");
		Item 동전 = new Item("동전");
		Item 나무덩쿨 = new Item("나무덩쿨");
		Item 나뭇잎 = new Item("나뭇잎");
		Item 야자나뭇잎 = new Item("야자나뭇잎");
		Item 깃털 = new Item("깃털");
		Item 수액 = new Item("수액");
		Item 진흙 = new Item("진흙");
		Item 치커리 = new Item("치커리");
		Item 알로에 = new Item("마리골드");
		Item 허브 = new Item("허브");
		Item 나무판자 = new Item("나무판자");
		Item 테니스라켓 = new Item("테니스라켓");
		Item 녹슨칼 = new Item("녹슨칼");
		Item 냄비 = new Item("냄비");
		Item 토끼고기 = new Item("토끼고기");
		Item 너구리고기 = new Item("너구리고기");
		Item 조명탄 = new Item("조명탄");
		
		Item 원숭이꼬치 = new Item("원숭이꼬치");
		Item 새꼬치 = new Item("새꼬치");
		Item 뱀장어꼬치 = new Item("뱀장어꼬치");
		Item 쥐꼬치 = new Item("쥐꼬치");
		Item 토끼꼬치 = new Item("토끼꼬치");
		Item 너구리꼬치 = new Item("너구리꼬치");
		Item 피라미꼬치 = new Item("피라미꼬치");
		
		
		//장비아이템
		장비아이템 나무갑옷 = new 장비아이템("나무갑옷", 0, 5, 10, 30);
		장비아이템 가죽갑옷 = new 장비아이템("가죽갑옷", 0, 10, 15, 40);
		장비아이템 뼈갑옷 = new 장비아이템("뼈갑옷", 0, 15, 20, 50);
		장비아이템 망가진도끼 = new 장비아이템("망가진도끼", 5, 0, 5, 20);
		장비아이템 망가진톱 = new 장비아이템("망가진톱", 5, 0, 5, 20);
		장비아이템 나무무기 = new 장비아이템("나무무기", 6, 0, 10, 30);
		장비아이템 뼈무기 = new 장비아이템("뼈무기", 10, 0, 20, 50);
		

		

		
//		System.out.println("\n\n"
//	    +"██████╗ ███████╗███████╗███████╗██████╗ ████████╗    ██╗███████╗██╗      █████╗ ███╗   ██╗██████╗     \r\n" 
//		+"██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗╚══██╔══╝    ██║██╔════╝██║     ██╔══██╗████╗  ██║██╔══██╗    \r\n" 
//		+"██║  ██║█████╗  ███████╗█████╗  ██████╔╝   ██║       ██║███████╗██║     ███████║██╔██╗ ██║██║  ██║    \r\n" 
//		+"██║  ██║██╔══╝  ╚════██║██╔══╝  ██╔══██╗   ██║       ██║╚════██║██║     ██╔══██║██║╚██╗██║██║  ██║    \r\n"  
//		+"██████╔╝███████╗███████║███████╗██║  ██║   ██║       ██║███████║███████╗██║  ██║██║ ╚████║██████╔╝    \r\n"  
//		+"╚═════╝ ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝     \r\n" 
//		+"\n\n\t\t\t\t    시작하려면 ENTER를 입력하세요. ");
		Thread 타이틀Thread = new Thread(new 타이틀());
		타이틀Thread.start();
		
//		음악 인트로음악 = new 음악("IntroMusic.mp3",true);
//		인트로음악.start();
		
		
		
		
		
		
		Scanner scanner = new Scanner(System.in);
		String a = scanner.nextLine();

		//캐릭터 선택 반복문
		for(;;) {
			//캐릭터 선택창 (스킬 아이템 수정)
			System.out.println("\n\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n\n"
					+ "\t 1.백수 \t\t\t\t 2.직장인 \t\t\t\t 3.할머니 \n\n"
					+ "\t 체력 : 100  \t\t\t 체력 : 80 \t\t\t 체력 : 60  \n"
					+ "\t 정신력 : 100 \t\t\t 정신력 : 80 \t\t\t 정신력 : 60  \n"
					+ "\t 공격력 : 10 \t\t\t 공격력 : 9 \t\t\t 공격력 : 8  \n"
					+ "\t 방어력 : 7 \t\t\t 방어력 : 6 \t\t\t 방어력 : 5  \n"
					+ "\t 포만감 : 100 \t\t\t 포만감 : 100 \t\t\t 포만감 : 100  \n"
					+ "\t 목마름 : 0 \t\t\t 목마름 : 0 \t\t\t 목마름 : 0 \n"
					+ "\t 청결도 : 100 \t\t\t 청결도 : 100 \t\t\t 청결도 : 100  \n"
					+ "\n\t\t\t\t\t 캐릭터를 선택하세요.  \n\n"
					+ "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			
			while(!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
			}
			int 캐릭터선택 = scanner.nextInt();

			
			if(캐릭터선택==1) {
				NonWork nonwork = new NonWork();
				user = nonwork;
				
			} else if(캐릭터선택==2) {
				Work work = new Work();	
				user = work;
				
			} else if(캐릭터선택==3) {
				Grand grand = new Grand();
				user = grand;
			} else {
				System.out.println("잘못 입력했습니다.\n");
				continue;
			}
			
			
			System.out.println("\n\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n\n"
					+ "\t\t\t\t\t"+user.name+"을/를 선택했습니다.\n\n"
					+ "\t\t\t\t\t 체력 : "+user.hp+"\n"
					+ "\t\t\t\t\t 정신력 : "+user.mental+"\n"
					+ "\t\t\t\t\t 공격력 : "+user.strong+"\n"
					+ "\t\t\t\t\t 포만감 : "+user.hunger+"\n"
					+ "\t\t\t\t\t 목마름 : "+user.thirsty+"\n"
					+ "\t\t\t\t\t 청결도 : "+user.clean+"\n"
					+ "\t\t\t\t 1. 시작 \t\t\t 2. 다시 선택 \n"
					+ "\n"
					+ "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			
			while(!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
			}
			int startBack = scanner.nextInt();
			
			if(startBack==1) {
				break;
			} else if(startBack==2) {
				continue;
			} else {
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				continue;
			}
		}
		
		System.out.println(
				"\n\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n\n"+
				"───│─────────────────────────────────────\r\n" + 
				"───│────────▄▄───▄▄───▄▄───▄▄───────│────\r\n" + 
				"───▌────────▒▒───▒▒───▒▒───▒▒───────▌────\r\n" + 
				"───▌──────▄▀█▀█▀█▀█▀█▀█▀█▀█▀█▀▄─────▌────\r\n" + 
				"───▌────▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄────────\r\n" + 
				"▀██████████████████████████████████████▄─\r\n" + 
				"──▀███████████████████████████████████▀──\r\n" + 
				"─────▀██████████████████████████████▀────\r\n" + 
				"▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\r\n" + 
				"▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\r\n" + 
				"▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\r\n"
				+ "\n으..뭐야..여긴 어디지...?"
				+ "\n난 분명 배에서 자고 있었는데..?\n"
				+ "여보세요!\n"
				+ "아무도 없어요?\n"
				+ "저기요!!! \n"
				+ "뭐야...여기 설마 무..인도..?\n"
				+ "\t \n"
				+ "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n"
				+ "\n계속 하려면 Enter를 눌러주세요.\n\n");

		String b = scanner.nextLine();
		String c = scanner.nextLine();
		
		
		//@@@@@아이템삽입
//		user.inventory.add("낚시대");
//		user.inventory.add("낚시대");
//		user.inventory.add("낚시대");
//		user.inventory.add("조명탄");
//		user.inventory.add("나무가지");
//		user.inventory.add("나뭇잎");
//		user.inventory.add("돌");
//		user.inventory.add("토끼진흙구이");
//		user.inventory.add("원숭이꼬치");
//		user.inventory.add("나무무기");
		
		
		

		
		
		
		//1일차 시작 
		Thread 로딩쓰레드1 = new Thread(new 로딩());
		로딩쓰레드1.start();

		try {
			로딩쓰레드1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("\n〓〓 1일차 〓〓\n");
		user.현재날짜 = "1일";
		user.잠 = false;
		
		//하루시간쓰레드
		Thread 하루시간1 = new Thread(new 하루시간(user));
		하루시간1.start();
		
		//날씨쓰레드
		Thread wetherThread = new Thread(new Wether(user));
		wetherThread.start();

		//청결도 포만감 목마름 감소 쓰레드
		Thread 청결포만목마름감소쓰레드 = new Thread(new 청결포만목마름감소(user));
		청결포만목마름감소쓰레드.setDaemon(true);
		청결포만목마름감소쓰레드.start();

		
//		인트로음악.close();

		시스템.메뉴(user,시스템,event,specialmonkey);
		
			if(user.현재시간.equals("오전")) {
				시스템.메뉴(user,시스템,event,specialmonkey);
			}
		
			if(user.현재시간.equals("오후")) {
				시스템.메뉴(user,시스템,event,specialmonkey);
			}
		
			if(user.잠==false && user.현재시간.equals("밤")) {
				시스템.메뉴(user,시스템,event,specialmonkey);
			}
		
		
		//1일차 하루시간 인터럽트
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			하루시간1.interrupt();
			
			
			
			//1->2로 넘어가는 로딩 
			Thread 로딩쓰레드2 = new Thread(new 로딩());
			로딩쓰레드2.start();

			try {
				로딩쓰레드2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		//2일차 시작
		System.out.println("\n〓〓 2일차 〓〓\n");
		user.현재날짜 = "2일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		Thread 하루시간2 = new Thread(new 하루시간(user));
		하루시간2.start();
		
		
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//2일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간2.interrupt();
		
		//2->3로 넘어가는 로딩 
		Thread 로딩쓰레드3 = new Thread(new 로딩());
		로딩쓰레드3.start();

		try {
			로딩쓰레드3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		//3일차 시작
		System.out.println("\n〓〓 3일차 〓〓\n");
		user.현재날짜 = "3일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		Thread 하루시간3 = new Thread(new 하루시간(user));
		하루시간3.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//3일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간3.interrupt();
		
		//3->4로 넘어가는 로딩 
		Thread 로딩쓰레드4 = new Thread(new 로딩());
		로딩쓰레드4.start();

		try {
			로딩쓰레드4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
		
		System.out.println("\n〓〓 4일차 〓〓\n");
		user.현재날짜 = "4일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		Thread 하루시간4 = new Thread(new 하루시간(user));
		하루시간4.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//4일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간4.interrupt();
		
		//4->5로 넘어가는 로딩 
		Thread 로딩쓰레드5 = new Thread(new 로딩());
		로딩쓰레드5.start();

		try {
			로딩쓰레드5.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
		System.out.println("\n〓〓 5일차 〓〓\n");
		user.현재날짜 = "5일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		Thread 하루시간5 = new Thread(new 하루시간(user));
		하루시간5.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//5일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간5.interrupt();
		
		//5->6로 넘어가는 로딩 
		Thread 로딩쓰레드6 = new Thread(new 로딩());
		로딩쓰레드6.start();

		try {
			로딩쓰레드6.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("\n〓〓 6일차 〓〓\n");
		user.현재날짜 = "6일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		
		
		Thread 하루시간6 = new Thread(new 하루시간(user));
		하루시간6.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//6일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간6.interrupt();
		
		//6->7로 넘어가는 로딩 
		Thread 로딩쓰레드7 = new Thread(new 로딩());
		로딩쓰레드7.start();

		try {
			로딩쓰레드7.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("\n〓〓 7일차 〓〓\n");
		user.현재날짜 = "7일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		
		Thread 하루시간7 = new Thread(new 하루시간(user));
		하루시간7.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//7일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간7.interrupt();
		
		//7->8로 넘어가는 로딩 
		Thread 로딩쓰레드8 = new Thread(new 로딩());
		로딩쓰레드8.start();

		try {
			로딩쓰레드8.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("\n〓〓 8일차 〓〓\n");
		user.현재날짜 = "8일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		
		
		
		Thread 하루시간8 = new Thread(new 하루시간(user));
		하루시간8.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//8일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간8.interrupt();
		
		//8->9로 넘어가는 로딩 
		Thread 로딩쓰레드9 = new Thread(new 로딩());
		로딩쓰레드9.start();

		try {
			로딩쓰레드9.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		System.out.println("\n〓〓 9일차 〓〓\n");
		user.현재날짜 = "9일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		Thread 하루시간9 = new Thread(new 하루시간(user));
		하루시간9.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//9일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간9.interrupt();
		
		//9->10로 넘어가는 로딩 
		Thread 로딩쓰레드10 = new Thread(new 로딩());
		로딩쓰레드10.start();

		try {
			로딩쓰레드10.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("\n〓〓 10일차 〓〓\n");
		user.현재날짜 = "10일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		Thread 하루시간10 = new Thread(new 하루시간(user));
		하루시간10.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//10일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간10.interrupt();
		
		//10->11로 넘어가는 로딩 
		Thread 로딩쓰레드11 = new Thread(new 로딩());
		로딩쓰레드11.start();

		try {
			로딩쓰레드11.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("\n〓〓 11일차 〓〓\n");
		user.현재날짜 = "11일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		Thread 하루시간11 = new Thread(new 하루시간(user));
		하루시간11.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//11일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간11.interrupt();
		
		//11->12로 넘어가는 로딩 
		Thread 로딩쓰레드12 = new Thread(new 로딩());
		로딩쓰레드12.start();

		try {
			로딩쓰레드12.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		System.out.println("\n〓〓 12일차 〓〓\n");
		user.현재날짜 = "12일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		Thread 하루시간12 = new Thread(new 하루시간(user));
		하루시간12.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//12일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간12.interrupt();
		
		//12->13로 넘어가는 로딩 
		Thread 로딩쓰레드13 = new Thread(new 로딩());
		로딩쓰레드13.start();

		try {
			로딩쓰레드13.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("\n〓〓 13일차 〓〓\n");
		user.현재날짜 = "13일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		Thread 하루시간13 = new Thread(new 하루시간(user));
		하루시간13.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//13일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간13.interrupt();
		
		//13->14로 넘어가는 로딩 
		Thread 로딩쓰레드14 = new Thread(new 로딩());
		로딩쓰레드14.start();

		try {
			로딩쓰레드14.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		System.out.println("\n〓〓 14일차 〓〓\n");
		user.현재날짜 = "14일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		Thread 하루시간14 = new Thread(new 하루시간(user));
		하루시간14.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//14일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간14.interrupt();
		
		//14->15로 넘어가는 로딩 
		Thread 로딩쓰레드15 = new Thread(new 로딩());
		로딩쓰레드15.start();

		try {
			로딩쓰레드15.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		System.out.println("\n〓〓 15일차 〓〓\n");
		user.현재날짜 = "15일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		Thread 하루시간15 = new Thread(new 하루시간(user));
		하루시간15.start();
		
		시스템.메뉴(user,시스템,event,specialmonkey);
		
		if(user.현재시간.equals("오전")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.현재시간.equals("오후")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
	
		if(user.잠==false && user.현재시간.equals("밤")) {
			시스템.메뉴(user,시스템,event,specialmonkey);
		}
		
		//15일차 하루시간 인터럽트
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		하루시간15.interrupt();

		
		//15->16로 넘어가는 로딩 
		Thread 로딩쓰레드16 = new Thread(new 로딩());
		로딩쓰레드16.start();

		try {
			로딩쓰레드16.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		

		

		System.out.println("\n〓〓 16일차 〓〓\n");
		user.현재날짜 = "16일";
		user.잠 = false;
		user.현재시간 = "오전";
		
		event.헬기등장(user);

		
	}
}

