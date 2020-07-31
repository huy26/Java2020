package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * This class represents a <code>PhoneBillTextParser</code>
 */
public class PhoneBillTextParser implements PhoneBillParser<PhoneBill> {
    private final StringReader stringReader;

    /**
     * Creates a new <code>PhoneBillTextParser</code>
     * @param stringReader
     */
    public PhoneBillTextParser(StringReader stringReader) {
        this.stringReader=stringReader;
    }

    /**
     * The function parse the phone call and check if the arguments are valid.
     * @return
     * @throws ParserException
     */
    @Override
    public PhoneBill parse() throws ParserException {
        BufferedReader br = new BufferedReader(this.stringReader);
        String customer = null;
        try {
            customer = br.readLine();

            PhoneBill bill = new PhoneBill(customer);
            String line;
            while ((line = br.readLine())!=null) {
                String[] tokens = line.split("\\s");
                if (tokens.length<8){
                    System.err.println("Missing argument from the client");
                    System.exit(1);
                } else if (tokens.length>8){
                    System.err.println("Redundant argument from the client");
                    System.exit(1);
                }else {
                    String caller = tokens[0];
                    String callee = tokens[1];
                    String start = tokens[2]+ " " + tokens[3] +" "+tokens[4];
                    String end = tokens[5] + " " + tokens[6]+" "+tokens[7];
                    if(!caller.matches("\\d{3}-\\d{3}-\\d{4}$")||!callee.matches("\\d{3}-\\d{3}-\\d{4}$")){
                        System.err.println("Phone number from server is invalid");
                        System.exit(1);
                    }
                    if(!start.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2} (AM|PM|am|pm)$")|| !end.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2} (AM|PM|am|pm)$")){
                        System.err.println("Date or Time is invalid format");
                        System.exit(1);
                    }
                    bill.addPhoneCall(new PhoneCall(caller, callee, start, end));
                }
                }
                return bill;
        } catch (IOException e) {
            throw new ParserException("While parsing",e);
        }


    }
}
