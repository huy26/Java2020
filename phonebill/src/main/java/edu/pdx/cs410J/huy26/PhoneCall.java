package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
 private final String caller;
 private final String callee;
 private final String callerNumber;
 private final String calleeNumber;
 private final String start;
 private final String end;

 public PhoneCall(String caller, String callee, String callerNumber, String calleeNumber, String start, String end) {
  this.caller = caller;
  this.callee = callee;
  this.callerNumber = callerNumber;
  this.calleeNumber = calleeNumber;
  this.start = start;
  this.end= end;
 }


 @Override
 public String getCaller() {
  return this.caller;
 }

 @Override
 public String getCallee() {
  return this.callee;
 }

 @Override
 public String getStartTimeString() {
  throw new UnsupportedOperationException("This method is not implemented yet");
 }

 @Override
 public String getEndTimeString() {
  return this.end;
 }

 public String getCallerNumber(){
  return this.callerNumber;
 }
 public String getCalleeNumber(){
  return this.calleeNumber;
 }

}

