package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;

public class PhoneCall extends AbstractPhoneCall {
  private String callername;
  private String calleename;
  private String callernumber;
  private String calleenumber;
  public  PhoneCall(String callername, String calleename, String callernumber, String calleenumber){
    this.calleenumber=calleenumber;
    this.callernumber=callernumber;
    this.calleename=calleename;
    this.callername=callername;
  }

  @Override
  public String getCaller() {
    return this.getCaller().toString();
  }

  @Override
  public String getCallee() {
    return this.getCaller().toString();
  }

  @Override
  public String getStartTimeString() {
    return this.getStartTime().toString();
  }

  @Override
  public String getEndTimeString() {
    return this.getEndTime().toString();
  }
}
