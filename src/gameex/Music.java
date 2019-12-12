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
//	private boolean isLoop; //���ѹݺ����� �ѹ�����ϰ� ������
//	private File file;
//	private FileInputStream fis;
//	private BufferedInputStream bis;
//	
//	public Music(String name, boolean isLoop) {
//		try {
//			this.isLoop = isLoop; //�����ʱ�ȭ
//			file = new File(GameDemo3����.class.getResource("../music/"+name).toURI());
//			fis = new FileInputStream(file);
//			bis = new BufferedInputStream(fis);  //�ش������� ���ۿ� ��Ƽ� �о�� �� �ֵ��� ��
//			player = new Player(bis);
//		} catch(Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//	//����ǰ��ִ� ������ ���� � ��ġ��? ����ǰ� �ִ��� �˷���
//	//���� ���ʱ��� ����ǰ��ִٸ� �� �Լ���  10000�̶�� ���ڸ�  ��ȯ
//	public int getTime() {
//		if(player == null)
//			return 0;
//		return player.getPosition();
//	}
//	//���� ����ǰ� �ִ����� �׻� ������ �� �ֵ��� �ϴ� �Լ�
//	//(������ӿ���)�÷��̾ �÷��̸� �ߴ��ϰ� �ڷΰ��⸦ ������  
//	//�׶� Ŭ���� �Լ��� ������Ѽ� �ش���� ���������� ���ᰡ �� �� �ֵ��� ��
//	public void close() {
//		isLoop = false;
//		player.close();
//		this.interrupt(); //�ش� �����带 �������·� ����� ��
//	}
//	
//	@Override
//	public void run() {
//		try {
//			//isLoop�� ture�� ���ѹݺ��� �ǰ� �� 
//			do {
//				player.play();
//				fis = new FileInputStream(file);
//				bis = new BufferedInputStream(fis);  //�ش������� ���ۿ� ��Ƽ� �о�� �� �ֵ��� ��
//				player = new Player(bis);
//			} while (isLoop);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//	
//}
