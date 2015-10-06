package com.example.marek.paintactivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewDebug;

/*
  Created by marek on 2015-09-16.
 */
 class ProcessingFrame extends Thread {

    public byte[] readBuffer;
    public int bytes;
    public byte[] bufer = new byte[300];
    public double[]  graph_point = new double[150];
    public int stary_bajt;
    public int mlody_bajt;
    public int bytes_all;
    public double point;
    public int CRC;
    public int CRC_parse;
    private boolean SET_THREAD = true;
    public int  Error_count;
    protected static final int SUCCESS_CONNECT = 0;
    protected static final int DISCONECT = 2;
    public boolean flaga = false;
    Calculculations calculations = new Calculculations();
    static Handler mHandler = new Handler();
    public static void get_handler(Handler handler){mHandler = handler;}
    Handler hHandler;{
        hHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                        case Bluetooth.MESSAGE_READ:
                             if (Bluetooth.connectedThread != null) {
                                 Konstruktor(msg.obj, msg.arg1);
                                 break;
                             }
                            case Bluetooth.DISCONECT:{
                                mHandler.obtainMessage(DISCONECT).sendToTarget();
                                break;
                            }
                            case Bluetooth.SUCCESS_CONNECT:{
                                mHandler.obtainMessage(SUCCESS_CONNECT).sendToTarget();
                                break;
                            }
                }
            }

        };
    }
    @Override
    public void run() {
        while(SET_THREAD){
            if ((bytes_all>=106)) {
                bytes_all = 0;
                CRC = calculations.CalcCRC16(bufer, 104);
                CRC_parse = parse_bytes(calculations.convert_Byte_to_Int(bufer[105]), calculations.convert_Byte_to_Int(bufer[104]));
                if(CRC==CRC_parse) {
                    int counter = 0;
                for(int i=4;i<104;i+=2){
                    if((graph_point[counter] = parse_bytes(calculations.convert_Byte_to_Int(bufer[i]),calculations.convert_Byte_to_Int(bufer[i+1]))/2048.0)>1){
                        graph_point[counter] = 0;
                    }
                    Log.d("point:", Double.toString(graph_point[counter]));
                    counter++;
                    Log.d("counter",Integer.toString(i));
                }
                    flaga = true;
                }else{
                    Error_count++;
                }


            }

        }

    }

    public ProcessingFrame(){
        Bluetooth.gethandler(hHandler);
    }
    public void Konstruktor (Object obj, int arg1){
        readBuffer = (byte[]) obj;
        bytes = arg1;
        System.arraycopy(readBuffer, 0, bufer, bytes_all, bytes);
        bytes_all+=bytes;
    }

    public int parse_bytes(int mlody, int stary) {
         return 0xFFFF&((stary << 8) | (mlody));
    }

    public double return_voltage(){

        return parse_bytes(mlody_bajt,stary_bajt)*(3.3 / 4095);
    }

 /*   public int convert_Byte_to_Int(byte b) {
        if (b < 0) {
            return b + 256;
        } else return b;
    }

    public int CalcCRC16(byte[] data_array, int data_lenght) {
        int crc = 0xFFFF;
        //byte[] data = data_array;
        for (int i = 0; i < data_lenght; i++) {
            crc ^= (convert_Byte_to_Int(data_array[i])) << 8;
            crc = crc & 0x0000FFFF;
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x8000) > 0) {
                    crc = (crc << 1) ^ 0x1021;
                    crc = crc & 0x0000FFFF;
                }
                else {
                    crc <<= 1;
                    crc = crc & 0x0000FFFF;
                }
            }
        }
        return  crc & 0x0000FFFF;
    }*/

    public void SET_THREAD(boolean b) {
        SET_THREAD = b;
    }
}