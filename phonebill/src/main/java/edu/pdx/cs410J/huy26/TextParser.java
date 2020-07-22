package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * This class represents a <code>TextParser</code>
 */
public class TextParser implements PhoneBillParser<PhoneBill> {
    private final File fileText;
    private final File prettyFile;
    private final String [] args;
    private final Integer firstPosition;
    boolean textFileAvailable;
    boolean prettyFileAvailable;
    /**
     * Creates a new <code>TextParser</code>
     * @param fileText Path directory to the text file.
     * @param prettyFile Path directory to the pretty text file.
     * @param args Arguments from command line
     * @param firstPosition The first position of information, not option in command line.
     */
    public TextParser(File fileText, File prettyFile, String[] args, Integer firstPosition, boolean textFileAvailable, boolean prettyFileAvailable) {
        this.fileText = fileText;
        this.prettyFile = prettyFile;
        this.args = args;
        this.firstPosition = firstPosition;
        this.textFileAvailable=textFileAvailable;
        this.prettyFileAvailable=prettyFileAvailable;
    }

    /**
     * Read Text File if the file exist. If not, create a new file with new Phone Bill created from provided arguments.
     */
    @Override
    public PhoneBill parse() throws ParserException {
        PhoneBill bill= new PhoneBill(args[firstPosition],new TreeSet<PhoneCall>());
        if(textFileAvailable) {
            if (fileText.exists()) {
                try {
                    bill = readTextFile();
                } catch (IOException e) {
                    throw new ParserException("Cannot parsing the file", e);
                    //e.printStackTrace();
                }
            }
        }
            PhoneCall call = new PhoneCall(args[firstPosition+1],args[firstPosition+2],args[firstPosition+3] + " "+ args[firstPosition+4]+" "+args[firstPosition+5],args[firstPosition+6]+" "+args[firstPosition+7]+" "+args[firstPosition+8]);
            bill.addPhoneCall(call);
            if (textFileAvailable){
            TextDumper textDumper = new TextDumper(fileText);
            try {
                textDumper.dump(bill);
            } catch (IOException e) {
                throw new ParserException("Cannot write the file", e);
            }
        }
        if (prettyFileAvailable){
            PrettyPrinter prettyPrinter=new PrettyPrinter(prettyFile);
            try {
                prettyPrinter.dump(bill);
            } catch (IOException e) {
                throw new ParserException("Cannot write the file", e);
            }
        }
        return bill;
    }


    /**
     * Read Text File and add Phone Call to Phone Bill if match customer name. If not, exit the program.
     */
    public PhoneBill readTextFile() throws IOException {
        try {
            PhoneBill bill = new PhoneBill("",new TreeSet<PhoneCall>());
            Scanner scanner = new Scanner(fileText);
            String customerName=scanner.nextLine();
            if (customerName.equals(args[firstPosition])) {
                bill=new PhoneBill(customerName,new TreeSet<PhoneCall>());
//                while (scanner.hasNext()) {
//                    String caller = scanner.next();
//                    String callee = scanner.next();
//                    String start = scanner.next() + " " + scanner.next();
//                    String end = scanner.next() + " " + scanner.next();
//                    PhoneCall call = new PhoneCall(caller, callee, start, end);
//                    bill.addPhoneCall(call);
//                }
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    if(line.isEmpty())
                        continue;
                    String[] tokens = line.split("\\s");
                    if (tokens.length<8){
                        System.err.println("Missing argument in text file");
                        System.exit(1);
                    } else if (tokens.length>8){
                        System.err.println("Redundant argument in text file");
                        System.exit(1);
                    }else {
                        String caller = tokens[0];
                        String callee = tokens[1];
                        String start = tokens[2]+ " " + tokens[3] +" "+tokens[4];
                        String end = tokens[5] + " " + tokens[6]+" "+tokens[7];
                        if(!caller.matches("\\d{3}-\\d{3}-\\d{4}$")||!callee.matches("\\d{3}-\\d{3}-\\d{4}$")){
                            System.err.println("Phone number from file is invalid");
                            System.exit(1);
                        }
                        if(!start.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2} (AM|PM|am|pm)$")|| !end.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2} (AM|PM|am|pm)$")){
                            System.err.println("Date or Time is invalid format");
                            System.exit(1);
                        }
                        PhoneCall call = new PhoneCall(caller, callee, start, end);
                        bill.addPhoneCall(call);
                    }
                }
//                PhoneCall call = new PhoneCall(args[firstPosition+1],args[firstPosition+2],args[firstPosition+3] + " "+ args[firstPosition+4]+ " "+ args[firstPosition+5],args[firstPosition+6]+" "+args[firstPosition+7]+ " "+ args[firstPosition+8]);
//                bill.addPhoneCall(call);
//                TextDumper textDumper = new TextDumper(file);
//                textDumper.dump(bill);
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
