package org.example.home6.phoneBook;

import java.io.IOException;

public class Main {
    public static void main(String[] args)  throws IOException{
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.loadFile("phones.txt");
        System.out.println("phoneBook = " + phoneBook.getPhoneBook());
        phoneBook.addPhone("Ivanov", 3535535);
//        phoneBook.delContact("Petrov");
//        phoneBook.delPhone("Sidv", 3553535);
        System.out.println("phoneBook.find(\"Sidorov\") = " + phoneBook.find("Sidorov"));
        System.out.println("phoneBook = " + phoneBook.getPhoneBook());
        phoneBook.saveFile("phones.txt");
    }

}
