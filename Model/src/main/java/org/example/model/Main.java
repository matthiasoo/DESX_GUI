package org.example.model;

import java.math.BigInteger;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        byte[] keyB = new byte[]{
                (byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67,
                (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF
        };
        byte[] msgB = new byte[]{
                (byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67,
                (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF
        };
        byte[] msg1b = new byte[]{
                (byte) 0x41, (byte) 0x42, (byte) 0x43, (byte) 0x44,
                (byte) 0x45, (byte) 0x46, (byte) 0x47, (byte) 0x48
        };
        byte[] crt1b = new byte[]{
                (byte) 0x56, (byte) 0xCC, (byte) 0x09, (byte) 0xE7,
                (byte) 0xCF, (byte) 0xDC, (byte) 0x4C, (byte) 0xEF
        };
        byte[] crt2b = new byte[]{
                (byte) 0x8D, (byte) 0xF6, (byte) 0xA7, (byte) 0xA3,
                (byte) 0xFE, (byte) 0xAE, (byte) 0x6D, (byte) 0x34
        };
        byte[] crt3b = new byte[]{
                (byte) 0x70, (byte) 0x0F, (byte) 0xE5, (byte) 0xCF,
                (byte) 0x67, (byte) 0x46, (byte) 0xA9, (byte) 0xFB
        };
        byte[][] keysBytes = {
                {
                        (byte) 0x00, (byte) 0x22, (byte) 0x44, (byte) 0x66,
                        (byte) 0x88, (byte) 0xAA, (byte) 0xCC, (byte) 0xEE
                },
                {
                        (byte) 0x11, (byte) 0x33, (byte) 0x55, (byte) 0x77,
                        (byte) 0x99, (byte) 0xBB, (byte) 0xDD, (byte) 0xFF
                },
                {
                        (byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67,
                        (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF
                }
        };
        /*
        DataEncryptionStandard d = new DataEncryptionStandard();
        long a = d.des(keyB, msgB, false);
        System.out.println("Encrypt: " + Long.toHexString(a));
        long x = d.des(keyB, crt1b, true);
        System.out.println("Decrypt: " + Long.toHexString(x));

        long c = d.des(keyB, msg1b, false);
        System.out.println("Encrypt: " + Long.toHexString(c));
        long z = d.des(keyB, crt2b, true);
        System.out.println("Decrypt: " + Long.toHexString(z));

        long b = d.desx(keysBytes, msg1b, false);
        System.out.println("Encrypt: " + Long.toHexString(b));
        long y = d.desx(keysBytes, crt3b, true);
        System.out.println("Decrypt: " + Long.toHexString(y));
        */

        /*
        System.out.println("========");

        DataEncryptionStandard d0=new DataEncryptionStandard(keyB, msg1b);
        String d0e=d0.desEncryption();

        BigInteger d0e_str=new BigInteger(d0e,16);
        byte[] crt_d0=d0e_str.toByteArray();

        DataEncryptionStandard d12=new DataEncryptionStandard(keyB, crt_d0);
        String d12e=d12.desDecryption();


        System.out.println("========");
        */


        DataEncryptionStandard _d1 =new DataEncryptionStandard("0123456789ABCDEF","Alamakotaą",false);
        String _d1e = _d1.desEncryption();
        System.out.println("Len:\t"+_d1e.length());
        //String _d1e="9b81be597bb5a248";

        DataEncryptionStandard d1e=new DataEncryptionStandard("0123456789ABCDEF", _d1e,true);
        d1e.desDecryption();

        /*
        String[] keys={
                "0022446688AACCEE",
                "1133557799BBDDFF",
                "0123456789ABCDEF"
        };
        DataEncryptionStandard d2=new DataEncryptionStandard(keys,"Alamakot",false);
        String d2e=d2.desXEncryption();
        DataEncryptionStandard d3=new DataEncryptionStandard(keys,d2e,true);
        d3.desXDecryption();
        System.out.println("========");
        */

        //DataEncryptionStandard.createBlocks("aąasdfgA");

    }
}