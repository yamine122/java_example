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
			System.out.println("비트 주차장 관리 프로그램");
			System.out.print("1. 입고 2. 출고 3. 종료 : ");
			int choice = scan.nextInt();
			scan.nextLine();
			if (choice == 3) {
				System.out.println("사용해주셔서 감사합니다.");
				break;
			} else if (choice == 1) {
				insertPark(parkArray, scan);
			} else if(choice ==2) {
				deletePark(parkArray, scan);
			}
		}
	}
	
	private void deletePark(Park[] parkArray, Scanner scan) {
		//출차 순서
		// 차가 한대라도 들어왔는지 체크 -> 차량 번호가 존재하는지 확인
		// 입력받은 차량 번호로 들어간 시간 불러오기 -> 시간이 유효한지 ->
		// 요금 계산 후 해당 칸 비워주기
		
		//차가 한대라도 들어왔는지 체크
		if(isParkExist(parkArray)) {
			//차가 한대라도 들어와 있으니깐, 나가는 기능 실행
			System.out.print("출차할 차량의 번호를 입력해주세요: ");
			String plateNum = scan.nextLine();
			while(!findCar(parkArray, plateNum)) {
				System.out.println("입고된 기록이 없습니다.");
				System.out.print("출차할 차량의 번호를 입력해주세요: ");
				plateNum = scan.nextLine();
			}
			int inTime = findInTime(parkArray, plateNum);
			
			System.out.print("출차시간을 입력해주세요: ");
			int outTime = scan.nextInt();
			scan.nextLine();
			while(outTime < inTime || !validateTime(outTime)) {
				System.out.println("시간이 올바르지 않습니다.");
				System.out.print("출차시간을 입력해주세요: ");
				outTime = scan.nextInt();
				scan.nextLine();
			}
			
			//주차 요금 보여주기 완료
			calcRate(inTime, outTime);
			
			//나간 차량의 칸 비워주기
			for(int i = 0; i < parkArray.length; i++) {
				if(parkArray[i].getPlateNum() != null) {
					if(parkArray[i].getPlateNum().equals(plateNum)) {
						parkArray[i] = new Park();
					}
				}
			}
			
		}else {
			//차가 한대도 없으니 메시지 출력 후 메뉴로 돌아간다.
			System.out.println("주차된 차량이 없습니다.");
		}
	}
	private void calcRate(int inTime, int outTime) {
		//출차 시작
		//요금계산부터 시작
		//나간시간 분으로 바꾸고 들어온 시간 분으로 바꿔서
		//토탈 분의 차이를 계산
		//토탈 분 / 10 * 1000 -> 하지만 10, 1000 둘다 마법의 숫자니깐
		//위에다가 전역 상수로 만듭시다.
		
		//1. 시간 차이 계산 후 분으로 바꾸기
		int hourDiff = outTime / 100 * 60 - inTime / 100 * 60;
		int minDiff = outTime % 100 - inTime % 100;
		int rate = (hourDiff + minDiff) / UNIT_MIN * UNIT_PRICE;
		System.out.println("주차요금은 "+rate+"원입니다.");
		
	}
	
	//주차가 시작된 시간을 받아오는 메소드
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
	
	//차가 한대라도 있는지 확인하는 메소드
	private boolean isParkExist(Park[] parkArray) {
		for(Park p : parkArray) {
			if(p.getPlateNum() != null) {
				return true;
			}
		}
		return false;
	}

	private void insertPark(Park[] parkArray, Scanner scan) {
		// 입차 순서
		// 빈칸 있는지 확인 -> 차량 번호가 존재하는지 확인 -> 시간이 유효한지 확인
		// 위의 3단계 통과시에 빈칸에 차를 넣는다.
		// 1. 빈칸 있는지 확인하는 메소드 호출
		if (checkEmptySpot(parkArray)) {
			// 차량번호 입력 받기
			System.out.print("입고할 차량 번호를 입력해주세요: ");
			String plateNum = scan.nextLine();
			while(findCar(parkArray, plateNum)) {
				System.out.println("이미 입고된 차량입니다.");
				System.out.print("입고할 차량 번호를 다시 입력해주세요: ");
				plateNum = scan.nextLine();
			}
			
			// 들어온 시간 입력 받기
			System.out.print("들어온 시간을 입력해주세요: ");
			int inTime = scan.nextInt();
			scan.nextLine();
			while(!validateTime(inTime)) {
				System.out.println("잘못된 시간형식입니다.");
				System.out.print("들어온 시간을 입력해주세요: ");
				inTime = scan.nextInt();
				scan.nextLine();
			}
			
			insertPark(parkArray, plateNum, inTime);
		} else {
			System.out.println("주차장이 꽉 찼습니다.");
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
