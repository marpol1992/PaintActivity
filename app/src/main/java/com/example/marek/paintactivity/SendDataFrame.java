package com.example.marek.paintactivity;

import java.util.Iterator;

// Created by marek on 2015-09-30.

public class SendDataFrame {
    private final byte Header_frame        = (byte)0xFF;
    private int Lenght_Data = 0;
    private byte[] Data;
    private final byte Command_to_startADC = 0x04;
    private final byte Command_to_stopADC  = 0x05;
    private byte[] Frame = new byte[6];
    Calculculations calculations = new Calculculations();
    public  SendDataFrame(){
        Frame[0] = Header_frame;
    }
    public void Request_to_startADC(){
        Frame[1] = Command_to_startADC;
        Frame[2] = calculations.MSB_part_of_Bajt(Lenght_Data);
        Frame[3] = calculations.LSB_part_of_Bajt(Lenght_Data);
        Frame[4] = calculations.MSB_part_of_Bajt(calculations.CalcCRC16(Frame, 4));
        Frame[5] = calculations.LSB_part_of_Bajt(calculations.CalcCRC16(Frame, 4));
        Bluetooth.connectedThread.write(Frame);
        Bluetooth.connectedThread.write(Frame);
        Bluetooth.connectedThread.write(Frame);
        Bluetooth.connectedThread.write(Frame);
        Bluetooth.connectedThread.write(Frame);

/*        for(byte frame: Frame){
            Bluetooth.connectedThread.write(frame);
        }
        for (Iterator<String> i = Frame.iterator(); i.hasNext(); ){

        }*/
    }
    public void Request_to_stopADC(){
        Frame[1] = Command_to_stopADC;
        Frame[2] = calculations.MSB_part_of_Bajt(Lenght_Data);
        Frame[3] = calculations.LSB_part_of_Bajt(Lenght_Data);
        Frame[4] = calculations.MSB_part_of_Bajt(calculations.CalcCRC16(Frame, 4));
        Frame[5] = calculations.LSB_part_of_Bajt(calculations.CalcCRC16(Frame, 4));
        Bluetooth.connectedThread.write(Frame);
        Bluetooth.connectedThread.write(Frame);
        Bluetooth.connectedThread.write(Frame);
        Bluetooth.connectedThread.write(Frame);
    }

}
