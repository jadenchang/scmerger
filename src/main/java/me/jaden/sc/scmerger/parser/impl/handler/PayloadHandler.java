package me.jaden.sc.scmerger.parser.impl.handler;

import me.jaden.sc.scmerger.dto.Employer;
import me.jaden.sc.scmerger.dto.Fund;
import me.jaden.sc.scmerger.dto.Member;
import me.jaden.sc.scmerger.dto.MemberRegistrationTxn;
import me.jaden.sc.scmerger.utils.Converter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashSet;
import java.util.Set;

public class PayloadHandler extends DefaultHandler {

    private Set<MemberRegistrationTxn> payload;
    private MemberRegistrationTxn txn;
    private Fund fund;
    private Employer employer;
    private Set<Member> members;
    private Member member;

    private String val;
    private Converter c = new Converter();

    public Set<MemberRegistrationTxn> getPayload(){
        return this.payload;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        val = new String(ch, start, length);
    }

    @Override
    public void startDocument() throws SAXException {}

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException{
        switch(qName){
            case "member-registration-transaction":
                payload = new HashSet<>();
                break;
            case "transaction":
                String txnId = attr.getValue("transactionIdentifier");
                txn = new MemberRegistrationTxn(txnId);
            case "fund":
                fund = new Fund();
                break;
            case "employer":
                employer = new Employer();
                break;
            case "members":
                members = new HashSet<>();
                break;
            case"member":
                member = Member.builder().build();
                break;
            default:
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch(qName){
            case "transactionIdentifier":
                txn.setTransactionIdentifier(val);
                break;
            case "fund-id":
                fund.setFundIdentifier(c.strToLong(val));
                break;
            case "fund-employer-identifier":
                fund.setFundEmployerIdentifier(c.strToLong(val));
                break;
            case "fund":
                txn.setFund(fund);
                break;
            case "employer-name":
                employer.setEmployerName(val);
                break;
            case "employer-abn":
                employer.setEmployerABN(c.strToLong(val));
                break;
            case "employer":
                txn.setEmployer(employer);
                break;
            case "member":
                members.add(member);
                break;
            case "members":
                txn.setMembers(members);
                break;
            case "member-tfn":
                member.setTfn(c.strToLong(val));
                break;
            case "member-number":
                member.setMemberNumber(c.strToLong(val));
                break;
            case "member-payrollnumber":
                member.setPayrollNumber(c.strToLong(val));
                break;
            case "transaction":
                payload.add(txn);
                break;
        }
    }
}
