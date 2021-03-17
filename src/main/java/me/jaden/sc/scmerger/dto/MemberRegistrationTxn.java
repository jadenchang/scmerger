package me.jaden.sc.scmerger.dto;

import lombok.Data;

import java.util.Set;

@Data
public class MemberRegistrationTxn {

    private String transactionIdentifier;
    private Set<Member> members;
    private Employer employer;
    private Fund fund;

    public MemberRegistrationTxn(String transactionIdentifier){
        this.transactionIdentifier = transactionIdentifier;
    }
}
