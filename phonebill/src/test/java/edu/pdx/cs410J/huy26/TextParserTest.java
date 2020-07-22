package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TextParserTest {
    @Test
    public void testParserFunction() throws IOException, ParserException {
        File file=new File("test.txt");
        File file1=new File("test1.txt");
        PhoneBill bill=new PhoneBill("",new TreeSet<PhoneCall>());
        String args[]= new String[]{"-textFile","test","customer","123-456-7897","123-456-7898","11/5/2020","5:30","pm","11/5/2020","5:37","pm"};
        Integer firstPosition = 2;
        TextParser parser = new TextParser(file,file1,args,firstPosition,true,false);
        bill = parser.parse();
        Scanner scanner = new Scanner(file);
        String customerName=scanner.nextLine();
        assertEquals(customerName.equals(bill.getCustomer()),true);
    }
    @Ignore
    @Test
    public void returnPhoneBill() throws IOException, ParserException {
        File file=new File("test.txt");
        File file1=new File("test1.txt");
        PhoneBill bill=new PhoneBill("",new TreeSet<PhoneCall>());
        String args[]= new String[]{"-textFile","test","customer","123-456-7897","123-456-7898","11/5/2020","5:30","pm","11/5/2020","5:37","pm"};
        Integer firstPosition = 2;
       TextParser parser = new TextParser(file,file1,args,firstPosition,true,false);
        Integer count = parser.parse().getPhoneCalls().size();
        assertEquals(parser.parse().getPhoneCalls().size(),count+1);
    }
}