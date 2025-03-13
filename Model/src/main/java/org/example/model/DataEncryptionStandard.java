package org.example.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataEncryptionStandard {
    private final byte[] pc1 = {
            57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4
    };
    private final int[] shift = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    private final byte[] pc2 = {
            14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32
    };
    private final byte[] ip = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };
    private final byte[] ex = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };
    private final byte[][] sbox = {
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, // S1
                    0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
                    4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
                    15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13},
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, // S2
                    3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
                    0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
                    13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9},
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, // S3
                    13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
                    13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
                    1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12},
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, // S4
                    13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
                    10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
                    3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14},
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, // S5
                    14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
                    4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
                    11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3},
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, // S6
                    10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
                    9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
                    4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13},
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, // S7
                    13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
                    1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
                    6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12},
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, // S8
                    1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
                    7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
                    2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };
    private final byte[] pblock = {
            16, 7, 20, 21, 29, 12, 28, 17,
            1, 15, 23, 26, 5, 18, 31, 10,
            2, 8, 24, 14, 32, 27, 3, 9,
            19, 13, 30, 6, 22, 11, 4, 25
    };
    private final byte[] ip1 = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    private List<byte[]> blocks;

    private byte[] keyBytes;
    private byte[][] keysBytes;

    public DataEncryptionStandard(String key, String msg,boolean hex) {
        BigInteger k=new BigInteger(key,16);
        this.keyBytes=k.toByteArray();

        if(hex){
            BigInteger bg=new BigInteger(msg,16);
            byte[] _ba=bg.toByteArray();

            if(_ba.length%8==1 && _ba[0]==0){
                _ba= Arrays.copyOfRange(_ba, 1, _ba.length);
            }
            createBlocks(_ba);
        }
        else{
            createBlocks(msg.getBytes());
        }
    }

    public DataEncryptionStandard(String[] keys, String msg,boolean hex) {
        this.keysBytes=new byte[keys.length][8];
        for(int i=0; i<keys.length; i++){
            BigInteger k=new BigInteger(keys[i],16);
            this.keysBytes[i]=k.toByteArray();
        }
        if(hex){
            BigInteger bg=new BigInteger(msg,16);
            byte[] _ba=bg.toByteArray();

            if(_ba.length%8==1 && _ba[0]==0){
                _ba= Arrays.copyOfRange(_ba, 1, _ba.length);
            }
            createBlocks(_ba);
        }
        else{
            createBlocks(msg.getBytes());
        }
    }

    private void createBlocks(byte[] str){
        int _len=str.length;
        int len=_len%8!=0?((_len/8+1)*8):_len;
        byte[] data=new byte[len];

        System.arraycopy(str,0,data,0,_len);

        this.blocks=new ArrayList<>();

        for(int i=0;i<data.length;i+=8){
            byte[] block=new byte[8];
            System.arraycopy(data,i,block,0,8);
            this.blocks.add(block);
        }
    }

    public String desEncryption() {
        StringBuilder out= new StringBuilder();
        for(byte[] block:this.blocks){
            long a = des(this.keyBytes, block, false);
            out.append(String.format("%016x", a));
            System.out.println("Str: "+new String(longToBytes(a)));
        }
        System.out.println("DES Encrypt: "+out);
        return out.toString();
    }

    public String desXEncryption() {
        StringBuilder out= new StringBuilder();
        for(byte[] block:this.blocks){
            long a = desx(this.keysBytes, block, false);
            out.append(String.format("%016x", a));
            System.out.println("Str: "+new String(longToBytes(a)));
        }
        System.out.println("DESX Encrypt: "+out);
        return out.toString();
    }

    public String desDecryption() {
        StringBuilder out= new StringBuilder();
        for(byte[] block:this.blocks){
            long a = des(this.keyBytes, block, true);
            out.append(String.format("%016x", a));
            System.out.println("Str: "+new String(longToBytes(a)));
        }
        System.out.println("DES Decrypt: "+out);
        return out.toString();
    }

    public String desXDecryption() {
        StringBuilder out= new StringBuilder();
        for(byte[] block:this.blocks){
            long a = desx(this.keysBytes, block, true);
            out.append(String.format("%016x", a));
            System.out.println("Str: "+new String(longToBytes(a)));
        }
        System.out.println("DESX Decrypt: "+out);
        return out.toString();
    }

    private long des(byte[] keyB, byte[] msgB, boolean decrypt) {
        long key64 = bytesToLong(keyB);
        long key56 = selectBits(key64, this.pc1, 64);
        int key28l = (int) (key56 >> 28);
        int key28r = (int) (key56 & 0xfffffff);
        long[] keys56 = new long[17];
        keys56[0] = key56;
        for (int i = 0; i < 16; i++) {
            key28l = rotLeft(key28l, this.shift[i]);
            key28r = rotLeft(key28r, this.shift[i]);
            keys56[i + 1] = ((long) key28l << 28) | key28r;
        }
        long[] keys48 = new long[17];
        for (int i = 0; i < 17; i++) {
            keys48[i] = selectBits(keys56[i], this.pc2, 56);
        }
        long msg = bytesToLong(msgB);
        long msg64 = selectBits(msg, this.ip, 64);
        int msg32l = (int) (msg64 >> 32);
        int msg32r = (int) (msg64);

        for (int i = 0; i < 16; i++) {
            long next = 0;
            long k = selectBits(msg32r, this.ex, 32) ^ keys48[decrypt ? 16 - i : i + 1];
            for (int j = 0; j < 8; j++) {
                int bit6 = (int) ((k >> j * 6) & 0b11_1111);
                int row = (((bit6 & 0b10_0000) >> 4) & 0b10) | (bit6 & 1);
                int col = (bit6 & 0b01_1110) >> 1;
                next |= ((long) this.sbox[7 - j][16 * row + col]) << (j * 4);
            }
            int f = (int) selectBits(next, this.pblock, 32);
            int lxor = msg32l ^ f;
            msg32l = msg32r;
            msg32r = lxor;
        }
        long msg64rl = ((long) msg32r << 32) | ((long) msg32l & 0xffff_ffffL);
        long finalMsg = selectBits(msg64rl, this.ip1, 64);
        return finalMsg;
    }

    private long desx(byte[][] keys, byte[] msgB, boolean decrypt) {
        long key1 = bytesToLong(keys[decrypt ? 2 : 0]);
        long key3 = bytesToLong(keys[decrypt ? 0 : 2]);
        long msg = bytesToLong(msgB);
        long pxork = msg ^ key3;
        long des = des(keys[1], longToBytes(pxork), decrypt);
        long finalMsg = des ^ key1;
        return finalMsg;
    }

    private long selectBits(long key64, byte[] map, long bits) {
        long out64 = 0;
        for (int i = 0; i < map.length; i++) {
            out64 |= ((key64 >> (bits - map[i])) & 1) << (map.length - i - 1);
        }
        return out64;
    }

    private int rotLeft(int keyHalf, int shift) {
        int tmp = (keyHalf & ((1 << 31) >> (3 + shift))) >> (28 - shift);
        keyHalf <<= shift;
        keyHalf |= tmp;
        keyHalf &= 0xfff_ffff;
        return keyHalf;
    }

    private long bytesToLong(byte[] b) {
        long out = 0;
        for (int i = 0; i < b.length; i++) {
            out <<= 8;
            out |= (b[i] & 0xFF);
        }
        return out;
    }

    private byte[] longToBytes(long in) {
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (in >> (8 * (7 - i)) & 0xFF);
        }
        return b;
    }

}