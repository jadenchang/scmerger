package me.jaden.sc.scmerger.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class Transaction implements Human, Workable, Serializable {

    private String transactionIdentifier;
    private String employerName;
    private Long employerABN;
    private Long fundIdentifier;
    private Long fundEmployerIdentifier;
    private String memberFirstName;
    private String memberLastName;
    private String memberOtherNames;
    private LocalDate memberDateOfBirther;
    private Gender memberGender;
    private String memberAddress;
    private String memberEmail;
    private String memberContactNumber;
    private Long memberNumber;
    private Long memberTfn;
    private Long memberPayrollNumber;
    private EmploymentStatus memberEmploymentStatus;
    private LocalDate memberFundRegistrationDate;

    public Transaction(String transactionIdentifier){
        this.transactionIdentifier = transactionIdentifier;
    }

    public void importFrom(Employer e){
        this.setEmployerName(e.getEmployerName());
        this.setEmployerABN(e.getEmployerABN());
    }

    public void importFrom(Member m){
        this.setMemberFirstName(m.getFirstName());
        this.setMemberLastName(m.getLastName());
        this.setMemberOtherNames(m.getOtherNames());
        this.setMemberDateOfBirther(m.getDateOfBirther());
        this.setMemberGender(m.getGender());
        this.setMemberAddress(m.getAddress());
        this.setMemberEmail(m.getEmail());
        this.setMemberContactNumber(m.getContactNumber());
        this.setMemberNumber(m.getMemberNumber());
        this.setMemberTfn(m.getTfn());
        this.setMemberPayrollNumber(m.getPayrollNumber());
        this.setMemberEmploymentStatus(m.getEmploymentstatus());
        this.setMemberFundRegistrationDate(m.getMemberFundRegistrationDate());
    }

    public void importFrom(Fund f){
        this.setFundIdentifier(f.getFundIdentifier());
        this.setFundEmployerIdentifier(f.getFundEmployerIdentifier());
    }

}
