package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TextParser implements PhoneBillParser<PhoneBill> {
    private final File file;
    private final String [] args;
    private final Integer firstPosition;

    public TextParser(File file, String[] args, Integer firstPosition) {
        this.file = file;
        this.args = args;
        this.firstPosition = firstPosition;
    }

    @Override
    public PhoneBill parse() throws ParserException {
        if(file.exists()){
            try {
                return readTextFile();
            } catch (IOException e) {
                throw new ParserException("Cannot parsing the file",e);
            }

        }
        else {
            PhoneBill bill= new PhoneBill(args[firstPosition],new ArrayList<PhoneCall>());
            PhoneCall call = new PhoneCall(args[firstPosition+1],args[firstPosition+2],args[firstPosition+3] + " "+ args[firstPosition+4],args[firstPosition+5]+" "+args[firstPosition+6]);
            bill.addPhoneCall(call);
            TextDumper textDumper = new TextDumper(file);
            try {
                textDumper.dump(bill);
                return bill;
            } catch (IOException e) {
                throw new ParserException("Cannot parsing the file",e);
            }
        }
    }



    public PhoneBill readTextFile() throws IOException {
        try {
            PhoneBill bill = new PhoneBill("",new ArrayList<>());
            Scanner scanner = new Scanner(file);
            String customerName=scanner.nextLine();
            if (customerName.equals(args[firstPosition])) {
                bill=new PhoneBill(customerName,new ArrayList<PhoneCall>());
                while (scanner.hasNext()) {
                    String caller = scanner.next();
                    String callee = scanner.next();
                    String start = scanner.next() + " " + scanner.next();
                    String end = scanner.next() + " " + scanner.next();
                    PhoneCall call = new PhoneCall(caller, callee, start, end);
                    bill.addPhoneCall(call);
                }
                PhoneCall call = new PhoneCall(args[firstPosition+1],args[firstPosition+2],args[firstPosition+3] + " "+ args[firstPosition+4],args[firstPosition+5]+" "+args[firstPosition+6]);
                bill.addPhoneCall(call);
                TextDumper textDumper = new TextDumper(file);
                textDumper.dump(bill);
            }
            else {
                System.err.println("Customer name does not match the file");
                System.exit(1);
            }
            scanner.close();
            return bill;
        }catch (IOException ex) {
            System.err.println(ex);
            throw ex;
        }
    }
}
