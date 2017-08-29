package application;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import javafx.fxml.FXMLLoader;

public class SerialTest implements SerialPortEventListener {
	SerialPort serialPort;

	/** 당신의 아두이노와 연결된 시리얼 포트로 변경해야 한다. */
	public SerialTest() {

	}

	public static void setPort(String port) {
		PORT_NAMES[2] = port;
	}

	private static String PORT_NAMES[] = {

			"/dev/tty.usbserial-A9007UX1", // Mac OS X

			"/dev/ttyUSB0", // Linux

			"COM11", // Windows

	};

	/** 포트에서 데이터를 읽기 위한 버퍼를 가진 input stream */

	private InputStream input;

	/** 포트를 통해 아두이노에 데이터를 전송하기 위한 output stream */

	private OutputStream output;

	/** 포트가 오픈되기 까지 기다리기 위한 대략적인 시간(2초) */

	private static final int TIME_OUT = 0;

	/** 포트에 대한 기본 통신 속도 */

	private static final int DATA_RATE = 9600;

	public boolean initialize() {

		CommPortIdentifier portId = null;

		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();

		// 당신의 컴퓨터에서 지원하는 시리얼 포트들 중 아두이노와 연결된

		// 포트에 대한 식별자를 찾는다.

		while (portEnum.hasMoreElements()) {

			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

			for (String portName : PORT_NAMES) {

				if (currPortId.getName().equals(portName)) {

					portId = currPortId;
					System.out.println("연결중...");
					break;

				}

			}

		}

		// 식별자를 찾지 못했을 경우 종료

		if (portId == null) {

			System.out.println("Could not find COM port.");

			return false;

		}

		try {
			System.out.println("연결 성공");

			// 시리얼 포트 오픈, 클래스 이름을 애플리케이션을 위한 포트 식별 이름으로 사용

			serialPort = (SerialPort) portId.open(this.getClass().getName(),

					TIME_OUT);

			// 속도등 포트의 파라메터 설정

			serialPort.setSerialPortParams(DATA_RATE,

					SerialPort.DATABITS_8,

					SerialPort.STOPBITS_1,

					SerialPort.PARITY_NONE);
			serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_OUT);

			// 포트를 통해 읽고 쓰기 위한 스트림 오픈

			input = serialPort.getInputStream();

			output = serialPort.getOutputStream();

			// 터미널로부터 전송된 데이터를 수신하는 리스너를 등록

			serialPort.addEventListener(this);

			serialPort.notifyOnDataAvailable(true);
			
			return true;
		} catch (Exception e) {
			
			System.err.println(e.toString());
			return false;
		}

	}

	/**
	 * 
	 * 이 메서드는 포트 사용을 중지할 때 반드시 호출해야 한다.
	 * 
	 * 리눅스와 같은 플랫폼에서는 포트 잠금을 방지한다.
	 * 
	 */

	public synchronized void close() {

		if (serialPort != null) {

			serialPort.removeEventListener();

			serialPort.close();

		}

	}

	/**
	 * 
	 * 시리얼 통신에 대한 이벤트를 처리. 데이터를 읽고 출력한다..
	 * 
	 */

	public void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {

			try {

				int available = input.available();

				byte chunk[] = new byte[available];

				input.read(chunk, 0, available);
				// 바로 출력
				System.out.print(new String(chunk));
				
				Main.serialData += new String(chunk);
				
				if(new String(chunk).contains("\n")) {
					Main.serialData.replaceAll("\n", "");
					Main.serialData.replaceAll("\r", "");
					Main.mainController.receiveData();
					Main.serialData = "";
				}
				
				
//				if(Main.serialData.length() >= 18) {
//					
//					Main.mainController.receiveData();
//					System.out.println(Main.serialData);
//					Main.serialData = "";
//				}else if(Main.serialData.length() >= 12) {
//					
//				}
				
			} catch (Exception e) {

				e.printStackTrace();

			}

		}

		// 다른 이벤트 유형은 무시한다. 만약 당신이 필요한 이벤트가 있으면 추가하면된다.

		// 다른 이벤트에 대한 것은 SerialPortEvent 소스를 참조

	}

}