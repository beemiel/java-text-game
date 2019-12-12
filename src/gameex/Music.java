//package gameex;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//
//import javazoom.jl.player.Player;
//
//public class Music extends Thread{
//	
//	private Player player;
//	private boolean isLoop; //무한반복인지 한번재생하고 끝인지
//	private File file;
//	private FileInputStream fis;
//	private BufferedInputStream bis;
//	
//	public Music(String name, boolean isLoop) {
//		try {
//			this.isLoop = isLoop; //변수초기화
//			file = new File(GameDemo3실행.class.getResource("../music/"+name).toURI());
//			fis = new FileInputStream(file);
//			bis = new BufferedInputStream(fis);  //해당파일을 버퍼에 담아서 읽어올 수 있도록 함
//			player = new Player(bis);
//		} catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//	//실행되고있는 음악이 현재 어떤 위치에? 실행되고 있는지 알려줌
//	//현재 십초까지 재생되고있다면 이 함수는  10000이라는 숫자를  반환
//	public int getTime() {
//		if(player == null)
//			return 0;
//		return player.getPosition();
//	}
//	//언제 실행되고 있던간에 항상 종료할 수 있도록 하는 함수
//	//(리듬게임예제)플레이어가 플레이를 중단하고 뒤로가기를 누를때  
//	//그때 클로즈 함수를 실행시켜서 해당곡이 안정적으로 종료가 될 수 있도록 함
//	public void close() {
//		isLoop = false;
//		player.close();
//		this.interrupt(); //해당 쓰레드를 중지상태로 만드는 것
//	}
//	
//	@Override
//	public void run() {
//		try {
//			//isLoop가 ture면 무한반복이 되게 함 
//			do {
//				player.play();
//				fis = new FileInputStream(file);
//				bis = new BufferedInputStream(fis);  //해당파일을 버퍼에 담아서 읽어올 수 있도록 함
//				player = new Player(bis);
//			} while (isLoop);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//	
//}
