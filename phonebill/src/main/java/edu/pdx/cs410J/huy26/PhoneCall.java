package edu.pdx.cs410J.huy26;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Date;

public class PhoneCall extends AbstractPhoneCall {


 private final String callee;
 public PhoneCall(String callee){
  this.callee=callee;
 }

 @Override
 public String getCaller() {
  return null;
 }

 @Override
 public String getCallee() {
  return this.callee;
 }

 @Override
 public String getStartTimeString() {
  return null;
 }

 @Override
 public String getEndTimeString() {
  return null;
 }
}
