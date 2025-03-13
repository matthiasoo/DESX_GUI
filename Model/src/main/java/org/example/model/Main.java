package org.example.model;

public class Main {
    public static void main(String[] args) {
        DataEncryptionStandard d1 = new DataEncryptionStandard("0123456789ABCDEF", "Alamakotaą", false);
        String d1e = d1.desEncryption();

        DataEncryptionStandard de = new DataEncryptionStandard("0123456789ABCDEF", d1e, true);
        de.desDecryption();

        System.out.println("========");

        String[] keys = {
                "0022446688AACCEE",
                "0133557799BBDDFF",
                "0123456789ABCDEF"
        };
        DataEncryptionStandard d2 = new DataEncryptionStandard(keys, "Alamakotaą", false);
        String d2e = d2.desXEncryption();
        DataEncryptionStandard d3 = new DataEncryptionStandard(keys, d2e, true);
        d3.desXDecryption();
        System.out.println("========");
    }
}