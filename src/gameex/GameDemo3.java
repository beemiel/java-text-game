package gameex;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//음악
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
//import javazoom.jl.player.Player;
//import javax.swing.JOptionPane;
import javax.swing.*;

class Character {
	// 변수
	int hp; // 체력
	int strong; // 기본 공격력
	int fightStrong; // 전투시 공격력
	int dice = 0; // 낚시주사위 결과 값
	String name; // 캐릭터 이름
	String skill; // 스킬 캐릭터 생성창에서 사용

	//
	public int randomStrong(Character character) {
		Random rnd = new Random();
		int 크리티컬 = rnd.nextInt(10) + 1;
		character.fightStrong = (character.strong * 2) + 크리티컬;
		return character.fightStrong;
	}

}

class User extends Character {
	int mental; // 정신력(행동력)
	int clean; // 청결도
	int hunger; // 포만감
	int thirsty; // 목마름
	int 공포감;
	int select; // 행동 선택창에서 선택한 숫자를 저장
//	int userSelect; //유저선택지 저장 ->삭제?
	boolean 독; // 독상태
	boolean 잠 = false; // 잠을 잤는지 안잤는지
	boolean 늪빠짐 = false;
	boolean 원숭이인사 = false;
	int characterMenuSelect; // 유저의 메뉴선택 행동하기를 선택할시 이 변수에 저장됨
	String 현재시간 = ""; // 현재시간을 저장하고 알려줌
	String 현재날짜; // 현재 몇일차인지 알려줌

	String skill;
	String skill2;
	String skill3;


	ArrayList<String> inventory = new ArrayList<String>(); // 인벤토리
	ArrayList<String> 갑옷 = new ArrayList<String>(); // 갑옷창
	ArrayList<String> 무기 = new ArrayList<String>(); // 무기창

	// 애니멀 클래스 객체 생성 -> 애니멀 클래스 메서드를 유저 클래스 메서드 안에 넣기 위해서
	Animal animal = new Animal();
	Item item = new Item();
	시스템 시스템 = new 시스템();

	// 스킬 오버라이딩
	public void skill(User user, Animal animal) {
	}

	public void skill2(User user, Animal animal) {
	}

	public void skill3(User user, Animal animal) {
	}

	// 플레이어 일반 공격
	public void attack(User user, Animal animal) {

		System.out.println(user.name + "의 공격");
		System.out.println("현재 적 체력 : " + animal.hp);
		System.out.println("공격!");
		animal.hp = animal.hp - user.randomStrong(user);
		if (animal.hp <= 0) {
			animal.hp = 0;
		}
		System.out.println("현재 적 체력 : " + animal.hp);
	}

	public void fishing(User user, Animal animal) {
		// 낚시 총 2번
		for (int i = 0; i < 2; i++) {
			user.dice = 0; // 유저의 주사위값을 초기화해줌
			animal = 시스템.물고기랜덤(); // 숲속 동물객체를 랜덤으로 생성하는 메서드
			// 입질시간
			Thread 입질 = new Thread(new 입질());
			입질.start();
			try {
				입질.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 물고기 걸릴 확률 랜덤
			Random rnd = new Random();
			if (rnd.nextInt(10) < 3) {
				System.out.println("아무것도 걸리지 않았습니다...\n");
			} else if (rnd.nextInt(10) < 10) {
				System.out.println("무언가 걸렸습니다!");
				System.out.println(animal.name + "이/가 나타났습니다!\n");
				// 각각 주사위 세번씩 던지는 쓰레드
				Thread 낚시주사위 = new Thread(new 낚시주사위(user, animal));
				낚시주사위.start();
				try {
					낚시주사위.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 낚시 결과 출력
				if (user.dice > animal.dice) {
					System.out.println(animal.name + "를 잡았습니다!" + "\n");
					// 아이템 습득 메서드
					this.getItem(user, animal);
				} else if (user.dice <= animal.dice) {
					System.out.println(animal.name + "를 놓쳤습니다.." + "\n");
				}
			}
		} // for문 끝
		System.out.println("현재 인벤토리 : " + user.inventory + "\n");

	}

	// 상태보기
	public void status(User user) {
		System.out.println("이름 = " + user.name + "\n" + "체력 = " + user.hp + "\n" + "정신력 = " + user.mental + "\n"
				+ "공격력 = " + user.strong + "\n" + "포만감 = " + user.hunger + "\n" + "목마름 = " + user.thirsty + "\n"
				+ "청결도 = " + user.clean + "\n" + "공포감 = " + user.공포감 + "\n" + "착용중인 갑옷 = " + user.갑옷 + "\n"
				+ "착용중인 무기 = " + user.무기 + "\n");
	}

	// 구조신호 보내기
	public void sosSignal(시스템 시스템) {
		if (this.inventory.contains("조명탄")) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("조명탄을 가지고 있습니다!");
			System.out.println("조명탄을 쏘았습니다!");
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			시스템.goodEnd();
		} else if (!this.inventory.contains("조명탄")) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("조명탄이 없습니다...");
			System.out.println("헬기를 떠나보냅니다...");
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
			시스템.specialEnd();
		}
	}

	// 도망(플레이어 체력) - 체력 깎임
	public void runAway(User user) {
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("도망을 치기로 마음 먹었습니다.");
		System.out.println("뒤도 안돌아보고 뛰어가다가 넘어졌습니다.");
		System.out.println("체력이 5 감소합니다.");
		user.hp -= 5;
		System.out.println("현재 체력 : " + user.hp + "\n");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		시스템.userHpCheck(user);
	}

	// 플레이어 공격 선택
	public void 공격선택(User user, Animal animal) {
		Scanner scanner = new Scanner(System.in);
		// 플레이어공격 for문
		for (;;) {
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("스킬사용은 정신력을 소모합니다.\n");
			System.out.println("1. 공격 \n2. " + user.skill + "\n3. " + user.skill2 + "\n4. " + user.skill3 + "\n");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println();

			// 스킬선택
			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
			}
			int z = scanner.nextInt();
			if (z == 1) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//				음악 타격음 = new 음악("Kick.mp3", false);
//				타격음.start();
				user.attack(user, animal);
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			} else if (z == 2) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.skill(user, animal);
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			} else if (z == 3) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//				음악 타격음 = new 음악("Kick.mp3", false);
//				타격음.start();
				user.skill2(user, animal);
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			} else if (z == 4) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//				음악 타격음 = new 음악("Kick.mp3", false);
//				타격음.start();
				user.skill3(user, animal);
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			} else {
				System.out.println("잘못 입력했습니다.\n");
				continue;
			}

			// 적 체력 확인
			if (animal.hp <= 0) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				시스템.monsterHpCheck(user, animal);
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				break; // 다음전투
			} else {
				continue; // 다시공격
			}
		} // 공격for문 끝
	}

	// 아이템섭취 주석
	public void 아이템섭취(User user, 섭취아이템 item) {

		if (item.섭취유무 == false) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println("\n먹을 수 있는 아이템이 아닙니다.\n");
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
		} else if (item.섭취유무 == true && item.음식 == true && item.약 == false && item.해독 == false) {

			// 음식
			if (user.inventory.contains(item.name)) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
//				음악 음식섭취효과음 = new 음악("EatMeat.mp3", false);
//				음식섭취효과음.start();
				System.out.println(item.name + "을/를 먹습니다.\n");
				System.out.println("현재 체력 = " + user.hp + " 현재 정신력 = " + user.mental + " 현재 포만감 = " + user.hunger
						+ " 현재 목마름 = " + user.thirsty);
				System.out.println("\n스탯이 회복됩니다.\n");
				user.hp += item.hpUp;
				user.mental += item.mentalUp;
				user.hunger += item.포만감상승;
				user.thirsty -= item.목마름감소;
				user.inventory.remove(item.name);
				System.out.println("현재 체력 = " + user.hp + "\n" + "현재 정신력 = " + user.mental + "\n" + "현재 포만감 = "
						+ user.hunger + "\n" + "현재 목마름 = " + user.thirsty);
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");

			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println(item.name + "이/가 없습니다.");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			}

		} else if (item.섭취유무 == true && item.음식 == false && item.약 == true && item.해독 == false) {

			// 약
			if (user.inventory.contains(item.name)) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
//				음악 음식섭취효과음 = new 음악("EatMeat.mp3", false);
//				음식섭취효과음.start();
				System.out.println(item.name + "을/를 먹습니다.\n");
				System.out.println("현재 체력 = " + user.hp + " 현재 정신력 = " + user.mental);
				System.out.println("\n스탯이 회복됩니다.\n");
				user.hp += item.hpUp;
				user.mental += item.mentalUp;
				user.inventory.remove(item.name);
				System.out.println("현재 체력 = " + user.hp + "\n" + "현재 정신력 = " + user.mental);
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");

			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println(item.name + "이/가 없습니다.");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");

			}

		} else if (item.섭취유무 == true && item.음식 == false && item.약 == true && item.해독 == true) {

			// 해독제
			if (user.inventory.contains(item.name)) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
//				음악 음식섭취효과음 = new 음악("EatMeat.mp3", false);
//				음식섭취효과음.start();
				System.out.println(item.name + "을/를 먹습니다.\n");
				System.out.println("\n독이 치료됩니다.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
				user.독 = false;
				user.inventory.remove(item.name);

			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println(item.name + "이/가 없습니다.");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			}

		} // 섭취 if문 끝

	} // 아이템 섭취 메서드 끝

//	public void eatYes(User user, 섭취아이템 items) {
//		if (user.inventory.contains(items.name)) {
//
//			System.out.println(items.name + "을/를 먹습니다.\n");
//			System.out.println("현재 체력 = " + this.hp + " 현재 정신력 = " + this.mental + " 현재 포만감 = " + this.hunger
//					+ " 현재 목마름 = " + this.thirsty);
//			System.out.println("\n스탯이 회복됩니다.\n");
//			this.hp += items.hpUp;
//			this.mental += items.mentalUp;
//			this.hunger += items.포만감상승;
//			this.thirsty -= items.목마름감소;
//			this.inventory.remove(items.name);
//			System.out.println("현재 체력 = " + this.hp + "\n" + " 현재 정신력 = " + this.mental + "\n" + " 현재 포만감 = "
//					+ this.hunger + "\n" + " 현재 목마름 = " + this.thirsty);
//		} else {
//			System.out.println(items.name + "이/가 없습니다.");
//		}
//	}

//	// 약먹기
//	public void eatYes2(User user, 섭취아이템 items) {
//		if (user.inventory.contains(items.name)) {
//
//			System.out.println(items.name + "을/를 먹습니다.\n");
//			System.out.println("현재 체력 = " + this.hp + " 현재 정신력 = " + this.mental);
//			System.out.println("\n스탯이 회복됩니다.\n");
//			this.hp += items.hpUp;
//			this.mental += items.mentalUp;
//			this.inventory.remove(items.name);
//			System.out.println("현재 체력 = " + this.hp + "\n" + " 현재 정신력 = " + this.mental);
//		} else {
//			System.out.println(items.name + "이/가 없습니다.");
//		}
//	}

	// 잠
	public void sleep(User user, Event event) {
		if (this.inventory.contains("텐트")) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("텐트를 소지하고 있습니다.");
			System.out.println("회복 효과가 상승합니다.");
			this.hp += 30;
			this.mental += 30;
			this.공포감 -= 20;
			System.out.println("현재 체력 : " + this.hp + "\n" + "현재 정신력 : " + this.mental + "\n" + "현재 공포감 : " + this.공포감 + "\n");
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
			event.벌레등장(user);
		} else {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("잠을 자는 동안 스탯이  회복됩니다.");
			this.hp += 20;
			this.mental += 20;
			this.공포감 -= 10;
			System.out.println("현재 체력 : " + this.hp + "\n" + "현재 정신력 : " + this.mental + "\n" + "현재 공포감 : " + this.공포감 + "\n");
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
			event.벌레등장(user);
		}

	}

	// 씻기
	public void wash() {
		System.out.println("샤워를 하면 청결도와 정신력이  회복됩니다.");
		this.clean += 60;
		this.mental += 30;
		System.out.println("현재 청결도 : " + this.clean + "\n" + "현재 정신력 : " + this.mental + "\n");
	}

	// 아이템 습득
	public void getItem(User user, Animal animal) {
		System.out.println(animal.item + "을/를 가방에 넣었습니다.\n");
		user.inventory.add(animal.item);
	}

	// 아이템 교환1
	public void 뷰1교환() {
		for(;;) {
			
		System.out.println("교환할 물건의 숫자를 입력하세요.");
		Scanner scanner = new Scanner(System.in);
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
		}
		int select = scanner.nextInt();
		if (select == 1) {
			if (this.inventory.contains("뱀허물")) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("아이템을 교환합니다.");
				this.inventory.remove("뱀허물");
				this.inventory.add("녹슨칼");
				System.out.println(this.inventory);
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("인간! 물건 없다 우끼! 다음에 다시 온다 우끼!");
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			}
		} else if (select == 2) {
			if (this.inventory.contains("단데기허물") && this.inventory.contains("나무열매")) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("아이템을 교환합니다.");
				this.inventory.remove("단데기허물");
				this.inventory.remove("나무열매");
				this.inventory.add("빵");
				System.out.println(this.inventory);
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("인간! 물건 없다 우끼! 다음에 다시 온다 우끼!");
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			}
		} else if (select == 3) {
			if (this.inventory.contains("메기수염") && this.inventory.contains("블랙베리") && this.inventory.contains("깃털")) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("아이템을 교환합니다.");
				this.inventory.remove("메기수염");
				this.inventory.remove("블랙베리");
				this.inventory.remove("깃털");
				this.inventory.add("햄버거세트");
				System.out.println(this.inventory);
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("인간! 물건 없다 우끼! 다음에 다시 온다 우끼!");
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			}
		} else if (select == 4) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("인간.. 가난하다 우끼... 다음에 다시 온다 우끼....");
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
		} else {
			System.out.println("잘못 선택했습니다.");
			continue;
		}
		break;
		}//for끝
	}//메서드 끝

	// 아이템 교환2
	public void 뷰2교환() {
		for(;;) {
		System.out.println("교환할 물건의 숫자를 입력하세요.");
		Scanner scanner = new Scanner(System.in);
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
		}
		int select = scanner.nextInt();
		if (select == 1) {
			if (this.inventory.contains("스노우베리") && this.inventory.contains("진흙")) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("아이템을 교환합니다.");
				this.inventory.remove("스노우베리");
				this.inventory.remove("진흙");
				this.inventory.add("라면");
				System.out.println(this.inventory);
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("인간! 물건 없다 우끼! 다음에 다시 온다 우끼!");
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			}
		} else if (select == 2) {
			if (this.inventory.contains("나무가지") && this.inventory.contains("나무열매")) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("아이템을 교환합니다.");
				this.inventory.remove("나무가지");
				this.inventory.remove("나무열매");
				this.inventory.add("토끼고기");
				System.out.println(this.inventory);
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("인간! 물건 없다 우끼! 다음에 다시 온다 우끼!");
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			}
		} else if (select == 3) {
			if (this.inventory.contains("돌") && this.inventory.contains("나무판자")) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("아이템을 교환합니다.");
				this.inventory.remove("돌");
				this.inventory.remove("나무판자");
				this.inventory.add("열대어지느러미");
				System.out.println(this.inventory);
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("인간! 물건 없다 우끼! 다음에 다시 온다 우끼!");
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			}
		} else if (select == 4) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("인간.. 가난하다 우끼... 다음에 다시 온다 우끼....");
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
		} else {
			System.out.println("잘못 입력했습니다.");
			continue;
		}
		break;
		}//for문 끝
	}//메서드 끝 

	// 아이템 교환3
	public void 뷰3교환() {
		for(;;) {
		System.out.println("교환할 물건의 숫자를 입력하세요.");
		Scanner scanner = new Scanner(System.in);
		while (!scanner.hasNextInt()) {
			scanner.next();
			System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
		}
		int select = scanner.nextInt();
		if (select == 1) {
			if (this.inventory.contains("해골바가지") && this.inventory.contains("동전") && this.inventory.contains("조개껍질")
					&& this.inventory.contains("테니스라켓") && this.inventory.contains("열대어지느러미")
					&& this.inventory.contains("토끼꼬치")) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("아이템을 교환합니다.");
				this.inventory.remove("해골바가지");
				this.inventory.remove("동전");
				this.inventory.remove("조개껍질");
				this.inventory.remove("테니스라켓");
				this.inventory.remove("열대어지느러미");
				this.inventory.remove("토끼꼬치");
				this.inventory.add("조명탄");
				System.out.println(this.inventory);
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("물건 없다 우끼! 다음에 다시 온다 우끼!");
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			}
		} else if (select == 2) {
			if (this.inventory.contains("동전") && this.inventory.contains("메기수염") && this.inventory.contains("진흙")) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("아이템을 교환합니다.");
				this.inventory.remove("동전");
				this.inventory.remove("메기수염");
				this.inventory.remove("진흙");
				this.inventory.add("테니스라켓");
				System.out.println(this.inventory);
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("물건 없다 우끼! 다음에 다시 온다 우끼!");
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			}
		} else if (select == 3) {
			if (this.inventory.contains("단데기허물")) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("아이템을 교환합니다.");
				this.inventory.remove("단데기허물");
				this.inventory.add("수액");
				System.out.println(this.inventory);
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			} else {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("물건 없다 우끼! 다음에 다시 온다 우끼!");
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
			}
		} else if (select == 4) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("인간.. 가난하다 우끼... 다음에 다시 온다 우끼....");
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
		} else {
			System.out.println("잘못 선택했습니다.");
		}
		}//for문 끝
	}//메서드 끝 

	public void 아이템조합() {
		for (;;) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("아이템 조합 선택\n");
			System.out.println("조합가능한 아이템");
			System.out.println("불 / 막대기 / 덫 / 통발 / 해독제 / 체력약 / 정신력약 / 수통 / 낚시대 / 텐트");
			System.out.println("나무갑옷 / 나무무기 / 가죽갑옷 / 뼈갑옷 / 뼈무기 ");
			System.out.println("만들고자 하는 아이템의 이름을 입력해주세요.\n");
			System.out.println("뒤로가기는 뒤로가기를 입력해주세요.");
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");

			Scanner scanner = new Scanner(System.in);

			String 아이템선택 = scanner.nextLine();

			if ("불".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("나뭇잎") && this.inventory.contains("돌")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("나무가지");
					this.inventory.remove("나뭇잎");
					this.inventory.remove("돌");
					this.inventory.add("불");
					System.out.println("불을 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("막대기".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("녹슨칼")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("나무가지");
					this.inventory.add("막대기");
					System.out.println("막대기를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");

					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("덫".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("나뭇잎") && this.inventory.contains("나무덩쿨")
						&& this.inventory.contains("쥐고기")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("나무가지");
					this.inventory.remove("나뭇잎");
					this.inventory.remove("나무덩쿨");
					this.inventory.remove("쥐고기");
					this.inventory.add("덫");
					System.out.println("덫을 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("통발".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("나무덩쿨")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("나무가지");
					this.inventory.remove("나무덩쿨");
					this.inventory.add("통발");
					System.out.println("통발을 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("해독제".equals(아이템선택)) {
				if (this.inventory.contains("허브") && this.inventory.contains("마리골드")
						&& this.inventory.contains("치커리")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("허브");
					this.inventory.remove("마리골드");
					this.inventory.remove("치커리");
					this.inventory.add("해독제");
					System.out.println("해독제를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("체력약".equals(아이템선택)) {
				if (this.inventory.contains("알로에") && this.inventory.contains("마리골드")
						&& this.inventory.contains("허브")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("알로에");
					this.inventory.remove("마리골드");
					this.inventory.remove("허브");
					this.inventory.add("체력약");
					System.out.println("체력약을 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("정신력약".equals(아이템선택)) {
				if (this.inventory.contains("알로에") && this.inventory.contains("치커리") && this.inventory.contains("허브")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("알로에");
					this.inventory.remove("치커리");
					this.inventory.remove("허브");
					this.inventory.add("정신력약");
					System.out.println("정신력약을 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("수통".equals(아이템선택)) {
				if (this.inventory.contains("악어가죽") && this.inventory.contains("나무덩쿨")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("악어가죽");
					this.inventory.remove("나무덩쿨");
					this.inventory.add("수통");
					System.out.println("수통을 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("낚시대".equals(아이템선택)) {
				if (this.inventory.contains("막대기") && this.inventory.contains("나무덩쿨")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("막대기");
					this.inventory.remove("나무덩쿨");
					this.inventory.add("낚시대");
					System.out.println("낚시대를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("텐트".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("나무판자")
						&& this.inventory.contains("야자나뭇잎") && this.inventory.contains("나무덩쿨")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("나무가지");
					this.inventory.remove("나무판자");
					this.inventory.remove("야자나뭇잎");
					this.inventory.remove("나무덩쿨");
					this.inventory.add("텐트");
					System.out.println("텐트를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("나무갑옷".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("수액")
						&& this.inventory.contains("뱀허물")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("나무가지");
					this.inventory.remove("수액");
					this.inventory.remove("뱀허물");
					this.inventory.add("나무갑옷");
					System.out.println("나무갑옷를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("나무무기".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("수액")
						&& this.inventory.contains("피라냐이빨")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("나무가지");
					this.inventory.remove("수액");
					this.inventory.remove("피라냐이빨");
					this.inventory.add("나무무기");
					System.out.println("나무무기를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("가죽갑옷".equals(아이템선택)) {
				if (this.inventory.contains("악어가죽") && this.inventory.contains("수액")
						&& this.inventory.contains("고릴라가죽")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("악어가죽");
					this.inventory.remove("수액");
					this.inventory.remove("고릴라가죽");
					this.inventory.add("가죽갑옷");
					System.out.println("가죽갑옷를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("뼈갑옷".equals(아이템선택)) {
				if (this.inventory.contains("뼈") && this.inventory.contains("거북이등딱지")
						&& this.inventory.contains("재규어이빨") && this.inventory.contains("수액")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("뼈");
					this.inventory.remove("거북이등딱지");
					this.inventory.remove("수액");
					this.inventory.remove("재규어이빨");
					this.inventory.add("뼈갑옷");
					System.out.println("뼈갑옷를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("뼈무기".equals(아이템선택)) {
				if (this.inventory.contains("뼈") && this.inventory.contains("깃털") && this.inventory.contains("재규어이빨")
						&& this.inventory.contains("수액")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("아이템을 만듭니다.");
					this.inventory.remove("뼈");
					this.inventory.remove("깃털");
					this.inventory.remove("수액");
					this.inventory.remove("재규어이빨");
					this.inventory.add("뼈무기");
					System.out.println("뼈무기를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("재료가 부족합니다");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				}

			} else if ("뒤로가기".equals(아이템선택)) {
				break;
			} else {
				System.out.println("잘못 입력했습니다.\n");
				continue;
			} // 조건문끝

		} // for문 끝

	}

	public void 요리() {
		for (;;) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println("\n요리를 선택했습니다.");
			System.out.println("만들 수 있는 요리\n");
			System.out.println("원숭이꼬치 / 쥐꼬치 / 토끼꼬치 / 너구리꼬치 / 피라미꼬치 ");
			System.out.println("구운원숭이꼬치 / 구운쥐꼬치 / 구운토끼꼬치 / 구운너구리꼬치 / 구운피라미꼬치 ");
			System.out.println("토끼진흙구이 / 육지고기국 / 물고기국\n");
			System.out.println("만들고자 하는 요리의 이름을 입력해주세요.\n");
			System.out.println("뒤로가기는 뒤로가기를 입력해주세요.");
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
			Scanner scanner = new Scanner(System.in);
			String 아이템선택 = scanner.nextLine();

			if ("원숭이꼬치".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("원숭이고기")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("나무가지");
					this.inventory.remove("원숭이고기");
					this.inventory.add("원숭이꼬치");
					System.out.println("원숭이꼬치를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("쥐꼬치".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("쥐고기")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("나무가지");
					this.inventory.remove("쥐고기");
					this.inventory.add("쥐꼬치");
					System.out.println("쥐꼬치를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("토끼꼬치".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("토끼고기")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("나무가지");
					this.inventory.remove("토끼고기");
					this.inventory.add("토끼꼬치");
					System.out.println("토끼꼬치를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("너구리꼬치".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("너구리고기")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("나무가지");
					this.inventory.remove("너구리고기");
					this.inventory.add("너구리꼬치");
					System.out.println("너구리꼬치를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("피라미꼬치".equals(아이템선택)) {
				if (this.inventory.contains("나무가지") && this.inventory.contains("피라미")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("나무가지");
					this.inventory.remove("피라미");
					this.inventory.add("피라미꼬치");
					System.out.println("피라미꼬치를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("구운원숭이꼬치".equals(아이템선택)) {
				if (this.inventory.contains("원숭이꼬치") && this.inventory.contains("불")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("원숭이꼬치");
					this.inventory.add("구운원숭이꼬치");
					System.out.println("구운원숭이꼬치를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("구운쥐꼬치".equals(아이템선택)) {
				if (this.inventory.contains("쥐꼬치") && this.inventory.contains("불")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("쥐꼬치");
					this.inventory.add("구운쥐꼬치");
					System.out.println("구운쥐꼬치를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("구운토끼꼬치".equals(아이템선택)) {
				if (this.inventory.contains("토끼꼬치") && this.inventory.contains("불")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("토끼꼬치");
					this.inventory.add("구운토끼꼬치");
					System.out.println("구운토끼꼬치를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("구운너구리꼬치".equals(아이템선택)) {
				if (this.inventory.contains("너구리꼬치") && this.inventory.contains("불")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("너구리꼬치");
					this.inventory.add("구운너구리꼬치");
					System.out.println("구운너구리꼬치를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n"); 
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("구운피라미꼬치".equals(아이템선택)) {
				if (this.inventory.contains("피라미꼬치") && this.inventory.contains("불")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("피라미꼬치");
					this.inventory.add("구운피라미꼬치");
					System.out.println("구운피라미꼬치를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("토끼진흙구이".equals(아이템선택)) {
				if (this.inventory.contains("진흙") && this.inventory.contains("토끼고기") && this.inventory.contains("불")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("진흙");
					this.inventory.remove("토끼고기");
					this.inventory.add("토끼진흙구이");
					System.out.println("토끼진흙구이를 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("육지고기국".equals(아이템선택)) {
				if (this.inventory.contains("냄비") && this.inventory.contains("물") && this.inventory.contains("원숭이고기")
						&& this.inventory.contains("쥐고기") && this.inventory.contains("토끼고기")
						&& this.inventory.contains("불")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("물");
					this.inventory.remove("원숭이고기");
					this.inventory.remove("쥐고기");
					this.inventory.remove("토끼고기");
					this.inventory.add("육지고기국");
					System.out.println("육지고기국을 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("물고기국".equals(아이템선택)) {
				if (this.inventory.contains("냄비") && this.inventory.contains("물") && this.inventory.contains("뱀장어고기")
						&& this.inventory.contains("피라미") && this.inventory.contains("불")) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템을 만듭니다.\n");
					this.inventory.remove("물");
					this.inventory.remove("뱀장어고기");
					this.inventory.remove("피라미");
					this.inventory.add("물고기국");
					System.out.println("물고기국을 만들었습니다!");
					System.out.println(this.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n재료가 부족합니다\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					break;
				}

			} else if ("뒤로가기".equals(아이템선택)) {
				break;
			} else {
				System.out.println("잘못 입력했습니다.\n");
				continue;
			}

		}

	}

	public void 장비착용(User user) {
		for (;;) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("장비 착용 선택\n");
			System.out.println("착용하고자 하는 장비의 번호를 선택하세요.\n");
			System.out.println("1.나무갑옷  2.나무무기  3.가죽갑옷  4.뼈갑옷  5.뼈무기  6.망가진도끼  7.망가진톱  8.돌아가기");
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
			Scanner scanner = new Scanner(System.in);
			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
			}
			int a = scanner.nextInt();
			if (a == 1) {
				if (user.갑옷.isEmpty()) {
					if (this.inventory.contains("나무갑옷")) {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("나무갑옷 선택");
						System.out.println("나무갑옷를 착용합니다.");
						System.out.println("공격력과 체력이 상승하고 공포감이 하락합니다.");
						this.inventory.remove("나무갑옷");
						this.갑옷.add("나무갑옷");
						장비아이템 나무갑옷 = new 장비아이템("나무갑옷", 0, 10, 10, 30);
						this.strong += 나무갑옷.공격력상승;
						this.hp += 나무갑옷.체력상승;
						this.공포감 -= 나무갑옷.공포감감소;
						System.out.println("현재 공격력 : " + user.strong + " 현재 체력 : " + user.hp + " 현재 공포감 : " + user.공포감);
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						break;
					} else {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("장비가 없습니다.");
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						continue;
					}

				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("현재 갑옷을 착용한 상태입니다.");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
				}

			} else if (a == 2) {
				if (user.무기.isEmpty()) {
					if (this.inventory.contains("나무무기")) {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("나무무기 선택");
						System.out.println("나무무기를 착용합니다.");
						System.out.println("공격력이 상승하고 공포감이 하락합니다.");
						this.inventory.remove("나무무기");
						this.무기.add("나무무기");
						장비아이템 나무무기 = new 장비아이템("나무무기", 6, 0, 10, 30);
						this.strong += 나무무기.공격력상승;
						this.공포감 -= 나무무기.공포감감소;
						System.out.println("현재 공격력 : " + user.strong + " 현재 공포감 : " + user.공포감);
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						break;
					} else {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("장비가 없습니다.");
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						continue;
					}

				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("현재 무기를 착용한 상태입니다.");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
				}

			} else if (a == 3) {
				if (user.갑옷.isEmpty()) {
					if (this.inventory.contains("가죽갑옷")) {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("가죽갑옷 선택");
						System.out.println("가죽갑옷를 착용합니다.");
						System.out.println("공격력과 체력이 상승하고 공포감이 하락합니다.");
						this.inventory.remove("가죽갑옷");
						this.갑옷.add("가죽갑옷");
						장비아이템 가죽갑옷 = new 장비아이템("가죽갑옷", 0, 20, 15, 40);
						this.strong += 가죽갑옷.공격력상승;
						this.hp += 가죽갑옷.체력상승;
						this.공포감 -= 가죽갑옷.공포감감소;
						System.out.println("현재 공격력 : " + user.strong + " 현재 체력 : " + user.hp + " 현재 공포감 : " + user.공포감);
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						break;
					} else {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("장비가 없습니다.");
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						continue;
					}

				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("현재 갑옷을 착용한 상태입니다.");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
				}

			} else if (a == 4) {
				if (user.갑옷.isEmpty()) {
					if (this.inventory.contains("뼈갑옷")) {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("뼈갑옷 선택");
						System.out.println("뼈갑옷를 착용합니다.");
						System.out.println("공격력과 체력이 상승하고 공포감이 하락합니다.");
						this.inventory.remove("뼈갑옷");
						this.갑옷.add("뼈갑옷");
						장비아이템 뼈갑옷 = new 장비아이템("뼈갑옷", 0, 30, 20, 50);
						this.strong += 뼈갑옷.공격력상승;
						this.hp += 뼈갑옷.체력상승;
						this.공포감 -= 뼈갑옷.공포감감소;
						System.out.println("현재 공격력 : " + user.strong + " 현재 방어력 : " + user.hp + " 현재 공포감 : " + user.공포감);
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						break;
					} else {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("장비가 없습니다.");
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						continue;
					}

				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("현재 갑옷을 착용한 상태입니다.");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
				}

			} else if (a == 5) {
				if (user.무기.isEmpty()) {
					if (this.inventory.contains("뼈무기")) {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("뼈무기 선택");
						System.out.println("뼈무기를 착용합니다.");
						System.out.println("공격력이 상승하고 공포감이 하락합니다.");
						this.inventory.remove("뼈무기");
						this.무기.add("뼈무기");
						장비아이템 뼈무기 = new 장비아이템("뼈무기", 10, 0, 20, 50);
						this.strong += 뼈무기.공격력상승;
						this.공포감 -= 뼈무기.공포감감소;
						System.out.println("현재 공격력 : " + user.strong + " 현재 공포감 : " + user.공포감);
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						break;
					} else {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("장비가 없습니다.");
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						continue;
					}

				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("현재 무기를 착용한 상태입니다.");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
				}

			} else if (a == 6) {
				if (user.무기.isEmpty()) {
					if (this.inventory.contains("망가진도끼")) {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("망가진도끼 선택");
						System.out.println("망가진도끼를 착용합니다.");
						System.out.println("공격력이 상승하고 공포감이 하락합니다.");
						this.inventory.remove("망가진도끼");
						this.무기.add("망가진도끼");
						장비아이템 망가진도끼 = new 장비아이템("망가진도끼", 5, 0, 5, 20);
						this.strong += 망가진도끼.공격력상승;
						this.공포감 -= 망가진도끼.공포감감소;
						System.out.println("현재 공격력 : " + user.strong + " 현재 공포감 : " + user.공포감);
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						break;
					} else {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("장비가 없습니다.");
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						continue;
					}

				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("현재 무기를 착용한 상태입니다.");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
				}

			} else if (a == 7) {
				if (user.무기.isEmpty()) {
					if (this.inventory.contains("망가진톱")) {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("망가진톱 선택");
						System.out.println("망가진톱을 착용합니다.");
						System.out.println("공격력이 상승하고 공포감이 하락합니다.");
						this.inventory.remove("망가진톱");
						this.무기.add("망가진톱");
						장비아이템 망가진톱 = new 장비아이템("망가진톱", 5, 0, 5, 20);
						this.strong += 망가진톱.공격력상승;
						this.공포감 -= 망가진톱.공포감감소;
						System.out.println("현재 공격력 : " + user.strong + " 현재 공포감 : " + user.공포감);
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						break;
					} else {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("장비가 없습니다.");
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						continue;
					}

				} else {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("현재 무기를 착용한 상태입니다.");
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
				}

			} else if (a == 8) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("돌아갑니다.");
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
				break;
			} else {
				System.out.println("잘못 입력했습니다.");
				continue;
			}

		} // for문끝

	}

	public void 아이템버리기() {
		for(;;) {
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
		System.out.println("아이템 버리기 선택");
		System.out.println("버리고 싶은 아이템의 이름을 하나 입력해주세요.");
		System.out.println("현재 아이템 : " + this.inventory);
		System.out.println("");
		System.out.println("뒤로가기는 뒤로가기를 입력해주세요.");
		System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
		System.out.println("\n\n\n");
		Scanner scanner = new Scanner(System.in);
		String 아이템 = scanner.nextLine();
		if (this.inventory.contains(아이템)) {
			this.inventory.remove(아이템);
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("아이템을 버렸습니다.");
			System.out.println("현재 아이템 : " + this.inventory);
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
			break;
		} else if("뒤로가기".equals(아이템)) {
			break;
		} else {
			System.out.println("잘못 입력했습니다.\n");
			continue;
		}
		}//for문 끝
	}//메서드 끝

	// 수동전투
	public void 숲속수동전투(User user) {
		Scanner scanner = new Scanner(System.in);
		// 숲속 수동전투
		System.out.println("수동전투 선택\n");
		Loop1: for (int i = 0; i < 3; i++) {
//			음악 전투음악 = new 음악("FightMusic.mp3", true);
//			전투음악.start();
			// 동물 랜덤 선택
			animal = 시스템.숲속랜덤();
			System.out.println("\n야생의 " + animal.name + "이/가 나타났다!\n");

			// 유저 공포감 확인
			if (user.공포감 >= 80) {
				시스템.공포감확인(user);
				continue; // 다음전투
			}

			for (;;) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("1. 공격한다 2.도망친다.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int runOr = scanner.nextInt();
				if (runOr == 1) {
					break;
				} else if (runOr == 2) {
					user.runAway(user);
//					전투음악.close();
					continue Loop1; // 다음전투
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
			} // 공격 도망for문 끝

			// 동물자동공격 쓰레드
			Thread forestThread = new Thread(new 동물자동공격(user, animal));
			forestThread.start();
			user.공격선택(user, animal);

//			전투음악.close();
		}
	}

	public void 늪수동전투(User user) {
		Scanner scanner = new Scanner(System.in);
		// 숲속 수동전투
		System.out.println("수동전투 선택\n");
		Loop1: for (int i = 0; i < 3; i++) {
//			음악 전투음악 = new 음악("FightMusic.mp3", true);
//			전투음악.start();
			// 동물 랜덤 선택
			animal = 시스템.늪랜덤();
			System.out.println("\n야생의 " + animal.name + "이/가 나타났다!\n");

			// 유저 공포감 확인
			if (user.공포감 >= 80) {
				시스템.공포감확인(user);
				continue; // 다음전투
			}

			for (;;) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("1. 공격한다 2.도망친다.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int runOr = scanner.nextInt();
				if (runOr == 1) {
					break;
				} else if (runOr == 2) {
					user.runAway(user);
//					전투음악.close();
					continue Loop1; // 다음전투
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
			} // 공격 도망for문 끝

			// 동물자동공격 쓰레드
			Thread 늪Thread = new Thread(new 동물자동공격(user, animal));
			늪Thread.start();
			user.공격선택(user, animal);

//			전투음악.close();
		}

	}

	public void 해변수동전투(User user) {
		Scanner scanner = new Scanner(System.in);
		// 해변 수동전투
		System.out.println("수동전투 선택\n");
		Loop1: for (int i = 0; i < 3; i++) {
//			음악 전투음악 = new 음악("FightMusic.mp3", true);
//			전투음악.start();
			// 동물 랜덤 선택
			animal = 시스템.해변랜덤();
			System.out.println("\n야생의 " + animal.name + "이/가 나타났다!\n");

			// 유저 공포감 확인
			if (user.공포감 >= 80) {
				시스템.공포감확인(user);
				continue; // 다음전투
			}

			for (;;) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("1. 공격한다 2.도망친다.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int runOr = scanner.nextInt();
				if (runOr == 1) {
					break;
				} else if (runOr == 2) {
					user.runAway(user);
//					전투음악.close();
					continue Loop1; // 다음전투
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
			} // 공격 도망for문 끝

			// 동물자동공격 쓰레드
			Thread 해변Thread = new Thread(new 동물자동공격(user, animal));
			해변Thread.start();
			user.공격선택(user, animal);

//			전투음악.close();
		}

	}

	public void 파손된리조트수동전투(User user) {
		Scanner scanner = new Scanner(System.in);
		// 파손된리조트 수동전투
		System.out.println("수동전투 선택\n");
		Loop1: for (int i = 0; i < 3; i++) {
//			음악 전투음악 = new 음악("FightMusic.mp3", true);
//			전투음악.start();
			// 동물 랜덤 선택
			animal = 시스템.파손된리조트랜덤();
			System.out.println("\n야생의 " + animal.name + "이/가 나타났다!\n");

			// 유저 공포감 확인
			if (user.공포감 >= 80) {
				시스템.공포감확인(user);
				continue; // 다음전투
			}

			for (;;) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("1. 공격한다 2.도망친다.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int runOr = scanner.nextInt();
				if (runOr == 1) {
					break;
				} else if (runOr == 2) {
					user.runAway(user);
//					전투음악.close();
					continue Loop1; // 다음전투
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
			} // 공격 도망for문 끝

			// 동물자동공격 쓰레드
			Thread 파손된리조트Thread = new Thread(new 동물자동공격(user, animal));
			파손된리조트Thread.start();
			user.공격선택(user, animal);

//			전투음악.close();
		}

	}

	public void 파손된배수동전투(User user) {
		Scanner scanner = new Scanner(System.in);
		// 파손된배 수동전투
		System.out.println("수동전투 선택\n");
		Loop1: for (int i = 0; i < 3; i++) {
//			음악 전투음악 = new 음악("FightMusic.mp3", true);
//			전투음악.start();
			// 동물 랜덤 선택
			animal = 시스템.파손된배랜덤();
			System.out.println("\n야생의 " + animal.name + "이/가 나타났다!\n");

			// 유저 공포감 확인
			if (user.공포감 >= 80) {
				시스템.공포감확인(user);
				continue; // 다음전투
			}

			for (;;) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("1. 공격한다 2.도망친다.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int runOr = scanner.nextInt();
				if (runOr == 1) {
					break;
				} else if (runOr == 2) {
					user.runAway(user);
//					전투음악.close();
					continue Loop1; // 다음전투
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
			} // 공격 도망for문 끝

			// 동물자동공격 쓰레드
			Thread 파손된배Thread = new Thread(new 동물자동공격(user, animal));
			파손된배Thread.start();
			user.공격선택(user, animal);

//			전투음악.close();
		}
	}

}

//백수 캐릭터 클래스
class NonWork extends User {

	// 생성자
	NonWork() {
		this.name = "백수";
		this.hp = 100;
		this.mental = 100;
		this.strong = 10;
		this.clean = 100;
		this.hunger = 100;
		this.thirsty = 0;
		this.공포감 = 0;
		this.skill = "뒹굴거리기(체력 회복)";
		this.skill2 = "자소서던지기(공격)";
		this.skill3 = "토익스피킹(공격)";
	}

	// 백수 스킬 뒹굴거리기 - 체력 회복, 정신력 감소
	public void skill(User user, Animal animal) {
		System.out.println("스킬 뒹굴거리기를 사용합니다.");
		System.out.println("정신력을 5 소모합니다.\n");
		System.out.println(user.name + "가 드러누워서 뒹굴거리기 시작합니다.");
		System.out.println("왠지 모르게 치유되는 기분입니다.\n");
		System.out.println("체력이 30 증가합니다.");
		user.hp += 30;
		user.mental -= 5;
		System.out.println("현재 체력 = " + user.hp + "\n" + "현재 정신력 = " + user.mental + "\n");
	}

	// 자소서던지기
	public void skill2(User user, Animal animal) {
		System.out.println("스킬 자소서 던지기를 사용합니다.");
		System.out.println("정신력을 5 소모합니다.");
		user.mental -= 5;
		System.out.println("현재 정신력 : " + user.mental + "\n");
		System.out.println("야..니가 취준을 알어?");
		System.out.println("지금껏 쓴 자소서만 100개는 되겠다!");
		System.out.println("한을 담은 자소서를 던졌습니다!\n");
		user.attack(user, animal);
	}

	// 토익스피킹
	public void skill3(User user, Animal animal) {
		System.out.println("스킬 토익스피킹을 사용합니다.");
		System.out.println("정신력을 5 소모합니다.");
		user.mental -= 5;
		System.out.println("현재 정신력 : " + user.mental + "\n");
		System.out.println("...have...magazines...tabel....area");
		System.out.println("WE CAN'T CHANGE THE AREA TO MAKE IT ONLY FOR KIDS USE!!!\n");
		user.attack(user, animal);
	}

}

//직장인 캐릭터 클래스
class Work extends User {
	// 생성자
	Work() {
		this.name = "직장인";
		this.hp = 80;
		this.mental = 80;
		this.strong = 9;
		this.clean = 100;
		this.hunger = 100;
		this.thirsty = 0;
		this.공포감 = 0;
		this.skill = "아아메 마시기(체력 회복)";
		this.skill2 = "칼퇴하기(낮은 확률 즉사)";
		this.skill3 = "자기개발(공격)";
	}

	// 직장인 스킬 아아메 마시기(적 이름) - 체력 증가,포만감 증가, 정신력 감소
	public void skill(User user, Animal animal) {
		System.out.println("스킬 아아메 마시기를 사용합니다.");
		System.out.println("정신력을 5 소모합니다.");
		user.mental -= 5;
		System.out.println("현재 정신력 = " + user.mental + "\n");
		System.out.println(user.name + "이 아이스 아메리카노를 마십니다.");
		System.out.println("카페인이 도는 것이 느껴집니다.\n");
		System.out.println("체력이 30 증가합니다. \n포만감이 10 증가합니다.");
		user.hp += 30;
		user.hunger += 10;
		System.out.println("현재 체력 : " + user.hp + "\n" + "현재 포만감 : " + user.hunger + "\n");
	}

	// 칼퇴하기 낮은 확률 즉사
	public void skill2(User user, Animal animal) {
		System.out.println("스킬 칼퇴하기를 사용합니다.");
		System.out.println("정신력을 5 소모합니다.");
		user.mental -= 5;
		System.out.println("현재 정신력 : " + user.mental + "\n");
		System.out.println("저..먼저...들어가보겠습니다!!!!!");
		Random rnd = new Random();
		if (rnd.nextInt(10) < 1) {
			System.out.println("칼퇴에 성공했습니다!");
			animal.hp = 0;
		} else {
			System.out.println("칼퇴에 실패했습니다..");
		}
	}

	// 자기개발
	public void skill3(User user, Animal animal) {
		System.out.println("스킬 자기개발을 사용합니다.");
		System.out.println("정신력을 5 소모합니다.");
		user.mental -= 5;
		System.out.println("현재 정신력 : " + user.mental + "\n");
		System.out.println("취직하면 끝인거 같지..?");
		System.out.println("ㅋ");
		System.out.println("취직하면 이제 다시 시작이야...!\n");
		this.attack(user, animal);
	}

}

//할머니 캐릭터 클래스
class Grand extends User {

	// 생성자
	Grand() {
		this.name = "할머니";
		this.hp = 60;
		this.mental = 60;
		this.strong = 8;
		this.clean = 100;
		this.hunger = 100;
		this.thirsty = 0;
		this.공포감 = 0;
		this.skill = "셀프물리치료(체력 회복)";
		this.skill2 = "옛날 이야기 하기(공격)";
		this.skill3 = "동정심 유발(공격)";
	}

	// 셀프물리치료
	public void skill(User user, Animal animal) {
		System.out.println("스킬 셀프물리치료를 사용합니다.");
		System.out.println("정신력을 5 소모합니다.");
		user.mental -= 5;
		System.out.println("현재 정신력 : " + user.mental + "\n");
		System.out.println("아이고 삭신이야");
		System.out.println("허리를 땡겨주고...");
		System.out.println("여기도 풀어주면.. 어우 이제 좀 살겠네.\n");
		System.out.println("체력을 30회복합니다.");
		user.hp += 30;
		System.out.println("현재 체력 : " + user.hp);
	}

	// 할머니 스킬 옛날 이야기 하기
	public void skill2(User user, Animal animal) {
		System.out.println("스킬 옛날 이야기 하기를 사용합니다.");
		System.out.println("정신력을 5 소모합니다.");
		user.mental -= 5;
		System.out.println("현재 정신력 = " + user.mental + "\n");
		System.out.println(user.name + "가 갑자기 옛날 얘기를 하기 시작합니다.");
		System.out.println("나때는 말이야~! 이런 것도 없었어~! ");
		System.out.println("요오즘 애들은 고마운줄도 몰라~! 노오력을 안해 노오력을~!!!!!");
		System.out.println("적의 동공이 흔들립니다.\n");
		this.attack(user, animal);
	}

	// 동정심유발
	public void skill3(User user, Animal animal) {
		System.out.println("스킬 동정심 유발을 사용합니다.");
		System.out.println("정신력을 5 소모합니다.");
		user.mental -= 5;
		System.out.println("현재 정신력 : " + user.mental + "\n");
		System.out.println("어? 아이템이 떨어졌네");
		System.out.println("아휴..안그래도 허리가...아픈데...");
		System.out.println("에휴휴휴....");
		System.out.println("아이고 허리야! 아이고 내 허리!");
		System.out.println("적이 방심합니다.\n");
		this.attack(user, animal);
	}

}

class Animal extends Character {
	// 변수
	String item;

	// 기본생성자
	Animal() {
	}

	Animal(String 이름, String 아이템) {
		this.name = 이름;
		this.item = 아이템;
	}

	public void attack(User user, Animal animal, 시스템 시스템) {
	}

	public void attack2(User user, Animal animal) {
	}

	public void attack3(User user, Animal animal) {
	}

	// 아이템 드랍
	public void itemDrop(Animal animal) {
		System.out.println(animal.name + "이/가 " + animal.item + " 을/를 떨어뜨렸습니다.");
	}

}

class 뱀 extends Animal {
	뱀() {
		this.name = "뱀";
		this.hp = 50;
		this.strong = 3;
		this.item = "뱀허물";

	}

	// 물기 체력저하, 독확률
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "뱀이 혀를 낼름거립니다.");
		System.out.println("\t\t\t" + "뱀에게 물렸습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
		시스템.독확률30(user);
	}

	// 꼬리흔들기 정신력하락
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "뱀이 꼬리를 살랑거립니다.");
		System.out.println("\t\t\t" + "\"뭐지..갑자기 왜 이렇게 멍하지..\"\n");
		System.out.println("\t\t\t" + "정신력이 하락합니다.");
		user.mental -= 5;
		System.out.println("\t\t\t" + "플레이어 정신력 : " + user.mental);
	}

	// 조르기 체력저하
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "뱀이 혀를 낼름거립니다.");
		System.out.println("\t\t\t" + "뱀이 빠른 속도로 다가옵니다.");
		System.out.println("\t\t\t" + "뱀이 몸을 옥죄기 시작합니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

}

class 원숭이 extends Animal {
	// 원숭이가 훔쳐간 아이템을 넣을 리스트

	원숭이() {
		this.name = "원숭이";
		this.hp = 50;
		this.strong = 3;
		this.item = "원숭이꼬리";
	}

	// 엉덩이 춤
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "원숭이가 갑자기 뒤돌아서 춤을 춥니다.");
		System.out.println("\t\t\t" + "\"원숭이 엉덩이는 빨개...빨가면 사과...사과....먹고싶다...\"\n");
		System.out.println("\t\t\t" + "포만감이 3 하락합니다.");
		user.hunger -= 3;
		System.out.println("\t\t\t" + "플레이어 포만감 : " + user.hunger);
		System.out.println("\t\t\t" + "원숭이의 엉덩이가 점점 다가옵니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

	// 훔치기(나쁜손)
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "원숭이가 음흉하게 웃습니다.");
		System.out.println("\t\t\t" + "\"ㅋㅋㅋㅋ\"");
		System.out.println("\t\t\t" + "불안합니다.");
		System.out.println("\t\t\t" + "원숭이가 갑자기 달려듭니다!\n");
		try {
			user.inventory.remove(0);
			System.out.println("\t\t\t" + "주머니가 가벼운듯한 느낌이 듭니다.");
			System.out.println("\t\t\t" + "\"설마...\"");
			System.out.println("\t\t\t" + "원숭이의 나쁜손이 아이템을 훔쳐갔습니다!");
			System.out.println("\t\t\t" + "플레이어 아이템 : " + user.inventory + "\n");
		} catch (Exception e) {
			System.out.println("\t\t\t" + "훔쳐갈 아이템이 없습니다.\n");
		}
	}

	// 바나나먹기
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "원숭이가 나무로 올라갑니다.");
		System.out.println("\t\t\t" + "바나나를 먹기 시작합니다.");
		System.out.println("\t\t\t" + "배가 부른지 트름을 합니다.\n");
		animal.hp += 20;
		System.out.println("\t\t\t" + "원숭이가 체력을 회복했습니다.");
	}

}

class 피라냐 extends Animal {
	피라냐() {
		this.name = "피라냐";
		this.hp = 50;
		this.strong = 3;
		this.item = "피라냐이빨";
	}

	// 물기
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "피라냐가 이빨을 드러냅니다.");
		System.out.println("\t\t\t" + "콱");
		System.out.println("\t\t\t" + "피라냐에게 물렸습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

	// 이빨치기 정신력 하락
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "피라냐가 이빨을 드러냅니다.");
		System.out.println("\t\t\t" + "딱");
		System.out.println("\t\t\t" + "딱딱");
		System.out.println("\t\t\t" + "딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱딱");
		System.out.println("\t\t\t" + "몸에 소름이 돋습니다..\n");
		System.out.println("\t\t\t" + "정신력이 하락합니다.");
		user.mental -= 3;
		System.out.println("\t\t\t" + "플레이어 정신력 : " + user.mental);
	}

	// 몸통박치기 체력저하
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "피라냐가 물 속에서 사라졌습니다.");
		System.out.println("\t\t\t" + "\"뭐야...어디갔지\"");
		System.out.println("\t\t\t" + "갑자기 물 속에서 피라냐가 뛰어올랐습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

}

class 단데기 extends Animal {
	단데기() {
		this.name = "단데기";
		this.hp = 50;
		this.strong = 0;
		this.item = "단데기허물";
	}

	// 단단해지기
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "단데기가 몸을 웅크립니다.");
		System.out.println("\t\t\t" + "단데기의 체력이 상승합니다.");
		animal.hp += 10;
	}

	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "단데기가 몸을 웅크립니다.");
		System.out.println("\t\t\t" + "단데기의 체력이 상승합니다.");
		animal.hp += 10;
	}

	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "단데기가 몸을 웅크립니다.");
		System.out.println("\t\t\t" + "단데기의 체력이 상승합니다.");
		animal.hp += 10;
	}

}

class 사람 extends Animal {
	사람() {
		this.name = "사람?";
		this.hp = 50;
		this.strong = 3;
		this.item = "뼈";
	}

	// 할퀴기 체력저하 독
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "사람처럼 생긴 생물이 손톱을 드러냅니다.");
		System.out.println("\t\t\t" + "키키킥");
		System.out.println("\t\t\t" + "손톱으로 할퀴었습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
		시스템.독확률30(user);
	}

	// 웃기 공포감업
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "사람처럼 생긴 생물이 웃기 시작합니다.");
		System.out.println("\t\t\t" + "..키...키킥...");
		System.out.println("\t\t\t" + "키키킥...");
		System.out.println("\t\t\t" + "키키킥키키킥키키킥키키킥키키킥키키킥키키킥키키킥키키킥키키킥키키킥키키킥키키킥");
		System.out.println("\t\t\t" + "플레이어의 동공이 흔들립니다.\n");
		System.out.println("\t\t\t" + "공포감이 상승합니다.");
		user.공포감 += 5;
		System.out.println("\t\t\t" + "플레이어 공포감 : " + user.공포감);
	}

	// 말걸기 정신력하락
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "사람처럼 생긴 생물이 입을 움직입니다.");
		System.out.println("\t\t\t" + "..녕....녕..?");
		System.out.println("\t\t\t" + "안...녕? 안녕...?");
		System.out.println("\t\t\t" + "안녕안녕안녕안녕안녕안녕안녕너누구야너누구야너누구야너누구야너누구야너누구야너누구야");
		System.out.println("\t\t\t" + "플레이어의 동공이 흔들립니다.\n");
		System.out.println("\t\t\t" + "정신력이 하락합니다.");
		user.mental -= 5;
		System.out.println("\t\t\t" + "플레이어 정신력 : " + user.mental);
	}

}

class 박쥐 extends Animal {
	박쥐() {
		this.name = "박쥐";
		this.hp = 50;
		this.strong = 3;
		this.item = "박쥐날개";
	}

	// 날개치기 공포감상승 체력저하
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "박쥐가 날개를 폅니다.");
		System.out.println("\t\t\t" + "예상보다 큰 크기에 위압감을 느낍니다.");
		System.out.println("\t\t\t" + "박쥐가 날개로 내려칩니다!\n");
		System.out.println("\t\t\t" + "공포감이 상승합니다.");
		user.공포감 += 5;
		System.out.println("\t\t\t" + "플레이어 공포감 : " + user.공포감);
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

	// 잠자기 체력회복
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "박쥐가 날개를 접고 거꾸로 매달립니다");
		System.out.println("\t\t\t" + "하품을 합니다.");
		System.out.println("\t\t\t" + "잠깐의 휴식으로 체력을 회복합니다.\n");
		animal.hp += 20;
	}

	// 초음파 체력저하
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "박쥐가 초음파를 쏩니다.");
		System.out.println("\t\t\t" + "\"뭐지?\"");
		System.out.println("\t\t\t" + "\"악! 내 귀!\"\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

}

class 악어 extends Animal {
	악어() {
		this.name = "악어";
		this.hp = 60;
		this.strong = 4;
		this.item = "악어가죽";
	}

	// 노려보기 공포감상승 정신력하락
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "악어가 가만히 노려봅니다.");
		System.out.println("\t\t\t" + "아무런 움직임이 없지만 악어의 눈빛에 위압감을 느낍니다.\n");
		System.out.println("\t\t\t" + "공포감이 상승합니다.");
		System.out.println("\t\t\t" + "정신력이 하락합니다.");
		user.공포감 += 5;
		user.mental -= 5;
		System.out.println("\t\t\t" + "플레이어 공포감 : " + user.공포감);
		System.out.println("\t\t\t" + "플레이어 정신력 : " + user.mental);
	}

	// 물기 체력저하
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "악어가 빠른 속도로 달려듭니다.");
		System.out.println("\t\t\t" + "\"어?어?\"");
		System.out.println("\t\t\t" + "악어가 물었습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

	// 꼬리치기 체력저하
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "악어가 빠른 속도로 달려듭니다.");
		System.out.println("\t\t\t" + "\"어?어?\"");
		System.out.println("\t\t\t" + "악어의 꼬리치기!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

}

class 재규어 extends Animal {
	재규어() {
		this.name = "재규어";
		this.hp = 70;
		this.strong = 4;
		this.item = "재규어이빨";
	}

	// 입맛다시기 공포감상승 정신력하락
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "재규어가 빤히 쳐다봅니다.");
		System.out.println("\t\t\t" + "마치 견적을 내는 것 같습니다...");
		System.out.println("\t\t\t" + "재규어의 입에서 침이 떨어집니다.");
		System.out.println("\t\t\t" + "재규어가 입맛을 다십니다.\n");
		System.out.println("\t\t\t" + "공포감이 상승합니다. \n정신력이 하락합니다.");
		user.공포감 += 5;
		user.mental -= 5;
		System.out.println("\t\t\t" + "플레이어 공포감 : " + user.공포감);
		System.out.println("\t\t\t" + "플레이어 정신력 : " + user.mental);
	}

	// 할퀴기 체력하락
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "재규어가  날카로운 발톱을 드러냅니다.");
		System.out.println("\t\t\t" + "재규어의 움직임을 쫒기 힘듭니다.");
		System.out.println("\t\t\t" + "재규어가 발톱으로 할퀴었습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

	// 물어뜯기 체력하락
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "재규어가  날카로운 이빨을 드러냅니다.");
		System.out.println("\t\t\t" + "재규어의 움직임을 쫒기 힘듭니다.");
		System.out.println("\t\t\t" + "재규어가 이빨로 물었습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

}

class 거북이 extends Animal {
	거북이() {
		this.name = "거북이";
		this.hp = 70;
		this.strong = 3;
		this.item = "거북이등딱지";
	}

	// 느리게 걷기 체력회복
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "거북이가 느리게 느리게 느리게 걷기 시작합니다.");
		System.out.println("\t\t\t" + "가만히 보고 있자니 속이 터집니다.\n");
		System.out.println("\t\t\t" + "거북이의 체력이 회복됩니다.");
		animal.hp += 20;

	}

	// 빨리 걷기 공격력상승
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "거북이가 갑자기 빨리걷기 시작합니다.");
		System.out.println("\t\t\t" + "\"헐 뭐야\"");
		System.out.println("\t\t\t" + "믿기 힘든 정도의 속도입니다!\n");
		System.out.println("\t\t\t" + "거북이의 공격력이 상승합니다.");
		animal.strong += 2;
	}

	// 몸통박치기 체력저하
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "거북이가 두 걸음 물러납니다.");
		System.out.println("\t\t\t" + "\"??\"");
		System.out.println("\t\t\t" + "추진력을 얻기 위함입니다.");
		System.out.println("\t\t\t" + "거북이가 튀어올랐습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

}

class 쥐 extends Animal {
	쥐() {
		this.name = "쥐";
		this.hp = 30;
		this.strong = 2;
		this.item = "쥐고기";
	}

	// 할퀴기 체력저하 독
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "쥐가 손을 풉니다.");
		System.out.println("\t\t\t" + "전력질주합니다.");
		System.out.println("\t\t\t" + "쥐가 손톱으로 할퀴었습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
		시스템.독확률30(user);
	}

	// 물기 체력저하
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "쥐가 이빨을 드러냅니다.");
		System.out.println("\t\t\t" + "전력질주합니다.");
		System.out.println("\t\t\t" + "쥐에게 물렸습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

	// 울기 정신력하락
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "쥐가 울기 시작합니다.");
		System.out.println("\t\t\t" + "찍찍");
		System.out.println("\t\t\t" + "찍찍찍");
		System.out.println("\t\t\t" + "쥐 울음소리는 언제 들어도 소름끼칩니다.\n");
		System.out.println("\t\t\t" + "정신력이 하락합니다.");
		user.mental -= 5;
		System.out.println("\t\t\t" + "플레이어 정신력 : " + user.mental);
	}

}

class 고릴라 extends Animal {
	고릴라() {
		this.name = "고릴라";
		this.hp = 60;
		this.strong = 4;
		this.item = "고릴라가죽";
	}

	// 가슴치기 공포감상승 정신력하락
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "고릴라가 빤히 쳐다봅니다.");
		System.out.println("\t\t\t" + "고릴라가 울부짖으며 자신의 가슴을 미친듯이 두들깁니다.");
		System.out.println("\t\t\t" + "식은땀이 삐질 흐릅니다.\n");
		System.out.println("\t\t\t" + "공포감이 상승합니다. \n정신력이 하락합니다.");
		user.공포감 += 5;
		user.mental -= 5;
		System.out.println("\t\t\t" + "플레이어 공포감 : " + user.공포감);
		System.out.println("\t\t\t" + "플레이어 정신력 : " + user.mental);
	}

	// 주먹날리기 체력저하
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "고릴라가 주먹을 말아쥡니다.");
		System.out.println("\t\t\t" + "목을 풀면서 어깨를 돌립니다.");
		System.out.println("\t\t\t" + "퍽");
		System.out.println("\t\t\t" + "고릴라의 주먹에 맞았습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

	// 발차기 체력저하
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "고릴라가 콩콩 뛰면서 바라봅니다.");
		System.out.println("\t\t\t" + "고릴라가 발목을 풀어줍니다.");
		System.out.println("\t\t\t" + "퍽");
		System.out.println("\t\t\t" + "고릴라의 발차기에 맞았습니다!\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

}

class 머리가없는사람 extends Animal {
	머리가없는사람() {
		this.name = "머리가 없는 사람?";
		this.hp = 70;
		this.strong = 4;
		this.item = "뼈";
	}

	// 가만히있기 정신력하락
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "사람처럼 생긴 생물이 움직임을 멈춥니다.");
		System.out.println("\t\t\t" + "머리가 없는데 마치 이쪽을 바라보는 것 같습니다.");
		System.out.println("\t\t\t" + "식은땀이 삐질 흐릅니다.\n");
		System.out.println("\t\t\t" + "정신력이 하락합니다.");
		user.mental -= 5;
		System.out.println("\t\t\t" + "플레이어 정신력 : " + user.mental);
	}

	// 마구때리기 체력저하
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "사람처럼 생긴 생물이 마구 때리기 시작합니다.");
		System.out.println("\t\t\t" + "모두 막을 수 없습니다.\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

	// 머리만지기 공포감업
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "사람처럼 생긴 생물이 머리가 있던 자리를 쓰다듬습니다.");
		System.out.println("\t\t\t" + "계속 쓰다듬습니다.");
		System.out.println("\t\t\t" + "이상한 기분이 듭니다.\n");
		System.out.println("\t\t\t" + "공포감이 상승합니다.");
		user.공포감 += 5;
		System.out.println("\t\t\t" + "플레이어 공포감 : " + user.공포감);
	}

}

class 한몸이된두사람 extends Animal {
	한몸이된두사람() {
		this.name = "한 몸이 된 두 사람?";
		this.hp = 120;
		this.strong = 10;
		this.item = "해골바가지";
	}

	// 손잡기 공격력상승
	public void attack(User user, Animal animal, 시스템 시스템) {
		System.out.println("\t\t\t" + "두 사람?이 각자의 손을 듭니다.");
		System.out.println("\t\t\t" + "두 사람?이 손을 잡습니다.\n");
		System.out.println("\t\t\t" + "한 몸이 된 두 사람?의 공격력이 상승합니다.");
		animal.strong += 4;
	}

	// 내려찍기 체력저하
	public void attack2(User user, Animal animal) {
		System.out.println("\t\t\t" + "두 사람?이 네 발로 뛰어 올랐습니다.");
		System.out.println("\t\t\t" + "믿을 수 없는 높이입니다.");
		System.out.println("\t\t\t" + "떨어지기 시작합니다.\n");
		user.hp = user.hp - animal.randomStrong(animal);
		System.out.println("\t\t\t" + "플레이어 체력 : " + user.hp);
	}

	// 손뼉치기 체력회복
	public void attack3(User user, Animal animal) {
		System.out.println("\t\t\t" + "두 사람?이 각자의 손을 듭니다.");
		System.out.println("\t\t\t" + "서로 손을 마주하고 손뼉을 치기 시작합니다.");
		System.out.println("\t\t\t" + "짝짝짝짝짝짝짝짝짝짝짝짝짝짝짝짝짝짝짝짝짝짝짝\n");
		System.out.println("\t\t\t" + "두 사람?의 체력이 회복됩니다.");
		animal.hp += 40;
	}

}

class SpecialMonkey extends Animal {
	SpecialMonkey() {
		this.name = "원숭이?";
	}

	public void fistHello() {
		System.out.println();
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\n" + "\t\t\t" + "원숭이? - 인간이다 우끼!" + "\n");
		System.out.println("플레이어 - 엄마야! 워..원숭이가 말을 하네?" + "\n");
		System.out.println("\t\t\t" + "원숭이? - 난 그냥 원숭이 아니다! 우끼! 나는 특별하다 우끼!" + "\n");
		System.out.println("플레이어 - 내가 미쳐도 단단히 미쳤구나.." + "\n");
		System.out.println("\t\t\t" + "원숭이? - 이상한 인간이다 우끼! 인간 물건있다 우끼!" + "\n");
		System.out.println("플레이어 - 뭐래는거야.." + "\n");
		System.out.println("\t\t\t" + "원숭이? - 나는 한다! 교환! 우끼!\n");
		System.out.println("\t\t\t" + "원숭이? - 다시 찾아온다! 원숭이! 우끼!\n");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

	}

	// 인사
	public void hello() {
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		System.out.println("\n부스럭 부스럭");
		System.out.println("발소리가 들립니다.");
		System.out.println("\t\t\t" + "원숭이? - 안녕 우끼! 한다! 교환! 우끼!\n");
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
	}

	// 상점 물건
	public void shopView1() {
		System.out.println("\n1.녹슨칼 - 뱀허물\n" + "2.빵 - 단데기허물, 나무열매\n" + "3.햄버거세트 - 메기수염, 블랙베리, 깃털\n" + "4.교환하지 않는다.\n");
	}

	public void shopView2() {
		System.out.println(
				"\n1.라면 - 스노우베리 , 진흙\n" + "2.토끼고기  - 나무가지, 나무열매\n" + "3.열대어 지느러미 - 돌, 나무판자\n" + "4.교환하지 않는다.\n");
	}

	public void shopView3() {
		System.out.println("\n1.조명탄 - 해골바가지,동전, 조개껍질, 테니스라켓, 열대어 지느러미, 토끼꼬치\n" + "2.테니스라켓 - 동전, 메기수염, 진흙\n"
				+ "3.수액 - 단데기허물\n" + "4.교환하지 않는다.\n");
	}
}

class 시스템 {

	// 객체
	Animal animal;
	User user;

	// 물고기 랜덤
	public Animal 물고기랜덤() {
		Random rnd = new Random();
		int a = rnd.nextInt(100);
		if (a < 50) {
			// 피라미객체
			Animal 피라미 = new Animal("피라미", "피라미");
			animal = 피라미;
		} else if (a < 70) {
			// 열대어객체
			Animal 열대어 = new Animal("열대어", "열대어지느러미");
			animal = 열대어;
		} else if (a < 90) {
			// 메기객체
			Animal 메기 = new Animal("메기", "메기수염");
			animal = 메기;
		} else if (a < 100) {
			// 메기객체
			Animal 뱀장어 = new Animal("뱀장어", "뱀장어고기");
			animal = 뱀장어;
		}
		return animal;
	}

	// 동물 랜덤
	public Animal 숲속랜덤() {
		Random rnd = new Random();
		int a = rnd.nextInt(100);
		if (a < 25) {
			// 단데기객체
			단데기 단데기 = new 단데기();
			animal = 단데기;
		} else if (a < 50) {
			// 뱀객체
			뱀 뱀 = new 뱀();
			animal = 뱀;
		} else if (a < 75) {
			// 사람?객체
			사람 사람 = new 사람();
			animal = 사람;
		} else if (a < 100) {
			// 원숭이객체
			원숭이 원숭이 = new 원숭이();
			animal = 원숭이;
		}
		return animal;
	}

	public Animal 늪랜덤() {
		Random rnd = new Random();
		int a = rnd.nextInt(100);
		if (a < 25) {
			// 뱀객체
			뱀 뱀 = new 뱀();
			animal = 뱀;
		} else if (a < 50) {
			// 악어객체
			악어 악어 = new 악어();
			animal = 악어;
		} else if (a < 75) {
			// 박쥐객체
			박쥐 박쥐 = new 박쥐();
			animal = 박쥐;
		} else if (a < 100) {
			// 단데기객체
			단데기 단데기 = new 단데기();
			animal = 단데기;
		}
		return animal;
	}

	public Animal 파손된배랜덤() {
		Random rnd = new Random();
		int a = rnd.nextInt(100);
		if (a < 25) {
			// 피라냐객체
			피라냐 피라냐 = new 피라냐();
			animal = 피라냐;
		} else if (a < 50) {
			// 쥐객체
			쥐 쥐 = new 쥐();
			animal = 쥐;
		} else if (a < 75) {
			// 고릴라객체
			고릴라 고릴라 = new 고릴라();
			animal = 고릴라;
		} else if (a < 100) {
			// 사람?객체
			사람 사람 = new 사람();
			animal = 사람;
		}
		return animal;
	}

	public Animal 파손된리조트랜덤() {
		Random rnd = new Random();
		int a = rnd.nextInt(100);
		if (a < 30) {
			// 사람?객체
			사람 사람 = new 사람();
			animal = 사람;
		} else if (a < 60) {
			// 재규어객체
			재규어 재규어 = new 재규어();
			animal = 재규어;
		} else if (a < 90) {
			// 머리가없는사람?객체
			머리가없는사람 머리가없는사람 = new 머리가없는사람();
			animal = 머리가없는사람;
		} else if (a < 100) {
			// 한몸이 된 두 사람?객체
			한몸이된두사람 한몸이된두사람 = new 한몸이된두사람();
			animal = 한몸이된두사람;
		}
		return animal;
	}

	public Animal 해변랜덤() {
		Random rnd = new Random();
		int a = rnd.nextInt(100);
		if (a < 25) {
			// 거북이객체
			거북이 거북이 = new 거북이();
			animal = 거북이;
		} else if (a < 50) {
			// 원숭이객체
			원숭이 원숭이 = new 원숭이();
			animal = 원숭이;
		} else if (a < 75) {
			// 고릴라객체
			고릴라 고릴라 = new 고릴라();
			animal = 고릴라;
		} else if (a < 100) {
			// 피라냐객체
			피라냐 피라냐 = new 피라냐();
			animal = 피라냐;
		}
		return animal;
	}

	// 행동 선택시 정신력 하락
	public void 행동시작(User user) {
		System.out.println("정신력이 10 하락합니다.");
		user.mental -= 10;
		System.out.println("현재 정신력 : " + user.mental + "\n");
	}

	// 행동 종료시 스탯 하락
//	public void 행동종료(User user) {
//		System.out.println("청결도가 10 하락합니다. \n포만감이 10 하락합니다. \n목마름이 10 증가합니다. \n");
//		user.clean -= 10;
//		user.hunger -= 10;
//		user.thirsty += 10;
//		System.out.println(
//				"현재 청결도 : " + user.clean + "\n" + "현재 포만감 : " + user.hunger + "\n" + "현재 목마름 : " + user.thirsty + "\n");
//	}

	public void 아이템조합보기() {
		System.out.println("아이템 조합 보기 선택\n");
		System.out.println("나무가지+나뭇잎+돌 = 불\r\n" + "나무가지+녹슨칼 = 막대기\r\n" + "나무가지+나뭇잎+나무덩쿨+쥐고기 = 덫\r\n"
				+ "나무가지+나무덩쿨+피라미 = 통발 \r\n" + "허브+ 마리골드 + 치커리 = 해독제\r\n" + "알로에 마리골드 허브 = 체력약\r\n"
				+ "치커리 알로에 허브  = 정신력약\r\n" + "악어가죽 + 나무덩쿨 = 수통\r\n" + "막대기 + 나무덩쿨 = 낚시대\r\n" + "나무가지+수액+뱀허물 = 나무갑옷\r\n"
				+ "나무가지+피라냐이빨+수액 = 나무무기\r\n" + "악어가죽+고릴라가죽+수액 = 가죽갑옷\r\n" + "뼈+등딱지+재규어이빨+수액 = 뼈갑옷\r\n"
				+ "뼈+재규어이빨+깃털+수액 = 뼈무기\r\n" + "나무가지+ 나무판자 +야자나뭇잎  + 나무덩쿨= 텐트");

	}

	public void 요리조합보기() {
		System.out.println("요리 조합 보기 선택");
		System.out.println("나무가지 + 고기 = 굽지않은 꼬치 \r\n" + "불 + 굽지않은꼬치 = 구운꼬치\r\n" + "진흙 + 토끼고기 + 불= 토끼진흙구이\r\n"
				+ "냄비+물+원숭이고기+쥐고기+토끼고기 +불 = 육지고기국\r\n" + "냄비+물+뱀장어고기+피라미+불= 물고기국");
	}

	// 적체력확인
	public void monsterHpCheck(User user, Animal animal) {
//		음악 몬스터다운 = new 음악("MonsterDown.mp3", false);
//		몬스터다운.start();
		System.out.println("사냥에 성공했습니다!");
		animal.itemDrop(animal);
		user.getItem(user, animal);
	}

	// 플레이어체력확인
	public void userHpCheck(User user) {

		if (user.hp <= 0) {
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("체력이 0이 되었습니다.");
			System.out.println("더이상 버틸 수 없습니다...");
			System.out.println("사냥 실패");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//			    배드엔딩 메서드 삽입
			this.hpBadEnd();
		} else {
			System.out.println();
		}

	}

	// 청결도 포만감 목마름 확인
	public void CHTcheck(User user) {
		if (user.clean <= 0) {
			this.cleanBadEnd(); // 엔딩메서드
		} else if (user.hunger <= 0) {
			this.hungerBadEnd(); // 엔딩메서드
		} else if (user.thirsty >= 100) {
			this.thirstyBadEnd(); // 엔딩메서드
		}
	}

	// 배드엔딩
	public void hpBadEnd() {
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("이제 아무것도 하기 싫어...");
		System.out.println("집에 가고싶다.....");
		System.out.println("\n" + "Bad Ending 1 - 집으로 돌아가지 못한 자");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.exit(0);
	}

	public void hungerBadEnd() {
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("먹을 거... 먹을 것 좀 줘.... 배고파 죽겠어....");
		System.out.println("배고...파......음..식......");
		System.out.println("\n" + "Bad Ending 2 - 굶주린 배를 움켜진 자");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.exit(0);
	}

	public void thirstyBadEnd() {
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("으.....물 없어 물..?");
		System.out.println("바다물..은 더...이상 못...마셔......");
		System.out.println("으....어......");
		System.out.println("\n" + "Bad Ending 3 - 말을 잃은 자");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.exit(0);
	}

	public void cleanBadEnd() {
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("윙~ 윙~");
		System.out.println("어우 뭔 벌레가..");
		System.out.println("킁킁");
		System.out.println("아니 이게 무슨 냄새야...? 음식물 쓰레기 냄새가 나...");
		System.out.println("억..냄새때문에 죽을거같아!");
		System.out.println("우웩...우웨에에엑");
		System.out.println("\n" + "Bad Ending 4 - 넌 샤워를 소중히 하지 않았지");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.exit(0);
	}

	public void 늪BadEnd() {
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("아무리 애써도 자꾸만 늪으로 빨려들어갑니다.");
		System.out.println("점점 팔에 힘이 빠지기 시작합니다.");
		System.out.println("이제는 가슴까지 빠졌습니다.");
		System.out.println("숨을 쉬기가 매우 어렵습니다...");
		System.out.println("이제 모든 것을 포기했습니다..");
		System.out.println("차라리 여기서 끝나는게 나을 지도 모릅니다....");
		System.out.println("\n" + "Bad Ending 5 - 늪에 빠진 자");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.exit(0);
	}

	// 스페셜 엔딩
	public void specialEnd() {
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("20XX.X.XX일");
		System.out.println("흐암");
		System.out.println("어우 찌뿌둥 해.. 어제 너무 많이 마셨나봐...");
		System.out.println("우끼우끼우끼");
		System.out.println("어 야 잘잤냐? 난 죽겠어 아주... 너 왜 이렇게 팔팔해 너 뭐 나 몰래 홍삼 챙겨먹냐?");
		System.out.println("우끼우끼! 우끼끼! 우낄낄낄!!");
		System.out.println("저 재수없는 놈.. 야 너도 내 나이 돼봐. 그렇게 마시면 죽어 나는");
		System.out.println("우끼! 우끼! 우끼우끼끼");
		System.out.println("아니 아침부터 뭔 낚시야! 야 같이 가자 좀!!");
		System.out.println("\n" + "Special Ending - 어쨌든 잘 먹고 잘 삽니다");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.exit(0);
	}

	// 굿엔딩
	public void goodEnd() {
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("20XX.X.XX일");
		System.out.println("흐암");
		System.out.println("어우 찌뿌둥 해.. 어제 너무 많이 마셨나봐...");
		System.out.println("아..속 안좋아...");
		System.out.println("그건 그렇고 무인도 꿈은 언제까지 꿀껀지... 벌써 몇년전인데..");
		System.out.println("그때 진짜 장난 아니었는데ㅋㅋㅋ 뱀도 막 때려잡고 말하는 원숭이도 보고");
		System.out.println("아휴 그때가 참....");
		System.out.println("그립긴 개뿔이 그리워!");
		System.out.println("읍! 화장실!");
		System.out.println("우웨에엑");
		System.out.println("\n" + "Good Ending - 컴백홈");
		System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.exit(0);
	}

	public void 공포감확인(User user) {
		if (user.공포감 >= 80) {
			System.out.println("현재 공포감이 80이상입니다.\n");
			user.runAway(user);
		}
	}

//	//인벤토리에 체력믹스 확인
//	public void 체력약확인(User user, 섭취아이템 체력약) {
//		if(user.hp<=20) {
//			if(user.inventory.contains("체력약")) {
//				System.out.println("체력이 낮습니다.");
//				System.out.println("가방에서 체력약을 발견했습니다!");
//				System.out.println("체력약을 섭취합니다.");
//				user.eatYes2(체력약);
//			} else {
//				System.out.println();
//			}
//		} 
//		
//	}
//	
//	//인벤토리에 에너지믹스 확인
//	public void 정신력약확인(User user, 섭취아이템 정신력약) {
//		if(user.mental<=20) {
//			if(user.inventory.contains("정신력약")) {
//				System.out.println("정신력이 낮습니다.");
//				System.out.println("가방에서 정신력약을 발견했습니다!");
//				System.out.println("정신력약을 섭취합니다.");
//				user.eatYes2(정신력약);
//			} else {
//				System.out.println();
//			}
//		} 
//		
//	}

	// 독확률30퍼
	public void 독확률30(User user) {

		if (user.독 == false) {
			Random rnd = new Random();
			if (rnd.nextInt(10) < 10) {
				System.out.println("\t\t\t" + "독에 걸렸습니다!");
				user.독 = true;
				System.out.println("\t\t\t" + "체력이 3 감소합니다.");
				user.hp -= 3;
				System.out.println("\t\t\t" + "현재 플레이어 체력 : " + user.hp);
				Thread 독지속쓰레드 = new Thread(new 독지속(user));
				독지속쓰레드.start();
			}

		} else if (user.독 == true) {
		}

	}

	// 메뉴 (플레이어 행동 선택창)
	public void 메뉴(User user, 시스템 시스템, Event event, SpecialMonkey specialmonkey) {

		// 메뉴 음악 쓰레드
//		음악 메뉴음악 = new 음악("MenuMusic.mp3", true);
//		메뉴음악.start();


		// 메뉴반복 for문
		Loop1 : for (;;) {

			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println("\n" + "<<" + user.현재날짜 + " " + user.현재시간 + ">>" + "\n");
			System.out.println("자, 이제 어떻게 해야할까..\n");
			System.out.println("1.상태 보기 2.인벤토리 보기 3.행동하기 4.식사하기 5.휴식하기 6.도움말");
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
			// 사용자 입력값을 받기 위한 스캐너
			Scanner scanner = new Scanner(System.in);
			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
			}
			int a = scanner.nextInt();
			// 메뉴선택 if문
			if (a == 1) {
				System.out.println("\n상태보기 선택\n");
				user.status(user); // 유저 상태보기 메서드
				continue; // 메뉴반복 for문 처음으로

			} else if (a == 2) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("\n인벤토리 보기 선택\n");
				System.out.println("1.아이템 보기 2.장비 착용 3.아이템 조합 4.아이템 버리기 5.뒤로가기\n");
				System.out.println("원하는 숫자를 입력해주세요.");
				System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int 인벤토리메뉴선택 = scanner.nextInt(); // 인벤토리 메뉴 선택을 받는 스캐너

				// 인벤토리 메뉴 선택 if문

				if (인벤토리메뉴선택 == 1) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템 보기 선택\n");
					System.out.println("현재 아이템 : " + user.inventory);
					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
				} else if (인벤토리메뉴선택 == 2) {
//					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
//					System.out.println("\n장비 착용 선택\n");
					user.장비착용(user);
//					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
//					System.out.println("\n\n\n");
				} else if (인벤토리메뉴선택 == 3) {
//					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
//					System.out.println("\n아이템 조합 선택\n");
					user.아이템조합();
//					System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
//					System.out.println("\n\n\n");
				} else if (인벤토리메뉴선택 == 4) {
//					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
//					System.out.println("\n아이템 버리기 선택\n");
					user.아이템버리기();
//					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
//					System.out.println("\n\n\n");

				} else if (인벤토리메뉴선택 == 5) {
					System.out.println("\n뒤로가기 선택\n");

				} else {
					System.out.println("잘못 입력했습니다.\n");

				}

				continue;

			} else if (a == 3) {

				if ("밤".equals(user.현재시간)) {
					System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
					System.out.println("야간에는 행동을 할 수 없습니다.");
					System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊\n");
					continue;
				}

				if (user.mental <= 0) {
					System.out.println("정신력이 부족합니다.");
					continue;
				} else {
					System.out.println();
				}
				
				for(;;){
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("\n행동하기 선택\n");
				System.out.println("1.사냥 2.탐색 3.낚시 4.뒤로가기\n");
				System.out.println("원하는 숫자를 입력해주세요.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
				// 사용자 입력값을 받기 위한 스캐너
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int 행동선택 = scanner.nextInt();

				// 행동선택별 if문
				if (행동선택 == 1) {
					// 사냥선택
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n사냥 선택\n");
					시스템.행동시작(user); // 행동시작시 정신력감소
					// 맵선택 for문
					for(;;) {
						
					System.out.println("1.숲 속  2.늪  3.파손된 배  4.해변  5.파손된 리조트");
					System.out.println("원하는 장소의 번호를 입력해주세요.\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					while (!scanner.hasNextInt()) {
						scanner.next();
						System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
					}
					int 맵선택 = scanner.nextInt();
					if (맵선택 == 1) {
						// 숲 선택
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
						System.out.println("\n숲 속 선택\n");
						// 전투방식 선택
						for(;;) {
						System.out.println("원하는 전투 유형을 선택해주세요.");
						System.out.println("\n1.자동전투  2.수동전투\n");
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						// 메뉴음악 끔
//						메뉴음악.close();

						while (!scanner.hasNextInt()) {
							scanner.next();
							System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
						}
						int 전투선택 = scanner.nextInt();
						if (전투선택 == 1) {
							System.out.println("자동전투 선택\n");
							
							Thread 숲속자동전투 = new Thread(new 숲속자동전투(user));
							숲속자동전투.start();
							try {
								숲속자동전투.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}				
							
							break Loop1;

						} else if (전투선택 == 2) {
//							System.out.println("수동전투 선택\n");
							
							// 숲속 수동전투
							user.숲속수동전투(user);
							System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
							System.out.println("\n사냥을 완료했습니다.");
							System.out.println("현재 인벤토리 : " + user.inventory);
							System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
							System.out.println("\n\n\n");
							break Loop1;
						} else {
							System.out.println("잘못 입력했습니다.\n");
							continue;
						}
						
						}//전투 선택 for문 끝

					} else if (맵선택 == 2) {
						System.out.println("늪 선택\n");

						event.늪빠짐(user);
						// 전투방식 선택
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
						System.out.println("\n원하는 전투 유형을 선택해주세요.");
						System.out.println("\n1.자동전투  2.수동전투\n");
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
//						메뉴음악.close();

						while (!scanner.hasNextInt()) {
							scanner.next();
							System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
						}
						int 전투선택 = scanner.nextInt();
						if (전투선택 == 1) {
							System.out.println("자동전투 선택\n");
							
							Thread 늪자동전투 = new Thread(new 늪자동전투(user));
							늪자동전투.start();
							try {
								늪자동전투.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							break Loop1;

						} else if (전투선택 == 2) {
							
//							System.out.println("수동전투 선택\n");
							// 늪 수동전투
							user.늪수동전투(user);
							System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
							System.out.println("\n사냥을 완료했습니다.");
							System.out.println("현재 인벤토리 : " + user.inventory);
							System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
							System.out.println("\n\n\n");
							break Loop1;
						} else {
							System.out.println("잘못 입력했습니다.\n");
							continue;
						}

					} else if (맵선택 == 3) {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
						System.out.println("파손된 배 선택\n");
						// 전투방식 선택
						System.out.println("\n원하는 전투 유형을 선택해주세요.");
						System.out.println("\n1.자동전투  2.수동전투\n");
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
//						메뉴음악.close();
						while (!scanner.hasNextInt()) {
							scanner.next();
							System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
						}
						int 전투선택 = scanner.nextInt();
						if (전투선택 == 1) {
							System.out.println("자동전투 선택\n");
							
							Thread 파손된배자동전투 = new Thread(new 파손된배자동전투(user));
							파손된배자동전투.start();
							try {
								파손된배자동전투.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							break Loop1;

						} else if (전투선택 == 2) {
							
//							System.out.println("수동전투 선택\n");
							// 파손된배 수동전투
							user.파손된배수동전투(user);
							System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
							System.out.println("\n사냥을 완료했습니다.");
							System.out.println("현재 인벤토리 : " + user.inventory);
							System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
							System.out.println("\n\n\n");
							break Loop1;
						} else {
							System.out.println("잘못 입력했습니다.\n");
							continue;
						}

					} else if (맵선택 == 4) {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
						System.out.println("해변 선택\n");
						// 전투방식 선택
						System.out.println("\n원하는 전투 유형을 선택해주세요.");
						System.out.println("\n1.자동전투  2.수동전투\n");
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
//						메뉴음악.close();

						while (!scanner.hasNextInt()) {
							scanner.next();
							System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
						}
						int 전투선택 = scanner.nextInt();
						if (전투선택 == 1) {
							System.out.println("자동전투 선택\n");
							
							Thread 해변자동전투 = new Thread(new 해변자동전투(user));
							해변자동전투.start();
							try {
								해변자동전투.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							break Loop1;
						} else if (전투선택 == 2) {
							
//							System.out.println("수동전투 선택\n");
							// 해변 수동전투
							user.해변수동전투(user);
							System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
							System.out.println("\n사냥을 완료했습니다.");
							System.out.println("현재 인벤토리 : " + user.inventory);
							System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
							System.out.println("\n\n\n");
							break Loop1;
						} else {
							System.out.println("잘못 입력했습니다.\n");
							continue;
						}

					} else if (맵선택 == 5) {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
						System.out.println("파손된 리조트 선택\n");
						// 전투방식 선택
						System.out.println("\n원하는 전투 유형을 선택해주세요.");
						System.out.println("\n1.자동전투  2.수동전투\n");
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
//						메뉴음악.close();

						while (!scanner.hasNextInt()) {
							scanner.next();
							System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
						}
						int 전투선택 = scanner.nextInt();
						if (전투선택 == 1) {
							
							System.out.println("자동전투 선택\n");
							
							Thread 파손된리조트자동전투 = new Thread(new 파손된리조트자동전투(user));
							파손된리조트자동전투.start();
							try {
								파손된리조트자동전투.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							break Loop1;
						} else if (전투선택 == 2) {
							
//							System.out.println("수동전투 선택\n");
							// 파손된리조트 수동전투
							user.파손된리조트수동전투(user);
							System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
							System.out.println("\n사냥을 완료했습니다.");
							System.out.println("현재 인벤토리 : " + user.inventory);
							System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
							System.out.println("\n\n\n");
							break Loop1;
						} else {
							System.out.println("잘못 입력했습니다.\n");
							continue;
						}

					} else {
						System.out.println("잘못 입력했습니다.\n");
						continue;
					}
					}//맵선택 for문 끝

					// 전투행동끝
				} else if (행동선택 == 2) {
					System.out.println("\n탐색 선택\n");
					if(user.원숭이인사==false) {
						specialmonkey.fistHello();
						user.원숭이인사=true;
						System.out.println("계속 하려면 Enter를 눌러주세요.");
						String b = scanner.nextLine();
						String c = scanner.nextLine();
					}
					시스템.행동시작(user);
					// 덫 확인 이벤트
					event.덫(user);
					// 맵선택
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("1.숲 속  2.늪  3.파손된 배  4.해변  5.파손된 리조트");
					System.out.println("원하는 장소의 번호를 입력해주세요.\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					while (!scanner.hasNextInt()) {
						scanner.next();
						System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
					}
					int 맵선택 = scanner.nextInt();
					if (맵선택 == 1) {
//						메뉴음악.close();
						System.out.println("숲 속 선택\n");
						Thread 숲속탐색 = new Thread(new 숲속탐색(user));
						숲속탐색.start();

						try {
							숲속탐색.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;

					} else if (맵선택 == 2) {
//						메뉴음악.close();
						System.out.println("늪 선택\n");
						Thread 늪탐색 = new Thread(new 늪탐색(user));
						늪탐색.start();

						try {
							늪탐색.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;

					} else if (맵선택 == 3) {
//						메뉴음악.close();
						System.out.println("파손된 배 선택\n");
						Thread 파손된배탐색 = new Thread(new 파손된배탐색(user));
						파손된배탐색.start();

						try {
							파손된배탐색.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;

					} else if (맵선택 == 4) {
//						메뉴음악.close();
						System.out.println("해변 선택\n");
						Thread 해변탐색 = new Thread(new 해변탐색(user));
						해변탐색.start();

						try {
							해변탐색.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;

					} else if (맵선택 == 5) {
//						메뉴음악.close();
						System.out.println("파손된 리조트 선택\n");
						Thread 파손된리조트탐색 = new Thread(new 파손된리조트탐색(user));
						파손된리조트탐색.start();

						try {
							파손된리조트탐색.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					} else {
						System.out.println("잘못 입력했습니다.\n");
						continue;
					}

					// 탐색행동끝

				} else if (행동선택 == 3) {
					// 유저 인벤토리에 낚시대가 있는지 확인
					if (user.inventory.contains("낚시대")) {
						System.out.println("낚시대가 있습니다.\n");
					} else {
						System.out.println("낚시대가 없습니다. 다른 행동을 선택해주세요.\n");
						continue;
					}
//					메뉴음악.close();
					System.out.println("\n낚시 선택\n");
//					음악 낚시음악 = new 음악("FishingAndExMusic.mp3", true);
//					낚시음악.start();
					시스템.행동시작(user);
					// 통발 확인 이벤트
					event.통발(user);
					// 낚시행동메서드
					user.fishing(user, animal);
					// 낚시행동끝
//					낚시음악.close();
					break;

				} else if (행동선택 == 4) {
					System.out.println("\n뒤로가기 선택\n");
					break;
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
				
				}//행동선택 for문 끝

			} else if (a == 4) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("\n식사하기 선택\n");
				System.out.println("1.요리하기 2.아이템 섭취 3.뒤로가기\n");
				System.out.println("원하는 숫자를 입력해주세요.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");

				// 사용자 입력값을 받기 위한 스캐너
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int 식사선택 = scanner.nextInt();

				// 식사선택별 if문
				if (식사선택 == 1) {
					user.요리();
				} else if (식사선택 == 2) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템 섭취 선택\n");
					System.out.println("현재 인벤토리 : " + user.inventory + "\n");
					System.out.println("섭취하고자 하는 아이템의 이름을 입력해주세요.\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					섭취아이템 섭취아이템 = new 섭취아이템();
					Scanner sc = new Scanner(System.in);
					String 아이템선택 = sc.nextLine();

					if (!user.inventory.contains(아이템선택)) {
						System.out.println("\n잘못 입력했습니다.\n");
						continue;
					}

					if (아이템선택.equals("나무열매")) {
						섭취아이템 나무열매 = new 섭취아이템("나무열매", 20, 20, 20, 10, true, false, false);
						섭취아이템 = 나무열매;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("블랙베리")) {
						섭취아이템 블랙베리 = new 섭취아이템("블랙베리", 20, 20, 20, 10, true, false, false);
						섭취아이템 = 블랙베리;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("스노우베리")) {
						섭취아이템 스노우베리 = new 섭취아이템("스노우베리", 20, 20, 20, 10, true, false, false);
						섭취아이템 = 스노우베리;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("통조림")) {
						섭취아이템 통조림 = new 섭취아이템("통조림", 20, 20, 30, 0, true, false, false);
						섭취아이템 = 통조림;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("에너지바")) {
						섭취아이템 에너지바 = new 섭취아이템("에너지바", 20, 20, 20, 0, true, false, false);
						섭취아이템 = 에너지바;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("약")) {
						섭취아이템 약 = new 섭취아이템("약", 50, 50, 0, 0, false, true, false);
						섭취아이템 = 약;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("물")) {
						섭취아이템 물 = new 섭취아이템("물", 5, 5, 5, 50, true, false, false);
						섭취아이템 = 물;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("빵")) {
						섭취아이템 빵 = new 섭취아이템("빵", 20, 20, 40, 0, true, false, false);
						섭취아이템 = 빵;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("햄버거세트")) {
						섭취아이템 햄버거세트 = new 섭취아이템("햄버거세트", 30, 30, 50, 50, true, false, false);
						섭취아이템 = 햄버거세트;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("라면")) {
						섭취아이템 라면 = new 섭취아이템("라면", 30, 30, 40, 20, true, false, false);
						섭취아이템 = 라면;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("음료수")) {
						섭취아이템 음료수 = new 섭취아이템("음료수", 20, 20, 20, 50, true, false, false);
						섭취아이템 = 음료수;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("음료수")) {
						섭취아이템 음료수 = new 섭취아이템("음료수", 20, 20, 20, 50, true, false, false);
						섭취아이템 = 음료수;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("구운원숭이꼬치")) {
						섭취아이템 구운원숭이꼬치 = new 섭취아이템("구운원숭이꼬치", 20, 20, 40, 10, true, false, false);
						섭취아이템 = 구운원숭이꼬치;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("구운쥐꼬치")) {
						섭취아이템 구운쥐꼬치 = new 섭취아이템("구운쥐꼬치", 10, 10, 20, 10, true, false, false);
						섭취아이템 = 구운쥐꼬치;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("구운토끼꼬치")) {
						섭취아이템 구운토끼꼬치 = new 섭취아이템("구운토끼꼬치", 20, 20, 30, 10, true, false, false);
						섭취아이템 = 구운토끼꼬치;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("구운너구리꼬치")) {
						섭취아이템 구운너구리꼬치 = new 섭취아이템("구운너구리꼬치", 20, 20, 30, 10, true, false, false);
						섭취아이템 = 구운너구리꼬치;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("구운피라미꼬치")) {
						섭취아이템 구운피라미꼬치 = new 섭취아이템("구운피라미꼬치", 10, 10, 15, 5, true, false, false);
						섭취아이템 = 구운피라미꼬치;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("토끼진흙구이")) {
						섭취아이템 토끼진흙구이 = new 섭취아이템("토끼진흙구이", 20, 20, 30, 10, true, false, false);
						섭취아이템 = 토끼진흙구이;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("육지고기국")) {
						섭취아이템 육지고기국 = new 섭취아이템("육지고기국", 40, 40, 60, 40, true, false, false);
						섭취아이템 = 육지고기국;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("물고기국")) {
						섭취아이템 물고기국 = new 섭취아이템("물고기국", 40, 40, 60, 40, true, false, false);
						섭취아이템 = 물고기국;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("체력약")) {
						섭취아이템 체력약 = new 섭취아이템("체력약", 80, 0, 0, 0, false, true, false);
						섭취아이템 = 체력약;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("정신력약")) {
						섭취아이템 정신력약 = new 섭취아이템("정신력약", 0, 80, 0, 0, false, true, false);
						섭취아이템 = 정신력약;
						user.아이템섭취(user, 섭취아이템);
					} else if (아이템선택.equals("해독제")) {
						섭취아이템 해독제 = new 섭취아이템("해독제", true, true);
						섭취아이템 = 해독제;
						user.아이템섭취(user, 섭취아이템);
					} else if (user.inventory.contains(아이템선택)) {
						System.out.println("\n섭취할 수 없는 아이템입니다.\n");
					} else {
						System.out.println("잘못 입력했습니다.\n");
					}
					continue;

				} else if (식사선택 == 3) {
					System.out.println("\n뒤로가기 선택\n");
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
				continue;

			} else if (a == 5) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n휴식하기 선택");
				System.out.println("\n1.잠자기 2.씻기 3.뒤로가기");
				System.out.println("\n원하는 번호를 입력해주세요.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
				// 사용자 선택을 받기 위한 스캐너
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int 휴식선택 = scanner.nextInt();
				if (휴식선택 == 1) {
					if ("밤".equals(user.현재시간)) {

//						메뉴음악.close();
						System.out.println("\n잠자기 선택");
//						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						user.sleep(user, event);
						user.잠 = true;
//						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
						break;
					} else {
						System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("눕기는 했지만 도저히 잠이 오질 않습니다");
						System.out.println("자리에서 일어납니다.");
						System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
						System.out.println("\n\n\n");
					}
				} else if (휴식선택 == 2) {
					System.out.println("\n씻기 선택");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					user.wash();
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					continue;
				} else if (휴식선택 == 3) {
					System.out.println("\n뒤로가기 선택");
					continue;
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}

			} else if (a == 6) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("\n도움말 선택");
				System.out.println("\n1.메뉴별 설명 2.아이템 조합 보기 3.요리 조합 보기 4.엔딩 조건 5.돌아가기");
				System.out.println("\n알고싶은 메뉴의 번호를 입력해주세요.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
				// 사용자 선택을 받기 위한 스캐너
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int 도움말선택 = scanner.nextInt();
				// 설명별 if문
				if (도움말선택 == 1) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n메뉴별 설명 선택");
					System.out.println("\n***상태 보기***");
					System.out.println("현재 플레이어중인 캐릭터의 상태를 보여줍니다.");
					System.out.println("\n***인벤토리 보기***");
					System.out.println("아이템 인벤토리, 갑옷 인벤토리, 무기 인벤토리를 볼 수 있습니다.");
					System.out.println("장비를 착용할 수 있습니다.");
					System.out.println("아이템 조합이 가능합니다.");
					System.out.println("아이템을 버릴 수 있습니다.");
					System.out.println("\n***행동하기***");
					System.out.println("탐색, 사냥, 낚시 중 하나를 선택하여 행동할 수 있습니다. \n정신력을 10 소모합니다.");
					System.out.println("\n***식사하기 선택***");
					System.out.println("요리를 할 수있습니다.\n아이템을 섭취할 수 있습니다.");
					System.out.println("\n***휴식하기 선택***");
					System.out.println("잠을 자거나 씻을 수 있습니다.\n스탯이 회복됩니다.");
					System.out.println("\n뒤로가려면 Enter를 입력해주세요.\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					String 뒤로가기 = scanner.nextLine();
					String 뒤로가기2 = scanner.nextLine();

				} else if (도움말선택 == 2) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n아이템 조합 보기 선택\n");
					this.아이템조합보기();
					System.out.println("\n뒤로가려면 Enter를 입력해주세요.\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					String 뒤로가기 = scanner.nextLine();
					String 뒤로가기2 = scanner.nextLine();

				} else if (도움말선택 == 3) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n요리 조합 보기 선택\n");
					this.요리조합보기();
					System.out.println("\n뒤로가려면 Enter를 입력해주세요.\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					String 뒤로가기 = scanner.nextLine();
					String 뒤로가기2 = scanner.nextLine();

				} else if (도움말선택 == 4) {
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
					System.out.println("\n엔딩 조건 선택");
					System.out.println("\n15일동안 조명탄을 구해서 헬기가 뜨면 조명탄을 쏘아 올리세요.");
					System.out.println("조명탄은 무인도 안 어딘가에서 구할 수 있습니다.");
					System.out.println("\n뒤로가려면 Enter를 입력해주세요.\n");
					System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
					System.out.println("\n\n\n");
					String 뒤로가기 = scanner.nextLine();
					String 뒤로가기2 = scanner.nextLine();

				} else if (도움말선택 == 5) {
					System.out.println("\n돌아가기 선택\n");

				} else {
					System.out.println("잘못 입력했습니다.\n");
				}
				continue;

			} else {
				System.out.println("잘못 입력했습니다.\n");
				continue;
			} // 행동선택 if문 끝

		} // for문 끝

	}// 메뉴 메서드 끝

}// 시스템클래스 끝

class Event {

	시스템 시스템 = new 시스템();

	//헬기 등장 
	public void 헬기등장(User user) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
//			음악 헬리콥터효과음 = new 음악("Helicopter.mp3", false);
//			헬리콥터효과음.start();
			
			System.out.println("\n에휴...설마 여기서 평생 살아야 하는건 아니겠지...? 집에 가고싶다...");
			System.out.println("어....저게 뭐지?");
			System.out.println("저거저거저거 저거 헬기아냐 헬기?");
			System.out.println("헬기다 헬기!! 헬기!!! ");
			System.out.println("사람살려!!! 여기 사람있어요!!!");
			System.out.println("뭐 없나? 나 뭐 없나??\n");
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.sosSignal(시스템);
	}


	// 수면시 벌레 등장 체력정신력청결도 하락
	public void 벌레등장(User user) {
		Random rnd = new Random();
		if (rnd.nextInt(10) < 3) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("...윙..");
			System.out.println("...윙~....윙~");
			System.out.println("...윙~....윙~...윙~~");
			System.out.println("아오 이놈의 벌레 벌레 벌레!!!!");
			System.out.println("잠 좀 자자 잠 좀!!");
			System.out.println("벌레로 인해 체력, 정신력, 청결도가 하락합니다.");
			user.hp -= 10;
			user.mental -= 10;
			user.clean -= 10;
			System.out.println("현재 체력 : " + user.hp + " 현재 정신력 : " + user.mental + " 현재 청결도 : " + user.clean);
			System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
		}

	}

	public void 넘어짐(User user) {
		Random rnd = new Random();
		if (rnd.nextInt(10) < 1) {
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("미끄덩");
			System.out.println("억!!!");
			System.out.println("아우씨...아 내 엉덩이....");
			System.out.println("누가 여기다 바나나껍질을..?");
			System.out.println("원숭이 놈들 짓이지? 에휴..");
			user.hp -= 5;
			System.out.println("현재 체력 : " + user.hp);
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
			System.out.println("\n\n\n");
		}

	}

	// 비가와서 피워놓은 불이 꺼짐 -> 아이템에서 불 제거
	public void 불꺼짐(User user) {
		if (user.inventory.contains("불")) {
			Random rnd = new Random();
			if (rnd.nextInt(10) < 2) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("뭔 놈의 비가 갑자기 와?");
				System.out.println("아 맞다! 내 불!!");
				System.out.println("악!! 꺼졌어!");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
				System.out.println("\n\n\n");
				user.inventory.remove("불");
			}
		}

	}

	// 늪에 빠졌다 선택지로 탈출 or 배드엔딩
	public void 늪빠짐(User user) {
		String temp = "";
		Random rnd = new Random();
		if (rnd.nextInt(10) < 10) {
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			user.늪빠짐 = true;
			System.out.println("어? 뭐...뭐야? 발이 안빠져!");
			System.out.println("어어? 이거 점점 안으로 들어가는거 같은데!");
			System.out.println("어떡해!");
			System.out.println("늪에서 탈출하기 위해 10초내에 문자를 입력해주세요.\n");
			System.out.println("abcdefghijk");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
			System.out.println("\n\n\n");

			Thread 카운트다운 = new Thread(new 미니게임시간());
			카운트다운.start();
//			음악 카운트다운소리 = new 음악("OvenTimer.mp3", true);
//			카운트다운소리.start();
			
			System.out.println("아래에 입력해주세요.");
			Scanner scanner = new Scanner(System.in);
			String a = scanner.nextLine();
			
			if (a.equals("abcdefghijk")) {
			System.out.println("탈출 성공!");
			카운트다운.interrupt();
//			카운트다운소리.close();
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			user.늪빠짐 = false;
			System.out.println("침착해 빠져나갈 수 있어.");
			System.out.println("일단 누워서... 팔로... 기어나가면...");
			System.out.println("끙차 끙차");
			System.out.println("어...빠진다 빠진다!");
			System.out.println("끙차 끙차");
			System.out.println("빠졌다!!");
			System.out.println("아오 진짜 죽다 살아났네...와...");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
//			break;
		} else {
			System.out.println("탈출 실패!");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			user.늪빠짐 = false;
			시스템.늪BadEnd();
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
//			break;
		}
			
			
			
//			do {
//				temp = JOptionPane.showInputDialog("10초내에 문자를 입력해주세요." + "\"abcdefghijk\"");
//				
//				// 사용자가 취소버튼을 누르거나 -1을 입력하면 do-while문을 벗어난다.
//				if (temp == null || temp.equals("-1")) {
////	            	System.out.println("입력값 : "+temp);
//					System.out.println("탈출 실패!");
//
////					System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//					user.늪빠짐 = false;
//					시스템.늪BadEnd();
//					
//
//					break;
//				}
//
//				if (temp.equals("abcdefghijk")) {
//					System.out.println("입력값 : " + temp);
//					System.out.println("탈출 성공!");
//					카운트다운.interrupt();
//					카운트다운소리.close();
//					System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//					user.늪빠짐 = false;
//					System.out.println("침착해 빠져나갈 수 있어.");
//					System.out.println("일단 누워서... 팔로... 기어나가면...");
//					System.out.println("끙차 끙차");
//					System.out.println("어...빠진다 빠진다!");
//					System.out.println("끙차 끙차");
//					System.out.println("빠졌다!!");
//					System.out.println("아오 진짜 죽다 살아났네...와...");
//					System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
//					break;
//				} else {
//					System.out.println("탈출 실패!");
//					System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//					user.늪빠짐 = false;
//					시스템.늪BadEnd();
//					System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
//					break;
//				}
//
//			} while (true);

		}

	}

	// 비가 와서 강가에 두었던 아이템이 사라짐
	public void 아이템분실(User user) {
		Random rnd = new Random();
		if (rnd.nextInt(10) < 3) {
			try {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("와...하늘에 구멍뚫렸나..?");
				System.out.println("무슨 비가 이렇게 와?");
				System.out.println("강물 넘치는거 아닌가몰라");
				System.out.println("강물이 넘쳐??");
				System.out.println("강 근처에 두고 온 거 있는데!!\n");
				user.inventory.remove(0);
				System.out.println("현재 아이템 : " + user.inventory);
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
				System.out.println("\n\n\n");
			} catch (Exception e) {
//				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("아니었나봐! 다행이다..");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
				System.out.println("\n\n\n");
			}
		}

	}

	// 감기에 걸렸다
	public void 감기(User user) {
		Random rnd = new Random();
		if (rnd.nextInt(10) < 2) {
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("에취!");
			System.out.println("으 코 근지러..");
			System.out.println("에취!");
			System.out.println("재채기에.. 열도 나는 것 같은데...");
			System.out.println("감기 걸렸나봐...");
			System.out.println("질병으로 체력과 정신력이 감소합니다.\n");
			user.hp -= 10;
			user.mental -= 10;
			System.out.println("현재 체력 : " + user.hp + " 현재 정신력 : " + user.mental);
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
			System.out.println("\n\n\n");
			try {
				시스템.userHpCheck(user);
			} catch (Exception e) {
				System.out.println();
			}
		}

	}

	// 더위를 먹었다
	public void 더위(User user) {
		Random rnd = new Random();
		if (rnd.nextInt(10) < 2) {
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("어우 핑 돈다...");
			System.out.println("왜 이러지 갑자기?");
			System.out.println("혹시 더위먹었나?");
			System.out.println("질병으로 체력과 정신력이 감소합니다.\n");
			user.hp -= 15;
			user.mental -= 15;
			System.out.println("현재 체력 : " + user.hp + " 현재 정신력 : " + user.mental);
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
			System.out.println("\n\n\n");
			try {
				시스템.userHpCheck(user);
			} catch (Exception e) {
				System.out.println();
			}
		}

	}

	// 덫에 사냥감 걸림
	public void 덫(User user) {
		if (user.inventory.contains("덫")) {
			Random rnd = new Random();
			if (rnd.nextInt(10) < 2) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n오랜만에 덫이나 한번 둘러보고 올까");
				System.out.println("뭐라도 있겠지?ㅎㅎㅎ");
				System.out.println("어! 저거봐!");
				System.out.println("아싸~! 두 마리 잡았다~!\n");
				user.inventory.add("토끼고기");
				user.inventory.add("너구리고기");
				System.out.println("현재 아이템 : " + user.inventory + "\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n\n\n");
			}
		} else {
			System.out.println();
		}

	}

	// 통발에 사냥감 걸림
	public void 통발(User user) {
		if (user.inventory.contains("통발")) {
			Random rnd = new Random();
			if (rnd.nextInt(10) < 2) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n오늘은 통발 한번 보러 가야겠다.");
				System.out.println("뭐라도 있겠지?ㅎㅎㅎ");
				System.out.println("라라라~");
				System.out.println("자 보자보자~!");
				System.out.println("오!! 잡았다!\n");
				user.inventory.add("피라미");
				System.out.println("현재 아이템 : " + user.inventory + "\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n\n\n");
			}
		} else {
			System.out.println();
		}

	}

	// 식중독에 걸렸다
	public void 식중독(User user) {
		Random rnd = new Random();
		if (rnd.nextInt(10) < 2) {
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\n아 이상하게 배가 살살 아프네");
			System.out.println("분명 아까 쌌는데...");
			System.out.println("아! 뭐야 배가..");
			System.out.println("뭐 잘못 먹었나봐...망할..");
			System.out.println("악!!!!!");
			System.out.println("식중독에 걸려 체력과 정신력이 감소합니다.\n");
			user.hp -= 10;
			user.mental -= 10;
			System.out.println("현재 체력 : " + user.hp + " 현재 정신력 : " + user.mental);
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
			System.out.println("\n\n\n");
			try {
				시스템.userHpCheck(user);
			} catch (Exception e) {
				System.out.println();
			}

		}

	}

}

class 늪자동전투 implements Runnable {
	// 객체생성
	Animal animal = new Animal();
	User user = new User();
	시스템 시스템 = new 시스템();

	// 생성자
	늪자동전투(User user) {
		this.user = user;
	}

	public void run() {
		// 몬스터등장for문
		Loop1 : for (int i = 0; i < 3; i++) {
//			음악 전투음악 = new 음악("FightMusic.mp3", true);
//			전투음악.start();
			//진행 예외 처리
			for(;;) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println("\n1.진행하기 2.돌아가기\n");
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			Scanner scanner = new Scanner(System.in);
			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
			}
			int a = scanner.nextInt();
			if (a == 1) { // 진행하기 선택
				animal = 시스템.늪랜덤(); // 늪 동물객체를 랜덤으로 생성하는 메서드
				System.out.println("\n야생의 " + animal.name + "이/가 나타났다!\n");

				// 유저의 공포감확인 80이상일시 전투를 끝내버리고 도주함
				if (user.공포감 >= 80) {
					시스템.공포감확인(user);
//					전투음악.close();
					break Loop1;
				} else { // 유저의 공포감이 80이 아닐 경우
					System.out.println();
				}
				//공격 선택 for문
				for(;;) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("\n1. 공격한다 2.도망친다.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int runOr = scanner.nextInt();
				if (runOr == 1) { // 공격한다 선택

					// 전투for문
					for (;;) {

						// 유저 공격
						if (user.hp <= 40) { // 유저의 체력이 40이하면 체력회복스킬 사용
							System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							user.skill(user, animal);
							System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
						} else if (user.hp > 40) {
							Random rnd = new Random();
							int c = rnd.nextInt(100);
							if (c < 70) { // 일반공격과 스킬2,스킬3을 랜덤으로 사용
								// 일반공격
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.attack(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							} else if (c < 85) {
								// 스킬2
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.skill2(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							} else if (c < 100) {
								// 스킬3
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.skill3(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							}
						}
						// 쓰레드를 1초간격으로 출력
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// 몬스터 체력 확인 0이면 해당 전투 종료
						if (animal.hp <= 0) {
//							전투음악.close();
							시스템.monsterHpCheck(user, animal);
							continue Loop1;
						}

						// 몬스터 공격
						Random rnd = new Random();
						int b = rnd.nextInt(100);
						if (b < 35) { // 몬스터의 스킬3가지 중 한가지 랜덤으로 선택
							// 스킬1
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack(user, animal, 시스템);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						} else if (b < 70) {
							// 스킬2
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack2(user, animal);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						} else if (b < 100) {
							// 스킬3
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack3(user, animal);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						}
						// 쓰레드 1초간격으로 출력
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// 유저의 체력이 0이면 배드엔딩으로 프로그램이 끝남
						시스템.userHpCheck(user);
						continue;
					} // 전투for문끝
				} else if (runOr == 2) { // 도망친다 선택
					user.runAway(user); // 도망 메서드
//					전투음악.close();
					continue;
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
				}//공격 선택 for문 끝 

			} else if (a == 2) { // 돌아가기 메뉴의 첫부분으로 돌아간다
//				전투음악.close();
				break ;
			} else {
				System.out.println("잘못 입력했습니다.\n");
				continue;
			}
			
			}//진행 for문 끝

//			전투음악.close();
			break;
		} // 몬스터등장for문끝
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
		System.out.println("사냥을 완료했습니다.");
		System.out.println("현재 인벤토리 : " + user.inventory);
		System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
	}// run끝
}

class 파손된배자동전투 implements Runnable {
	// 객체생성
	Animal animal = new Animal();
	User user = new User();
	시스템 시스템 = new 시스템();

	// 생성자
	파손된배자동전투(User user) {
		this.user = user;
	}

	public void run() {
		// 몬스터등장for문
		Loop1 : for (int i = 0; i < 3; i++) {
//			음악 전투음악 = new 음악("FightMusic.mp3", true);
//			전투음악.start();
			//진행 예외 처리
			for(;;) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println("\n1.진행하기 2.돌아가기\n");
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			Scanner scanner = new Scanner(System.in);
			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
			}
			int a = scanner.nextInt();
			if (a == 1) { // 진행하기 선택
				animal = 시스템.파손된배랜덤(); // 파손된배 동물객체를 랜덤으로 생성하는 메서드
				System.out.println("\n야생의 " + animal.name + "이/가 나타났다!\n");

				// 유저의 공포감확인 80이상일시 전투를 끝내버리고 도주함
				if (user.공포감 >= 80) {
					시스템.공포감확인(user);
//					전투음악.close();
					break Loop1;
				} else { // 유저의 공포감이 80이 아닐 경우
					System.out.println();
				}
				//공격 선택 for문
				for(;;) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("\n1. 공격한다 2.도망친다.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int runOr = scanner.nextInt();
				if (runOr == 1) { // 공격한다 선택

					// 전투for문
					for (;;) {

						// 유저 공격
						if (user.hp <= 40) { // 유저의 체력이 40이하면 체력회복스킬 사용
							System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							user.skill(user, animal);
							System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
						} else if (user.hp > 40) {
							Random rnd = new Random();
							int c = rnd.nextInt(100);
							if (c < 70) { // 일반공격과 스킬2,스킬3을 랜덤으로 사용
								// 일반공격
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.attack(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							} else if (c < 85) {
								// 스킬2
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.skill2(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							} else if (c < 100) {
								// 스킬3
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.skill3(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							}
						}
						// 쓰레드를 1초간격으로 출력
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// 몬스터 체력 확인 0이면 해당 전투 종료
						if (animal.hp <= 0) {
//							전투음악.close();
							시스템.monsterHpCheck(user, animal);
							continue Loop1;
						}

						// 몬스터 공격
						Random rnd = new Random();
						int b = rnd.nextInt(100);
						if (b < 35) { // 몬스터의 스킬3가지 중 한가지 랜덤으로 선택
							// 스킬1
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack(user, animal, 시스템);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						} else if (b < 70) {
							// 스킬2
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack2(user, animal);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						} else if (b < 100) {
							// 스킬3
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack3(user, animal);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						}
						// 쓰레드 1초간격으로 출력
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// 유저의 체력이 0이면 배드엔딩으로 프로그램이 끝남
						시스템.userHpCheck(user);
						continue;
					} // 전투for문끝
				} else if (runOr == 2) { // 도망친다 선택
					user.runAway(user); // 도망 메서드
//					전투음악.close();
					continue Loop1;
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
				}//공격선택 for문 끝

			} else if (a == 2) { // 돌아가기 메뉴의 첫부분으로 돌아간다
//				전투음악.close();
				break;
			} else {
				System.out.println("잘못 입력했습니다.\n");
				continue;
			}
			
			}//진행 for문 끝

//			전투음악.close();
			break;
		} // 몬스터등장for문끝
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
		System.out.println("사냥을 완료했습니다.");
		System.out.println("현재 인벤토리 : " + user.inventory);
		System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
	}// run끝
}

class 파손된리조트자동전투 implements Runnable {
	// 객체생성
	Animal animal = new Animal();
	User user = new User();
	시스템 시스템 = new 시스템();

	// 생성자
	파손된리조트자동전투(User user) {
		this.user = user;
	}

	public void run() {
		// 몬스터등장for문
		Loop1 : for (int i = 0; i < 3; i++) {
//			음악 전투음악 = new 음악("FightMusic.mp3", true);
//			전투음악.start();
			//진행 예외 처리
			for(;;) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println("\n1.진행하기 2.돌아가기\n");
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			Scanner scanner = new Scanner(System.in);
			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
			}
			int a = scanner.nextInt();
			if (a == 1) { // 진행하기 선택
				animal = 시스템.파손된리조트랜덤(); // 파손된리조트 동물객체를 랜덤으로 생성하는 메서드
				System.out.println("\n야생의 " + animal.name + "이/가 나타났다!\n");

				// 유저의 공포감확인 80이상일시 전투를 끝내버리고 도주함
				if (user.공포감 >= 80) {
					시스템.공포감확인(user);
//					전투음악.close();
					break Loop1;
				} else { // 유저의 공포감이 80이 아닐 경우
					System.out.println();
				}
				//공격 선택 for문
				for(;;) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("\n1. 공격한다 2.도망친다.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int runOr = scanner.nextInt();
				if (runOr == 1) { // 공격한다 선택

					// 전투for문
					for (;;) {

						// 유저 공격
						if (user.hp <= 40) { // 유저의 체력이 40이하면 체력회복스킬 사용
							System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							user.skill(user, animal);
							System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
						} else if (user.hp > 40) {
							Random rnd = new Random();
							int c = rnd.nextInt(100);
							if (c < 70) { // 일반공격과 스킬2,스킬3을 랜덤으로 사용
								// 일반공격
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.attack(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							} else if (c < 85) {
								// 스킬2
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.skill2(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							} else if (c < 100) {
								// 스킬3
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.skill3(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							}
						}
						// 쓰레드를 1초간격으로 출력
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// 몬스터 체력 확인 0이면 해당 전투 종료
						if (animal.hp <= 0) {
//							전투음악.close();
							시스템.monsterHpCheck(user, animal);
							continue Loop1;
						}

						// 몬스터 공격
						Random rnd = new Random();
						int b = rnd.nextInt(100);
						if (b < 40) { // 몬스터의 스킬3가지 중 한가지 랜덤으로 선택
							// 스킬1
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack(user, animal, 시스템);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						} else if (b < 85) {
							// 스킬2
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack2(user, animal);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						} else if (b < 100) {
							// 스킬3
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack3(user, animal);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						}
						// 쓰레드 1초간격으로 출력
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// 유저의 체력이 0이면 배드엔딩으로 프로그램이 끝남
						시스템.userHpCheck(user);
						continue;
					} // 전투for문끝
				} else if (runOr == 2) { // 도망친다 선택
					user.runAway(user); // 도망 메서드
//					전투음악.close();
					continue Loop1;
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
				}//공격 선택 for문 끝
				
			} else if (a == 2) { // 돌아가기 메뉴의 첫부분으로 돌아간다
//				전투음악.close();
				break;
			} else {
				System.out.println("잘못 입력했습니다.\n");
				continue;
			}
			
			}//진행 for문 끝

//			전투음악.close();
			break;
		} // 몬스터등장for문끝
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
		System.out.println("사냥을 완료했습니다.");
		System.out.println("현재 인벤토리 : " + user.inventory);
		System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
	}// run끝
}

class 해변자동전투 implements Runnable {
	// 객체생성
	Animal animal = new Animal();
	User user = new User();
	시스템 시스템 = new 시스템();

	// 생성자
	해변자동전투(User user) {
		this.user = user;
	}

	public void run() {
		// 몬스터등장for문
		Loop1 : for (int i = 0; i < 3; i++) {
//			음악 전투음악 = new 음악("FightMusic.mp3", true);
//			전투음악.start();
			//진행 예외 처리
			for(;;) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println("\n1.진행하기 2.돌아가기\n");
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			Scanner scanner = new Scanner(System.in);
			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
			}
			int a = scanner.nextInt();
			if (a == 1) { // 진행하기 선택
				animal = 시스템.해변랜덤(); // 해변 동물객체를 랜덤으로 생성하는 메서드
				System.out.println("\n야생의 " + animal.name + "이/가 나타났다!\n");

				// 유저의 공포감확인 80이상일시 전투를 끝내버리고 도주함
				if (user.공포감 >= 80) {
					시스템.공포감확인(user);
//					전투음악.close();
					break Loop1;
				} else { // 유저의 공포감이 80이 아닐 경우
					System.out.println();
				}
				//공격 선택 for문
				for(;;) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("\n1. 공격한다 2.도망친다.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int runOr = scanner.nextInt();
				if (runOr == 1) { // 공격한다 선택

					// 전투for문
					for (;;) {

						// 유저 공격
						if (user.hp <= 40) { // 유저의 체력이 40이하면 체력회복스킬 사용
							System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							user.skill(user, animal);
							System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
						} else if (user.hp > 40) {
							Random rnd = new Random();
							int c = rnd.nextInt(100);
							if (c < 70) { // 일반공격과 스킬2,스킬3을 랜덤으로 사용
								// 일반공격
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.attack(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							} else if (c < 85) {
								// 스킬2
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.skill2(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							} else if (c < 100) {
								// 스킬3
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.skill3(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							}
						}
						// 쓰레드를 1초간격으로 출력
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// 몬스터 체력 확인 0이면 해당 전투 종료
						if (animal.hp <= 0) {
//							전투음악.close();
							시스템.monsterHpCheck(user, animal);
							continue Loop1;
						}

						// 몬스터 공격
						Random rnd = new Random();
						int b = rnd.nextInt(100);
						if (b < 35) { // 몬스터의 스킬3가지 중 한가지 랜덤으로 선택
							// 스킬1
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack(user, animal, 시스템);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						} else if (b < 70) {
							// 스킬2
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack2(user, animal);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						} else if (b < 100) {
							// 스킬3
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack3(user, animal);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						}
						// 쓰레드 1초간격으로 출력
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// 유저의 체력이 0이면 배드엔딩으로 프로그램이 끝남
						시스템.userHpCheck(user);
						continue;
					} // 전투for문끝
				} else if (runOr == 2) { // 도망친다 선택
					user.runAway(user); // 도망 메서드
//					전투음악.close();
					continue Loop1;
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
				}//공격선택 for문 끝
				
			} else if (a == 2) { // 돌아가기 메뉴의 첫부분으로 돌아간다
//				전투음악.close();
				break;
			} else {
				System.out.println("잘못 입력했습니다.\n");
				continue;
			}
			
			}//진행 for문 끝 

//			전투음악.close();
			break;
		} // 몬스터등장for문끝
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
		System.out.println("사냥을 완료했습니다.");
		System.out.println("현재 인벤토리 : " + user.inventory);
		System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
	}// run끝
}

class 숲속자동전투 implements Runnable {
	// 객체생성
	Animal animal = new Animal();
	User user = new User();
	시스템 시스템 = new 시스템();

	// 생성자
	숲속자동전투(User user) {
		this.user = user;
	}

	public void run() {
		// 몬스터등장for문
		Loop1 : for (int i = 0; i < 3; i++) {
//			음악 전투음악 = new 음악("FightMusic.mp3", true);
//			전투음악.start();
			//진행 예외 처리
			for(;;) {
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
			System.out.println("\n1.진행하기 2.돌아가기\n");
			System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
			System.out.println("\n\n\n");
			Scanner scanner = new Scanner(System.in);
			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
			}
			int a = scanner.nextInt();
			if (a == 1) { // 진행하기 선택
				animal = 시스템.숲속랜덤(); // 숲속 동물객체를 랜덤으로 생성하는 메서드
				System.out.println("\n야생의 " + animal.name + "이/가 나타났다!\n");
				
				// 유저의 공포감확인 80이상일시 전투를 끝내버리고 도주함
				if (user.공포감 >= 80) {
					시스템.공포감확인(user);
//					전투음악.close();
					continue Loop1;
				} else { // 유저의 공포감이 80이 아닐 경우
					System.out.println();
				}
				//공격 선택 for문
				for(;;) {
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				System.out.println("\n1. 공격한다 2.도망친다.\n");
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
				System.out.println("\n\n\n");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
				}
				int runOr = scanner.nextInt();
				if (runOr == 1) { // 공격한다 선택

					// 전투for문
					for (;;) {

						// 유저 공격
						if (user.hp <= 40) { // 유저의 체력이 40이하면 체력회복스킬 사용
							System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							user.skill(user, animal);
							System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
						} else if (user.hp > 40) {
							Random rnd = new Random();
							int c = rnd.nextInt(100);
							if (c < 70) { // 일반공격과 스킬2,스킬3을 랜덤으로 사용
								// 일반공격
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.attack(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							} else if (c < 85) {
								// 스킬2
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.skill2(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							} else if (c < 100) {
								// 스킬3
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
//								음악 타격음 = new 음악("Kick.mp3", false);
//								타격음.start();
								user.skill3(user, animal);
								System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
							}
						}
						// 쓰레드를 1초간격으로 출력
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// 몬스터 체력 확인 0이면 해당 전투 종료
						if (animal.hp <= 0) {
//							전투음악.close();
							시스템.monsterHpCheck(user, animal);
							continue Loop1;
						}

						// 몬스터 공격
						Random rnd = new Random();
						int b = rnd.nextInt(100);
						if (b < 35) { // 몬스터의 스킬3가지 중 한가지 랜덤으로 선택
							// 스킬1
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack(user, animal, 시스템);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						} else if (b < 70) {
							// 스킬2
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack2(user, animal);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						} else if (b < 100) {
							// 스킬3
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
							animal.attack3(user, animal);
							System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
						}
						// 쓰레드 1초간격으로 출력
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// 유저의 체력이 0이면 배드엔딩으로 프로그램이 끝남
						시스템.userHpCheck(user);
						continue;
					} // 전투for문끝
				} else if (runOr == 2) { // 도망친다 선택
					user.runAway(user); // 도망 메서드
//					전투음악.close();
					continue Loop1;
				} else {
					System.out.println("잘못 입력했습니다.\n");
					continue;
				}
				}//공격 선택 for문 끝

			} else if (a == 2) { // 돌아가기 메뉴의 첫부분으로 돌아간다
//				전투음악.close();
				break;
			} else {
				System.out.println("잘못 입력했습니다.\n");
				continue;
			}


			}//진행 for문 끝 

//			전투음악.close();
			break;
		} // 몬스터등장for문끝
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		System.out.println("사냥을 완료했습니다.");
		System.out.println("현재 인벤토리 : " + user.inventory);
		System.out.println("\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");
		System.out.println("\n\n\n");
	}// run끝

}

class 동물자동공격 implements Runnable {
	// 객체생성
	Animal animal = new Animal();
	시스템 시스템 = new 시스템();
	User user = new User();

	동물자동공격(User user, Animal animal) {
		this.user = user;
		this.animal = animal;
	}

	public void run() {

		// 몬스터공격
		for (;;) {
			try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			// 몬스터 체력 확인
			if (animal.hp <= 0) {
				break;
			}
			
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			
			
			// 몬스터 공격
			Random rnd = new Random();
			int b = rnd.nextInt(100);
			if (b < 35) {
				// 스킬1
				System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
				animal.attack(user, animal, 시스템);
				System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
			} else if (b < 70) {
				// 스킬2
				System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
				animal.attack2(user, animal);
				System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
			} else if (b < 100) {
				// 스킬3
				System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
				animal.attack3(user, animal);
				System.out.println("\t\t\t" + "――――――――――――――――――――――――――――――――――");
			}

			// 유저 체력확인
			if (user.hp <= 0) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				시스템.userHpCheck(user);
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			}
			// 몬스터 체력 확인
			if (animal.hp <= 0) {
				break;
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			continue;
		}

//		}
	}// run 끝
}

class 숲속탐색 implements Runnable {
	User user = new User();
	SpecialMonkey specialmonkey = new SpecialMonkey();

	// 생성자
	숲속탐색(User user) {
		this.user = user;
	}

	public void run() {
//		음악 숲속탐색음악 = new 음악("FishingAndExMusic.mp3", true);
//		숲속탐색음악.start();
		System.out.println("숲 속 탐색을 시작합니다.");

		for (int i = 0; i < 4; i++) {

			Random rnd = new Random();
			int a = rnd.nextInt(100);
			if (a < 15) {
				// 나무열매
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 무언가 보입니다.");
				System.out.println("나무열매를 발견했습니다!");
				System.out.println("탐스럽게 잘 익었습니다.");
				System.out.println("나무열매를 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("나무열매");
			} else if (a < 30) {
				// 블랙베리
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n저게 뭘까요?");
				System.out.println("블랙베리를 발견했습니다!");
				System.out.println("까맣게 잘 익었습니다.");
				System.out.println("블랙베리를 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("블랙베리");
			} else if (a < 45) {
				// 스노우베리
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 무언가 보입니다.");
				System.out.println("스노우베리를 발견했습니다!");
				System.out.println("한국에선 보지 못한 열매입니다.");
				System.out.println("스노우베리를 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("스노우베리");
			} else if (a < 60) {
				// 나뭇잎
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앞에 무언가 떨어져있습니다.");
				System.out.println("나뭇잎을 발견했습니다!");
				System.out.println("나뭇잎을 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("나뭇잎");
			} else if (a < 75) {
				// 치커리
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앞에 어떤 식물이 보입니다.");
				System.out.println("치커리를 발견했습니다!");
				System.out.println("약초로 쓸만해 보입니다.");
				System.out.println("치커리를 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("치커리");
			} else if (a < 100) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗! 저기 무언가 커다란게 보입니다.");
				System.out.println("그냥 큰 바위였습니다..");
				System.out.println("쓸 데도 없지만 너무 무거워 들 수 없습니다..");
				System.out.println("바위를 그냥 내버려둡니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} // for문끝
		System.out.println("현재 아이템 : " + user.inventory);

		// 상인원숭이 등장확률
		Random 원숭이등장 = new Random();
		if (원숭이등장.nextInt(10) < 10) {
			specialmonkey.hello();
			// 어느 아이템을 보여줄건지 확률로 생성
			Random 샵확률 = new Random();
			if (샵확률.nextInt(10) < 4) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView1(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰1교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			} else if (샵확률.nextInt(10) < 8) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView2(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰2교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			} else if (샵확률.nextInt(10) < 10) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView3(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰3교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			}
		} // 상인원숭이랜덤 끝
//		숲속탐색음악.close();
	}// run끝

}

class 해변탐색 implements Runnable {
	User user = new User();
	SpecialMonkey specialmonkey = new SpecialMonkey();

	// 생성자
	해변탐색(User user) {
		this.user = user;
	}

	public void run() {
//		음악 해변탐색음악 = new 음악("FishingAndExMusic.mp3", true);
//		해변탐색음악.start();
		System.out.println("해변 탐색을 시작합니다.");

		for (int i = 0; i < 4; i++) {

			Random rnd = new Random();
			int a = rnd.nextInt(100);
			if (a < 20) {
				// 조개껍질
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 무언가 반짝입니다.");
				System.out.println("조개껍질을 발견했습니다!");
				System.out.println("조개껍질을 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("조개껍질");
			} else if (a < 35) {
				// 야자나뭇잎
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n눈 앞에 커다란 것이 보입니다.");
				System.out.println("야자나무잎을 발견했습니다!");
				System.out.println("야자나뭇잎을 듭니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("야자나뭇잎");
			} else if (a < 50) {
				// 돌
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 무언가 단단해보입니다.");
				System.out.println("돌을 발견했습니다!");
				System.out.println("쓸모있어 보입니다.");
				System.out.println("돌을 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("돌");
			} else if (a < 65) {
				// 깃털
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앞에 무언가 떨어져있습니다.");
				System.out.println("깃털을 발견했습니다!");
				System.out.println("깃털을 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("깃털");
			} else if (a < 80) {
				// 허브
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앞에 어떤 식물이 보입니다.");
				System.out.println("허브를 발견했습니다!");
				System.out.println("약초로 쓸만해 보입니다.");
				System.out.println("허브를 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("허브");
			} else if (a < 100) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n저벅저벅");
				System.out.println("저벅저벅");
				System.out.println("저기 무언가 반짝입니다.");
				System.out.println("그냥 모래였습니다...");
				System.out.println("저벅저벅\n");
				System.out.println("저벅저벅...\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} // for문끝

		System.out.println("현재 아이템 : " + user.inventory);

		// 상인원숭이 등장확률
		Random 원숭이등장 = new Random();
		if (원숭이등장.nextInt(10) < 10) {
			specialmonkey.hello();
			// 어느 아이템을 보여줄건지 확률로 생성
			Random 샵확률 = new Random();
			if (샵확률.nextInt(10) < 4) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView1(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰1교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			} else if (샵확률.nextInt(10) < 8) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView2(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰2교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			} else if (샵확률.nextInt(10) < 10) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView3(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰3교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			}
		} // 상인원숭이랜덤 끝
//		해변탐색음악.close();
	}

}

class 늪탐색 implements Runnable {
	User user = new User();
	SpecialMonkey specialmonkey = new SpecialMonkey();

	// 생성자
	늪탐색(User user) {
		this.user = user;
	}

	public void run() {
//		음악 늪탐색음악 = new 음악("FishingAndExMusic.mp3", true);
//		늪탐색음악.start();
		System.out.println("늪 탐색을 시작합니다.");

		for (int i = 0; i < 4; i++) {

			Random rnd = new Random();
			int a = rnd.nextInt(100);
			if (a < 15) {
				// 수액
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 무언가 보입니다.");
				System.out.println("수액을 발견했습니다!");
				System.out.println("수액을 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("수액");
			} else if (a < 30) {
				// 알로에
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앞에 어떤 식물이 보입니다.");
				System.out.println("알로에를 발견했습니다!");
				System.out.println("약초로 쓸만해 보입니다.");
				System.out.println("알로에를 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("알로에");
			} else if (a < 45) {
				// 나무덩쿨
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 밧줄일까요?");
				System.out.println("나무덩쿨을 발견했습니다!");
				System.out.println("쓸모있어 보입니다.");
				System.out.println("나무덩쿨을 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("나무덩쿨");
			} else if (a < 60) {
				// 진흙
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n무언가를 밟았습니다.");
				System.out.println("진흙을 발견했습니다!");
				System.out.println("쓸모있어 보입니다.");
				System.out.println("진흙을 담았습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("진흙");
			} else if (a < 80) {
				// 나무가지
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n무언가를 밟았습니다.");
				System.out.println("나무가지를 발견했습니다!");
				System.out.println("나무가지를 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("나무가지");
			} else if (a < 100) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n무언가를 밟았습니다.");
				System.out.println("킁킁");
				System.out.println("아...");
				System.out.println("배설물입니다...\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} // for문끝
		System.out.println("현재 아이템 : " + user.inventory);

		// 상인원숭이 등장확률
		Random 원숭이등장 = new Random();
		if (원숭이등장.nextInt(10) < 3) {
			specialmonkey.hello();
			// 어느 아이템을 보여줄건지 확률로 생성
			Random 샵확률 = new Random();
			if (샵확률.nextInt(10) < 4) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView1(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰1교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			} else if (샵확률.nextInt(10) < 8) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView2(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰2교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			} else if (샵확률.nextInt(10) < 10) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView3(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰3교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			}
		} // 상인원숭이랜덤 끝
//		늪탐색음악.close();
	}

}

class 파손된리조트탐색 implements Runnable {
	User user = new User();
	SpecialMonkey specialmonkey = new SpecialMonkey();

	// 생성자
	파손된리조트탐색(User user) {
		this.user = user;
	}

	public void run() {
//		음악 파손된리조트탐색음악 = new 음악("FishingAndExMusic.mp3", true);
//		파손된리조트탐색음악.start();
		System.out.println("파손된 리조트 탐색을 시작합니다.");

		for (int i = 0; i < 4; i++) {

			Random rnd = new Random();
			int a = rnd.nextInt(100);
			if (a < 10) {
				// 테니스라켓
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 무언가 보입니다.");
				System.out.println("테니스라켓을 발견했습니다!");
				System.out.println("누군가 좋아할 것 같습니다.");
				System.out.println("테니스라켓을 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("테니스라켓");
			} else if (a < 30) {
				// 녹슨칼
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 뭔가 반짝이는듯 합니다.");
				System.out.println("녹슨칼을 발견했습니다!");
				System.out.println("녹슬었지만 쓸만해보입니다.");
				System.out.println("녹슨칼을 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("녹슨칼");
			} else if (a < 50) {
				// 냄비
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 무언가 있습니다.");
				System.out.println("냄비를 발견했습니다!");
				System.out.println("요리를 할 수 있겠네요.");
				System.out.println("냄비를 듭니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("냄비");
			} else if (a < 60) {
				// 에너지바
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n낯익은 봉지가 보입니다.");
				System.out.println("에너지바를 발견했습니다!");
				System.out.println("에너지바를 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("에너지바");
			} else if (a < 70) {
				// 망가진도끼
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 뭔가 반짝이는듯 합니다.");
				System.out.println("망가진도끼를 발견했습니다!");
				System.out.println("무기로 쓸 수 있어 보입니다.");
				System.out.println("망가진도끼를 챙깁니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("망가진도끼");
			} else if (a < 100) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저게 뭘까요?");
				System.out.println("\"뭐지 저게?\"");
				System.out.println("아...");
				System.out.println("유심히 살펴보니 쓰레기입니다...\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} // for문끝
		System.out.println("현재 아이템 : " + user.inventory);

		// 상인원숭이 등장확률
		Random 원숭이등장 = new Random();
		if (원숭이등장.nextInt(10) < 3) {
			specialmonkey.hello();
			// 어느 아이템을 보여줄건지 확률로 생성
			Random 샵확률 = new Random();
			if (샵확률.nextInt(10) < 4) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView1(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰1교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			} else if (샵확률.nextInt(10) < 8) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView2(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰2교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			} else if (샵확률.nextInt(10) < 10) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView3(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰3교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			}
		} // 상인원숭이랜덤 끝
//		파손된리조트탐색음악.close();
	}

}

class 파손된배탐색 implements Runnable {
	User user = new User();
	SpecialMonkey specialmonkey = new SpecialMonkey();

	// 생성자
	파손된배탐색(User user) {
		this.user = user;
	}

	public void run() {
//		음악 파손된배탐색음악 = new 음악("FishingAndExMusic.mp3", true);
//		파손된배탐색음악.start();
		System.out.println("파손된 배 탐색을 시작합니다.");

		for (int i = 0; i < 4; i++) {

			Random rnd = new Random();
			int a = rnd.nextInt(100);
			if (a < 10) {
				// 약
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n낯익은 형태가 보입니다.");
				System.out.println("약을 발견했습니다!");
				System.out.println("약을 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("약");
			} else if (a < 30) {
				// 나무판자
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n뭔가 커다란게 보입니다.");
				System.out.println("나무판자를 발견했습니다!");
				System.out.println("나무판자를 듭니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("나무판자");
			} else if (a < 50) {
				// 동전
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 뭔가 반짝이는듯 합니다.");
				System.out.println("동전을 발견했습니다!");
				System.out.println("혹시모르니 챙겨봅니다.");
				System.out.println("동전을 가방에 넣습니다..\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("동전");
			} else if (a < 60) {
				// 통조림
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n낯익은 형태가 보입니다.");
				System.out.println("통조림을 발견했습니다!");
				System.out.println("통조림을 가방에 넣습니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("통조림");
			} else if (a < 70) {
				// 망가진톱
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저기 뭔가 보입니다.");
				System.out.println("망가진톱을 발견했습니다!");
				System.out.println("무기로 쓸 수 있어 보입니다.");
				System.out.println("망가진톱을 챙깁니다.\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				user.inventory.add("망가진톱");
			} else if (a < 100) {
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\n앗 저게 뭘까요?");
				System.out.println("\"뭐지 저게?\"");
				System.out.println("아...");
				System.out.println("유심히 살펴보니 쓰레기입니다...\n");
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} // for문끝
		System.out.println("현재 아이템 : " + user.inventory);

		// 상인원숭이 등장확률
		Random 원숭이등장 = new Random();
		if (원숭이등장.nextInt(10) < 3) {
			specialmonkey.hello();
			// 어느 아이템을 보여줄건지 확률로 생성
			Random 샵확률 = new Random();
			if (샵확률.nextInt(10) < 4) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView1(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰1교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			} else if (샵확률.nextInt(10) < 8) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView2(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰2교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			} else if (샵확률.nextInt(10) < 10) {
				System.out.println("――――――――――――――――――――――――――――――――――");
				specialmonkey.shopView3(); // 교환아이템 메뉴
				System.out.println("――――――――――――――――――――――――――――――――――");
				System.out.println();
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("현재 소지품 : " + user.inventory + "\n");
				user.뷰3교환(); // 유저 교환 메서드
				System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓" + "\n");
			}
		} // 상인원숭이랜덤 끝
//		파손된배탐색음악.close();
	}

}

//낚시 주사위 쓰레드
class 낚시주사위 implements Runnable {
	// 객체생성
	Animal animal = new Animal();
	User user = new User();

	// 생성자
	낚시주사위(User user, Animal animal) {
		this.user = user;
		this.animal = animal;
	}

	public void run() {
		System.out.println("주사위를 던집니다!\n");
		// 적 주사위
		for (int i = 0; i < 3; i++) {
			String 횟수 = "";
			Random rnd = new Random();
			int a = rnd.nextInt(6) + 1;
			animal.dice += a;
			if (i == 0) {
				횟수 = "첫번째";
			} else if (i == 1) {
				횟수 = "두번째";
			} else if (i == 2) {
				횟수 = "세번째";
			}
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("적의 " + 횟수 + " 주사위 굴리기");
			System.out.println("현재 적 주사위 :" + a);
			System.out.println("적 주사위 합계 :" + animal.dice + "\n");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		System.out.println("최종 적 주사위 : " + animal.dice);
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");

		// 유저 주사위
		for (int i = 0; i < 3; i++) {
			String 횟수 = "";
			Random rnd = new Random();
			int b = rnd.nextInt(6) + 1;
			user.dice += b;
			if (i == 0) {
				횟수 = "첫번째";
			} else if (i == 1) {
				횟수 = "두번째";
			} else if (i == 2) {
				횟수 = "세번째";
			}
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("플레이어의 " + 횟수 + " 주사위 굴리기");
			System.out.println("현재 플레이어 주사위 :" + b);
			System.out.println("플레이어 주사위 합계 :" + user.dice + "\n");
			System.out.println("〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		System.out.println("최종 플레이어 주사위 : " + user.dice);
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
	}
}

//입질 쓰레드
class 입질 implements Runnable {

	public void run() {
		System.out.print("입질이 오기를 기다리고 있습니다.");
		for (int i = 0; i < 5; i++) {
			System.out.print("..");
			try {
				Thread.sleep(1000);
			} catch (Exception e) {

			}
		}
//		음악 트릴  = new 음악("Tirin.mp3", false);
//		트릴.start();
		System.out.println("!\n");
	}
}

//날씨 쓰레드
class Wether implements Runnable {
	String 날씨;
	int type;

	Event event = new Event();
	User user = new User();
	시스템 시스템 = new 시스템();

	Wether() {
		this.날씨 = "";
		this.type = 0;
	}

	Wether(User user) {
		this.날씨 = "";
		this.type = 0;
		this.user = user;
	}

	Wether(String 날씨, int type, User user) {
		this.날씨 = 날씨;
		this.type = type;
		this.user = user;
	}

	// 현재날씨와 효과
	public void 비() {
		System.out.println("비가 옵니다. <불을 소지하고 있다면 꺼질 수 있습니다>");
		event.불꺼짐(user);
	}

	public void 맑음() {
		System.out.println("하늘이 맑습니다. <더위를 먹을  확률이 증가합니다>");
		event.더위(user);
	}

	public void 흐림() {
		System.out.println("하늘이 흐립니다. <감기나 식중독에 걸릴 확률이 증가합니다>");
		Random rnd = new Random();
		int a = rnd.nextInt(100);
		if (a < 5) {
			event.감기(user);
		} else if (a < 10) {
			event.식중독(user);
		}
	}

	public void 스콜() {
		System.out.println("갑자기 비가 쏟아집니다. <아이템을 분실할 확률이 증가합니다>");
		event.아이템분실(user);
	}

	public synchronized void run() {
		Wether wether = new Wether();

		for (;;) {
			Random rnd = new Random();
			int a = rnd.nextInt(100);

			if (a < 20) {
				Wether 비 = new Wether("비", 1, user);
				wether = 비;
			} else if (a < 60) {
				Wether 맑음 = new Wether("맑음", 2, user);
				wether = 맑음;
			} else if (a < 80) {
				Wether 흐림 = new Wether("흐림", 3, user);
				wether = 흐림;
			} else if (a < 100) {
				Wether 스콜 = new Wether("스콜", 4, user);
				wether = 스콜;
			}
			if (user.늪빠짐 == false) {
				switch (wether.type) {
				case 1:
					wether.비();
					break;
				case 2:
					wether.맑음();
					break;
				case 3:
					wether.흐림();
					break;
				case 4:
					wether.스콜();
					break;
				}
			} else if (user.늪빠짐 == true) {

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}//for문 끝 
		
		

	}


}

class 무기내구도 implements Runnable {
	User user = new User();
	장비아이템 무기 = new 장비아이템();
	// 생성자
	무기내구도(User user,장비아이템 무기) {
		this.user = user;
		this.무기 = 무기;
	}

	
	
	public void run() {
		

//				if(user.무기.contains(무기.name)) {
//					
//					for (무기.내구도 = 무기.내구도; 무기.내구도 >= 0; 무기.내구도--) {
//						System.out.println("내구도 하락");
//						if (무기.내구도 == 0) {
//							System.out.println(무기.name+"의 내구도가 0이 되었습니다.");
//							System.out.println("무기가 파괴되었습니다.");
//							user.strong -= 5;
//							user.공포감 += 5;
//							user.무기.remove(무기.name);
//							break;
//						}
//						try {
//							Thread.sleep(300);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//					
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					
//				}

		
		 for (;;) {

			if (user.무기.contains("망가진도끼")) {

				for (int i = 20; i >= 0; i--) {

					if (i == 0) {
						System.out.println("망가진도끼의 내구도가 0이 되었습니다.");
						System.out.println("무기가 파괴되었습니다.");
						user.strong -= 5;
						user.공포감 += 5;
						user.무기.remove("망가진도끼");
						break;
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			} else if (user.무기.contains("망가진톱")) {

				for (int i = 20; i >= 0; i--) {

					if (i == 0) {
						System.out.println("망가진톱의 내구도가 0이 되었습니다.");
						System.out.println("무기가 파괴되었습니다.");
						user.strong -= 5;
						user.공포감 += 5;
						user.무기.remove("망가진톱");
						break;
					}

					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			} else if (user.무기.contains("나무무기")) {

				for (int i = 30; i >= 0; i--) {
					if (i == 0) {
						System.out.println("나무무기의 내구도가 0이 되었습니다.");
						System.out.println("무기가 파괴되었습니다.");
						user.strong -= 6;
						user.공포감 += 10;
						user.무기.remove("나무무기");
						break;
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			} else if (user.무기.contains("뼈무기")) {

				for (int i = 50; i >= 0; i--) {

					if (i == 0) {
						System.out.println("뼈무기의 내구도가 0이 되었습니다.");
						System.out.println("무기가 파괴되었습니다.");
						user.strong -= 10;
						user.공포감 += 20;
						user.무기.remove("뼈무기");
						break;
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				continue;
			} // if문끝

		} // 무한반복 for문 끝
	}// run끝
}

class 갑옷내구도 implements Runnable {
	User user = new User();

	// 생성자
	갑옷내구도(User user) {
		this.user = user;
	}

	
	
	
	
//	try {
//		if(!Thread.currentThread().isInterrupted()) {
//
//			if(user.무기.contains("망가진도끼")) {
//				
//				
//				
//				
//			} else if (user.무기.contains("망가진톱")) {
//				
//				
//				
//			} else if (user.무기.contains("나무무기")) {
//				
//				
//				
//			} else if (user.무기.contains("뼈무기")) {
//				
//				
//				
//			}
//			
//			
//			
//			
//			
//			
//			
//		}
//	} catch(Exception e) {
//		
//	}
	
	
	
	
	
	
	
	
	
	
	public void run() {
		시스템 시스템 = new 시스템();
		for (;;) {

			if (user.갑옷.contains("나무갑옷")) {

				for (int i = 30; i >= 0; i--) {
					if (i == 0) {
						System.out.println("나무갑옷의 내구도가 0이 되었습니다.");
						System.out.println("갑옷이 파괴되었습니다.");
						user.strong -= 0;
						user.hp -= 10;
						user.공포감 += 10;
						user.갑옷.remove("나무갑옷");
						시스템.userHpCheck(user);
						break;
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			} else if (user.갑옷.contains("가죽갑옷")) {

				for (int i = 40; i >= 0; i--) {
					if (i == 0) {
						System.out.println("가죽갑옷의 내구도가 0이 되었습니다.");
						System.out.println("갑옷이 파괴되었습니다.");
						user.strong -= 0;
						user.hp -= 20;
						user.공포감 += 15;
						user.갑옷.remove("가죽갑옷");
						시스템.userHpCheck(user);
						break;
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			} else if (user.갑옷.contains("뼈갑옷")) {

				for (int i = 50; i >= 0; i--) {
					if (i == 0) {
						System.out.println("뼈갑옷의 내구도가 0이 되었습니다.");
						System.out.println("갑옷이 파괴되었습니다.");
						user.strong -= 0;
						user.hp -= 30;
						user.공포감 += 20;
						user.갑옷.remove("뼈갑옷");
						시스템.userHpCheck(user);
						break;
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		}
	}// run끝
}

class 독지속 implements Runnable {
	User user = new User();

	// 생성자
	독지속(User user) {
		this.user = user;
	}

	public void run() {
		시스템 시스템 = new 시스템();
		if (user.독 == true) {
			for (int i = 0; i < 3; i++) {
				if(user.hp<=0) {
					시스템.userHpCheck(user);
					break;
				}
				System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
				System.out.println("현재 독에 걸렸습니다.");
				System.out.println("체력이 3 감소합니다.");
				user.hp -= 3;
				System.out.println("현재 체력 : " + user.hp);
				System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊\n");
				
				if(user.hp<=0) {
					시스템.userHpCheck(user);
					break;
				}
				try {
					Thread.sleep(3500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
			System.out.println("상태이상 독의 지속시간이 끝났습니다.");
			System.out.println("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊\n");
			user.독 = false;
		} else if (user.독 == false) {
			System.out.println();
		}

	}
}

class 청결포만목마름감소 implements Runnable {
	User user;

	청결포만목마름감소(User user) {
		this.user = user;
	}

	public void run() {
		시스템 시스템 = new 시스템();
		for (;;) {

//			System.out.println("청결도, 포만감, 목마름이 감소하였습니다.");
			user.clean -= 1;
			user.hunger -= 1;
			user.thirsty += 1;

			시스템.CHTcheck(user);

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // for문끝

	}// run끝
}

//class 음악 extends Thread {
//
//	private Player player;
//	private boolean isLoop;
//	private File file;
//	private FileInputStream fis;
//	private BufferedInputStream bis;
//
//	public 음악(String name, boolean isLoop) {
//		try {
//			this.isLoop = isLoop;
//			file = new File(GameDemo3실행.class.getResource("../music/" + name).toURI());
//			fis = new FileInputStream(file);
//			bis = new BufferedInputStream(fis);
//			player = new Player(bis);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//
//	public void close() {
//		isLoop = false;
//		player.close();
//		this.interrupt();
//	}
//
//	@Override
//	public void run() {
//		try {
//			do {
//				player.play();
//				fis = new FileInputStream(file);
//				bis = new BufferedInputStream(fis);
//				player = new Player(bis);
//			} while (isLoop);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//}

class 하루시간 implements Runnable {
	User user;

	하루시간(User user) {
		this.user = user;
	}

	public void run() {

		try {
			if (!Thread.currentThread().isInterrupted()) {
				user.현재시간 = "오전";
				Thread.sleep(5000);

				user.현재시간 = "오후";
				Thread.sleep(5000);

				user.현재시간 = "밤";
				Thread.sleep(5000);

			}
		} catch (Exception e) {

		}

	}
}

class 미니게임시간 implements Runnable {
	int i;
//	private boolean stop;

	미니게임시간() {
	}

	public void run() {
		시스템 시스템 = new 시스템();
		System.out.println("<<카운트다운이 시작됩니다>>");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		try {
			
			if (!Thread.currentThread().isInterrupted()) {
				for (i = 10; i >= 0; i--) {

					System.out.println("\t" + i + " ");
					Thread.sleep(1000);

				}
				시스템.늪BadEnd();

			}
		} catch (Exception e) {

		}
	}// run
}

class 타이틀 implements Runnable {

	public void 슬립() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.print("\n\n"
				+ "██████╗ ███████╗███████╗███████╗██████╗ ████████╗    ██╗███████╗██╗      █████╗ ███╗   ██╗██████╗     \r\n");
		슬립();
		System.out.print(
				"██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗╚══██╔══╝    ██║██╔════╝██║     ██╔══██╗████╗  ██║██╔══██╗    \r\n");
		슬립();
		System.out.print(
				"██║  ██║█████╗  ███████╗█████╗  ██████╔╝   ██║       ██║███████╗██║     ███████║██╔██╗ ██║██║  ██║    \r\n");
		슬립();
		System.out.print(
				"██║  ██║██╔══╝  ╚════██║██╔══╝  ██╔══██╗   ██║       ██║╚════██║██║     ██╔══██║██║╚██╗██║██║  ██║    \r\n");
		슬립();
		System.out.print(
				"██████╔╝███████╗███████║███████╗██║  ██║   ██║       ██║███████║███████╗██║  ██║██║ ╚████║██████╔╝    \r\n");
		슬립();
		System.out.print(
				"╚═════╝ ╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝     \r\n");
		슬립();
		System.out.print("\n\n\t\t\t\t    시작하려면 Enter를 눌러주세요. ");

	}
}

//로딩스레드
class 로딩 implements Runnable {

	public void run() {
		Random rnd = new Random();
		int a = rnd.nextInt(100);
		if (a < 20) {
			System.out.println(" \tTIP : 날씨변화로 질병에 걸리면 스탯이 하락합니다.\n");
		} else if (a < 40) {
			System.out.println(" \tTIP : 파손된 리조트에서는 공포감 스탯 관리에 유의하세요.\n");
		} else if (a < 60) {
			System.out.println(" \tTIP : 말하는 원숭이와 물건을 교환할 수 있습니다.\n");
		} else if (a < 80) {
			System.out.println(" \tTIP : 더 다양한 설명은 도움말 메뉴를 이용해주세요.\n");
		} else if (a < 100) {
			System.out.println(" \tTIP : 탐색을 할 경우 말하는 원숭이를 만날수도 있습니다.\n");
		}

		System.out.print(" \tLoading\t");

		for (int i = 0; i < 20; i++) {
			System.out.print("▶");
			try {
				Thread.sleep(100);
			} catch (Exception e) {

			}
		}
		System.out.println("\n\n");

	}// run끝
}

class Item {
	boolean 섭취유무;
	String name ="-";
	int hpUp;
	int mentalUp;

	// 기본생성자
	Item() {
		this.name = "";
		this.hpUp = 0;
		this.mentalUp = 0;
	}

	// 못먹는 아이템 생성자
	Item(String name) {
		this.섭취유무 = false;
		this.name = name;
	}

}

class 섭취아이템 extends Item {
	// 변수
	boolean 음식;
	boolean 약;
	int 포만감상승;
	int 목마름감소;
	boolean 해독; // 일케 쓰는게 맞나..

	// 생성자
	섭취아이템() {
	}

	섭취아이템(String name, int hpup, int mentalup, int 포만감상승, int 목마름감소, boolean 음식, boolean 약, boolean 해독) {
		this.섭취유무 = true;
		this.해독 = 해독;
		this.음식 = 음식;
		this.약 = 약;
		this.name = name;
		this.hpUp = hpup;
		this.mentalUp = mentalup;
		this.포만감상승 = 포만감상승;
		this.목마름감소 = 목마름감소;
	}

	// 해독제 생성자
	섭취아이템(String name, boolean 약, boolean 해독) {
		// 일케하면 유저 독이 flase가 되는거 아닌가?
		this.섭취유무 = true;
		this.약 = 약;
		this.해독 = 해독;
		this.name = name;
	}

}

class 장비아이템 extends Item {
	int 공포감감소;
	int 공격력상승;
	int 체력상승;
	int 내구도;

	장비아이템() {
		this.name = "";
		this.공격력상승 = 0;
		this.체력상승 = 0;
		this.공포감감소 = 0;
		this.내구도 = 0;
	}

	장비아이템(String name, int 공격력상승, int 체력상승, int 공포감감소, int 내구도) {
		this.name = name;
		this.공격력상승 = 공격력상승;
		this.체력상승 = 체력상승;
		this.공포감감소 = 공포감감소;
		this.내구도 = 내구도;
	}

}

public class GameDemo3 {

	public static void main(String[] args) {

		// 맵 별 출현 몬스터
//		숲속 늪 파손된 리조트 파손된 배 동굴 해변

//		숲속 - 단데기 뱀 사람? 원숭이
//		늪 - 뱀 악어 박쥐  단데기
//		파손된 배 - 피라냐  쥐 고릴라 사람?
//		파손된 리조트 - 사람? 재규어 머리가 없는 사람? 	한 몸이 된 두 사람? 
//		해변 - 거북이 원숭이  고릴라 피라냐

		// 쓰레드

		// 객체생성
		User user = new User(); // 선택한 캐릭터 객체를 저장할 객체
		Animal animal = new Animal(); // 랜덤 몬스터의 객체를 저장할 객체
		Animal randomFish = new Animal();// 랜덤으로 생성된 물고기 객체를 저장할 객체
		시스템 시스템 = new 시스템();

		Item item = new Item(); // 식사시 선택한 메뉴의 객체 저장할 객체

		SpecialMonkey specialmonkey = new SpecialMonkey(); // 상점 캐릭터 객체 생성

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

		// 섭취 불가 아이템
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

		// 장비아이템
		장비아이템 나무갑옷 = new 장비아이템("나무갑옷", 0, 10, 10, 30);
		장비아이템 가죽갑옷 = new 장비아이템("가죽갑옷", 0, 20, 15, 40);
		장비아이템 뼈갑옷 = new 장비아이템("뼈갑옷", 0, 15, 30, 50);
		장비아이템 망가진도끼 = new 장비아이템("망가진도끼", 5, 0, 5, 20);
		장비아이템 망가진톱 = new 장비아이템("망가진톱", 5, 0, 5, 20);
		장비아이템 나무무기 = new 장비아이템("나무무기", 6, 0, 10, 30);
		장비아이템 뼈무기 = new 장비아이템("뼈무기", 10, 0, 20, 50);

		System.out.println("\n\n" + "\t\t\t\t무인도에서 살아남기" + "\n\n\t\t\t\t    시작하려면 ENTER를 입력하세요. ");

		Scanner scanner = new Scanner(System.in);
		String a = scanner.nextLine();

		// 캐릭터 선택 반복문
		for (;;) {
			// 캐릭터 선택창 (스킬 아이템 수정)
			System.out.println("\n\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n\n"
					+ "\t 1.백수 \t\t\t\t 2.직장인 \t\t\t\t 3.할머니 \n\n" + "\t 체력 : 100  \t\t\t 체력 : 80 \t\t\t 체력 : 60  \n"
					+ "\t 정신력 : 100 \t\t\t 정신력 : 80 \t\t\t 정신력 : 60  \n"
					+ "\t 공격력 : 10 \t\t\t 공격력 : 9 \t\t\t 공격력 : 8  \n" + "\t 방어력 : 7 \t\t\t 방어력 : 6 \t\t\t 방어력 : 5  \n"
					+ "\t 포만감 : 100 \t\t\t 포만감 : 100 \t\t\t 포만감 : 100  \n"
					+ "\t 목마름 : 0 \t\t\t 목마름 : 0 \t\t\t 목마름 : 0 \n"
					+ "\t 청결도 : 100 \t\t\t 청결도 : 100 \t\t\t 청결도 : 100  \n" + "\n\t\t\t\t\t 캐릭터를 선택하세요.  \n\n"
					+ "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");

//			user.characterSelect = scanner.nextInt();
//
//			if (user.characterSelect == 1) {
//				NonWork nonwork = new NonWork();
//				user = nonwork;
//
//			} else if (user.characterSelect == 2) {
//				Work work = new Work();
//				user = work;
//
//			} else if (user.characterSelect == 3) {
//				Grand grand = new Grand();
//				user = grand;
//			}

			System.out.println("\n\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n\n"
					+ "\t\t\t\t\t" + user.name + "을/를 선택했습니다.\n\n" + "\t\t\t\t\t 체력 : " + user.hp + "\n"
					+ "\t\t\t\t\t 정신력 : " + user.mental + "\n" + "\t\t\t\t\t 공격력 : " + user.strong + "\n"
					+ "\t\t\t\t\t 포만감 : " + user.hunger + "\n" + "\t\t\t\t\t 목마름 : " + user.thirsty + "\n"
					+ "\t\t\t\t\t 청결도 : " + user.clean + "\n" + "\t\t\t\t 1. 시작 \t\t\t 2. 다시 선택 \n" + "\n"
					+ "ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n");

			while (!scanner.hasNextInt()) {
				scanner.next();
				System.out.println("잘못 입력했습니다. 다시 입력하세요.\n");
			}
			int startBack = scanner.nextInt();

			if (startBack == 1) {
				break;
			} else if (startBack == 2) {
				continue;
			}
		}

		System.out.println("으..뭐야..여긴 어디지...?" + "\n난 분명 배에서 자고 있었는데..?\n" + "여보세요!!!\n" + "아무도 없어요????\n"
				+ "저기요!!!!!! \n" + "뭐야...여기 설마 무인도....????\n" + "\t \n" + "-----재료를 모아 10일차에 구조신호를 보내세요-----\n"
				+ "\n계속 하려면 Enter를 입력하세요.\n\n");

		String b = scanner.nextLine();

		// 1일차 시작
		System.out.println("\n1일차\n");
		System.out.println("1일 오전");

		System.out.println("\n2일차\n");
		System.out.println(user.inventory);
		System.out.println("체력 : " + user.hp);
		user.status(user);

		System.out.println("\n3일차\n");



	}
}
