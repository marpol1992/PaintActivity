package com.example.marek.paintactivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by marek on 2015-09-16.
 */
 class ProcessingFrame extends Thread {

    public byte[] readBuffer;
    public int bytes;
    public byte[] bufer = new byte[1900];
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
            if ((bytes_all>=4)) {
                mlody_bajt = convertByte(bufer[0]);
                stary_bajt = convertByte(bufer[1]);
                CRC = CalcCRC16(bufer, 2);
                CRC_parse = parse_bytes(convertByte(bufer[2]), convertByte(bufer[3]));
                if(CRC==CRC_parse) {
                    Log.e("mlody_bajt:", Integer.toString(CRC));
                    Log.e("stary_bajt:", Integer.toString(CRC_parse));
                    Log.d("bytes:", Integer.toString(bytes_all));
                    point = return_voltage();
                    bytes_all = 0;
                    //nadawaj = true;
                }else
                    Error_count++;

                Log.e("mlody_bajt:", Integer.toString(CRC));
                Log.e("stary_bajt:", Integer.toString(CRC_parse));
                Log.d("bytes:", Integer.toString(bytes_all));
                bytes_all = 0;
               // nadawaj = true;
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

        return ((stary << 8) | (mlody));


    }
    public double adc(double zam) {
        if (zam == 0) {
            return 0;
        } else {

            return zam * (3.3 / 4095);
        }
    }
    public double return_voltage(){

        return parse_bytes(mlody_bajt,stary_bajt)*(3.3 / 4095);
    }

    public int convertByte(byte b) {
        if (b < 0) {
            return b + 256;
        } else return b;
    }

    public int CalcCRC16(byte[] data_array, int data_lenght) {
        int crc = 0xFFFF;
        byte[] data = data_array;
        for (int i = 0; i < data_lenght; i++) {
            crc ^= (convertByte(data[i])) << 8;
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
    }

    public void SET_THREAD(boolean b) {
        SET_THREAD = b;
    }
}