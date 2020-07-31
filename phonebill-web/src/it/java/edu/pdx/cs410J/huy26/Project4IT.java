package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.huy26.PhoneBillRestClient.PhoneBillRestException;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project4IT extends InvokeMainTestCase {

    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");
    private PhoneBillRestClient newPhoneBillRestClient() {
        int port = Integer.parseInt(PORT);
        return new PhoneBillRestClient(HOSTNAME, port);
    }
    @Test
    public void test0RemoveAllMappings() throws IOException {
      PhoneBillRestClient client = new PhoneBillRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllPhoneBills();
    }

    @Test
    public void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    public void test3UnknownPhoneBillIssueUnknownPhoneBillError() throws Throwable {
        String[] args = {"-host","localhost","-port","8080","Eddie"};
        MainMethodResult result = invokeMain(Project4.class, args);
        assertThat(result.getTextWrittenToStandardError(),containsString("No phone bill for customer " + "Eddie"));
        assertThat(result.getExitCode(),equalTo(1));
    }

    @Test
    public void test4AddPhoneCall() {
        String[] args = {"-host","localhost","-port","8080","Dave","123-456-7897","123-456-7895","12/12/2020", "5:27","am","12/12/2020","5:30","am"};
        MainMethodResult result = invokeMain( Project4.class, args );
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, equalTo(""));
        assertThat(result.getExitCode(),equalTo(0));
    }

    @Ignore
    @Test
    public void test5PhoneBillIsPrettyPrinted(){
        String[] args = {"-host","localhost","-port","8080","Dave"};
        MainMethodResult result = invokeMain( Project4.class, args );
        assertThat(result.getExitCode(),equalTo(0));
        String pretty = result.getTextWrittenToStandardOut();
        assertThat(pretty, containsString("Dave"));
        assertThat(pretty, containsString("Phone call from "));
    }

    @Test
    public void test6MissingHostName(){
        String[] args = {"-print","Dave","123-456-7897","123-456-7895","12/12/2020", "5:27","am","12/12/2020","5:30","am"};
        MainMethodResult result = invokeMain( Project4.class, args);
        assertThat(result.getExitCode(),equalTo(1));
        //String pretty = result.getTextWrittenToStandardOut();
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing command line arguments"));

    }
    @Test
    public void test7MissingPort(){
        String[] args = {"-print","-host","localhost","Dave","123-456-7897","123-456-7895","12/12/2020", "5:27","am","12/12/2020","5:30","am"};
        MainMethodResult result = invokeMain( Project4.class, args);
        assertThat(result.getExitCode(),equalTo(1));
        //String pretty = result.getTextWrittenToStandardOut();
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing port"));
    }
    @Test
    public void test8RedundantArgument(){
        String[] args = {"-port","8080","-host","localhost","Dave","123-456-7897","123-456-7895","12/12/2020", "5:27","am","12/12/2020","5:30","am","Asda"};
        MainMethodResult result = invokeMain( Project4.class, args);
        assertThat(result.getExitCode(),equalTo(1));
        //String pretty = result.getTextWrittenToStandardOut();
        assertThat(result.getTextWrittenToStandardError(),containsString("Extraneous command line argument: "+args[args.length-1]));
    }

    @Test
    public void test9SearchOption() throws IOException {
        PhoneBillRestClient client = newPhoneBillRestClient();
        String customer = "Dave";
        String caller = "123-456-7890";
        String callee = "123-456-4515";
        String start = "5/11/2020 5:30 pm";
        String end = "5/11/2020 5:37 pm";
        client.addPhoneCall(customer, caller,callee,start,end);
        String[] args = {"-port","8080","-host","localhost","-search","Dave","5/11/2020", "5:27","am","12/12/2020","5:37","am"};
        MainMethodResult result = invokeMain( Project4.class, args);
        assertThat(result.getExitCode(),equalTo(0));
        //String pretty = result.getTextWrittenToStandardOut();
        assertThat(result.getTextWrittenToStandardOut(),containsString("5/11/20"));
    }

    @Test
    public void test10READMEOption(){
        String[] args = {"-port","8080","-host","localhost","-search","-README","Dave","5/11/2020", "5:27","am","12/12/2020","5:37","am"};
        MainMethodResult result = invokeMain( Project4.class, args);
        assertThat(result.getExitCode(),equalTo(0));
        //String pretty = result.getTextWrittenToStandardOut();
        assertThat(result.getTextWrittenToStandardOut(),containsString("This is a README file!"));

    }
    @Test
    public void test11searchNoDateArgument(){
        String[] args = {"-port","8080","-host","localhost","-search","Dave"};
        MainMethodResult result = invokeMain( Project4.class, args);
        assertThat(result.getExitCode(),equalTo(1));
        //String pretty = result.getTextWrittenToStandardOut();
        assertThat(result.getTextWrittenToStandardError(),containsString("Missing command line argument"));

    }
}