package edu.pdx.cs410J.huy26;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class PrettyPrinterTest {
    @Test
    public void testPrettyFunction() throws IOException {
        PrettyPrinter prettyPrinter = new PrettyPrinter(new File("pretty.txt"));
        ArrayList<PhoneCall> phoneCalls=new ArrayList<PhoneCall>();
        PhoneCall call=new PhoneCall("123-456-5468","123-456-7542","11/05/1999 5:30 pm","11/05/1999 5:37 pm");
        PhoneBill bill = new PhoneBill("customer",new TreeSet<PhoneCall>());
        phoneCalls.add(call);
        prettyPrinter.dump(bill);
        Scanner scanner = new Scanner(new File("pretty.txt"));
        String customerName=scanner.nextLine();
        assertEquals(customerName.equals(bill.getCustomer()),true);
    }
}