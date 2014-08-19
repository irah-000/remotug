package fi.uef.remotug.sensor;

import fi.conf.ae.routines.S;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Sensor {

	private final List<SensorListener> sensorListeners = new ArrayList<>();
	private static boolean continueToRead = true;
	private Thread readerThread; 
	
	public void start(String portName) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException {
		
		if(portName == ""){
			S.debug("Port name empty, initializing fake data provider");
			
			readerThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while (continueToRead){
						try {
							//announceSensorChange(kg);
						} catch(NumberFormatException e2) {
							e2.printStackTrace();
						}
						
						try {
							Thread.sleep(33);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			
			readerThread.start();
			
			return;
		}
		
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		
		if ( portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);

			if ( commPort instanceof SerialPort ) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				final InputStream in = serialPort.getInputStream();
				readerThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						BufferedReader reader = new BufferedReader(new InputStreamReader(in));
						while (continueToRead){
							try {
								//TODO get data tyyliin Integer.valueOf(reader.readLine().trim())
								//TODO announceSensorChange(kg);
							} catch(NumberFormatException e2) {
								e2.printStackTrace();
							}
						}
					}
				});
				
				readerThread.start();
			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}     
	}
	
	public void stop(){
		continueToRead = false;
	}

	public void addListener(SensorListener	sensorListener) {
		this.sensorListeners.add(sensorListener);
	}

	private void announceSensorChange(float kg){
		for(SensorListener l : sensorListeners){
			l.newSensorDataArrived(kg);
		}
	}

}
