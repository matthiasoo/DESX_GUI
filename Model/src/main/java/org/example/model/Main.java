package org.example.model;

public class Main {
    public static void main(String[] args) {
        DataEncryptionStandard _d1 =new DataEncryptionStandard("f123456789ABCDEF","Alamakotaą",false);
        String _d1e = _d1.desEncryption();

        DataEncryptionStandard d1e=new DataEncryptionStandard("f123456789ABCDEF", _d1e,true);
        d1e.desDecryption();

        System.out.println("========");

        String[] keys={
                "0022446688AACCEE",
                "1133557799BBDDFF",
                "0123456789ABCDEF"
        };
        DataEncryptionStandard d2=new DataEncryptionStandard(keys,"Alamakotaą",false);
        String d2e=d2.desXEncryption();
        DataEncryptionStandard d3=new DataEncryptionStandard(keys,d2e,true);
        d3.desXDecryption();
        System.out.println("========");
    }
}