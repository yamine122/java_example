package day05;

import java.util.Scanner;

public class Park {
	private final static int MAX_SIZE = 3;
	private final static int UNIT_MIN = 10;
	private final static int UNIT_PRICE = 1000;
	private String plateNum;
	private int inTime;
	private int outTime;

	public String getPlateNum() {
		return plateNum;
	}

	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}

	public int getInTime() {
		return inTime;
	}

	public void setInTime(int inTime) {
		this.inTime = inTime;
	}

	public int getOutTime() {
		return outTime;
	}

	public void setOutTime(int outTime) {
		this.outTime = outTime;
	}

	public void init() {
		Park[] parkArray = new Park[MAX_SIZE];
		for (int i = 0; i < parkArray.length; i++) {
			parkArray[i] = new Park();
		}
		showMenu(parkArray);
	}

	private void showMenu(Park[] parkArray) {
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("��Ʈ ������ ���� ���α׷�");
			System.out.print("1. �԰� 2. ��� 3. ���� : ");
			int choice = scan.nextInt();
			scan.nextLine();
			if (choice == 3) {
				System.out.println("������ּż� �����մϴ�.");
				break;
			} else if (choice == 1) {
				insertPark(parkArray, scan);
			} else if(choice ==2) {
				deletePark(parkArray, scan);
			}
		}
	}
	
	private void deletePark(Park[] parkArray, Scanner scan) {
		//���� ����
		// ���� �Ѵ�� ���Դ��� üũ -> ���� ��ȣ�� �����ϴ��� Ȯ��
		// �Է¹��� ���� ��ȣ�� �� �ð� �ҷ����� -> �ð��� ��ȿ���� ->
		// ��� ��� �� �ش� ĭ ����ֱ�
		
		//���� �Ѵ�� ���Դ��� üũ
		if(isParkExist(parkArray)) {
			//���� �Ѵ�� ���� �����ϱ�, ������ ��� ����
			System.out.print("������ ������ ��ȣ�� �Է����ּ���: ");
			String plateNum = scan.nextLine();
			while(!findCar(parkArray, plateNum)) {
				System.out.println("�԰�� ����� �����ϴ�.");
				System.out.print("������ ������ ��ȣ�� �Է����ּ���: ");
				plateNum = scan.nextLine();
			}
			int inTime = findInTime(parkArray, plateNum);
			
			System.out.print("�����ð��� �Է����ּ���: ");
			int outTime = scan.nextInt();
			scan.nextLine();
			while(outTime < inTime || !validateTime(outTime)) {
				System.out.println("�ð��� �ùٸ��� �ʽ��ϴ�.");
				System.out.print("�����ð��� �Է����ּ���: ");
				outTime = scan.nextInt();
				scan.nextLine();
			}
			
			//���� ��� �����ֱ� �Ϸ�
			calcRate(inTime, outTime);
			
			//���� ������ ĭ ����ֱ�
			for(int i = 0; i < parkArray.length; i++) {
				if(parkArray[i].getPlateNum() != null) {
					if(parkArray[i].getPlateNum().equals(plateNum)) {
						parkArray[i] = new Park();
					}
				}
			}
			
		}else {
			//���� �Ѵ뵵 ������ �޽��� ��� �� �޴��� ���ư���.
			System.out.println("������ ������ �����ϴ�.");
		}
	}
	private void calcRate(int inTime, int outTime) {
		//���� ����
		//��ݰ����� ����
		//�����ð� ������ �ٲٰ� ���� �ð� ������ �ٲ㼭
		//��Ż ���� ���̸� ���
		//��Ż �� / 10 * 1000 -> ������ 10, 1000 �Ѵ� ������ ���ڴϱ�
		//�����ٰ� ���� ����� ����ô�.
		
		//1. �ð� ���� ��� �� ������ �ٲٱ�
		int hourDiff = outTime / 100 * 60 - inTime / 100 * 60;
		int minDiff = outTime % 100 - inTime % 100;
		int rate = (hourDiff + minDiff) / UNIT_MIN * UNIT_PRICE;
		System.out.println("��������� "+rate+"���Դϴ�.");
		
	}
	
	//������ ���۵� �ð��� �޾ƿ��� �޼ҵ�
	private int findInTime(Park[] parkArray, String plateNum) {
		for(Park p : parkArray) {
			if(p.getPlateNum() != null) {
				if(p.getPlateNum().equals(plateNum)) {
					return p.getInTime();
				}
			}
		}
		
		return -1;
	}
	
	//���� �Ѵ�� �ִ��� Ȯ���ϴ� �޼ҵ�
	private boolean isParkExist(Park[] parkArray) {
		for(Park p : parkArray) {
			if(p.getPlateNum() != null) {
				return true;
			}
		}
		return false;
	}

	private void insertPark(Park[] parkArray, Scanner scan) {
		// ���� ����
		// ��ĭ �ִ��� Ȯ�� -> ���� ��ȣ�� �����ϴ��� Ȯ�� -> �ð��� ��ȿ���� Ȯ��
		// ���� 3�ܰ� ����ÿ� ��ĭ�� ���� �ִ´�.
		// 1. ��ĭ �ִ��� Ȯ���ϴ� �޼ҵ� ȣ��
		if (checkEmptySpot(parkArray)) {
			// ������ȣ �Է� �ޱ�
			System.out.print("�԰��� ���� ��ȣ�� �Է����ּ���: ");
			String plateNum = scan.nextLine();
			while(findCar(parkArray, plateNum)) {
				System.out.println("�̹� �԰�� �����Դϴ�.");
				System.out.print("�԰��� ���� ��ȣ�� �ٽ� �Է����ּ���: ");
				plateNum = scan.nextLine();
			}
			
			// ���� �ð� �Է� �ޱ�
			System.out.print("���� �ð��� �Է����ּ���: ");
			int inTime = scan.nextInt();
			scan.nextLine();
			while(!validateTime(inTime)) {
				System.out.println("�߸��� �ð������Դϴ�.");
				System.out.print("���� �ð��� �Է����ּ���: ");
				inTime = scan.nextInt();
				scan.nextLine();
			}
			
			insertPark(parkArray, plateNum, inTime);
		} else {
			System.out.println("�������� �� á���ϴ�.");
		}
	}
	private void insertPark(Park[] parkArray, String plateNum, int inTime) {
		for(int i = 0; i < parkArray.length; i++) {
			if(parkArray[i].getPlateNum() == null) {
				parkArray[i].setPlateNum(plateNum);
				parkArray[i].setInTime(inTime);
				break;
			}
		}
	}
	private boolean validateTime(int time) {
		int hour = time / 100;
		int min = time % 100;
		if(hour > -1 && hour < 24 && min > -1 && min < 60) {
			return true;
		}else {
			return false;
		}
	}

	private boolean findCar(Park[] parkArray, String plateNum) {
		for (Park p : parkArray) {
			if (p.getPlateNum() != null) {
				if (p.getPlateNum().equals(plateNum)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkEmptySpot(Park[] parkArray) {
		for (Park p : parkArray) {
			if (p.getPlateNum() == null) {
				return true;
			}
		}

		return false;
	}
}
