package com.example.marek.paintactivity;

// Created by marek on 2015-09-30.

public class Calculculations {

 /*  public void Calculculations(){

   }*/
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
    }
    public int convert_Byte_to_Int(byte b) {
        if (b < 0) {
            return b + 256;
        } else return b;
    }
   public byte MSB_part_of_Bajt(int licznik){


        return(byte)((licznik>>8) & 0x000000FF);
    }

  public byte LSB_part_of_Bajt(int licznik){

        return (byte)(licznik & 0x000000FF);
    }
}
