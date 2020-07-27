package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class PhoneBillTextParser implements PhoneBillParser<PhoneBill> {
    private final StringReader stringReader;

    public PhoneBillTextParser(StringReader stringReader) {
        this.stringReader=stringReader;
    }

    @Override
    public PhoneBill parse() throws ParserException {
        BufferedReader br = new BufferedReader(this.stringReader);
        String customer = null;
        try {
            customer = br.readLine();

            PhoneBill bill = new PhoneBill(customer);
            while (br.ready()) {
                String caller = br.readLine();
                if(caller==null){
                    break;
                }
                bill.addPhoneCall(new PhoneCall(caller));
            }
            return bill;
        } catch (IOException e) {
            throw new ParserException("While parsing",e);
        }


    }
}
