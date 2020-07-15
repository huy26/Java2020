package edu.pdx.cs410J.huy26;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class TextDumperTest {
    @Test
    public void testDumperFunction() throws IOException {
        TextDumper dumper = new TextDumper(new File("test.txt"));
        ArrayList<PhoneCall> phoneCalls=new ArrayList<PhoneCall>();
        PhoneCall call=new PhoneCall("123-456-5468","123-456-7542","11/05/1999 5:30","11/05/1999 5:37");
        PhoneBill bill = new PhoneBill("customer",new ArrayList<PhoneCall>());
        phoneCalls.add(call);
        dumper.dump(bill);
        Scanner scanner = new Scanner(new File("test.txt"));
        String customerName=scanner.nextLine();
        assertEquals(customerName.equals(bill.getCustomer()),true);
    }
}
