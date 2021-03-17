package me.jaden.sc.scmerger.dto;

import lombok.Builder;
import lombok.Data;
import me.jaden.sc.scmerger.exception.DataConvertingException;
import me.jaden.sc.scmerger.utils.Converter;

import java.time.LocalDate;

@Data
@Builder
public class Member implements Human, Workable{

    private String firstName;
    private String lastName;
    private String otherNames;
    private LocalDate dateOfBirther;
    private Gender gender;
    private String address;
    private String email;
    private String contactNumber;
    private Long memberNumber;
    private Long tfn;
    private Long payrollNumber;
    private EmploymentStatus employmentstatus;
    private LocalDate memberFundRegistrationDate;


    @Override
    public boolean equals(Object obj){
        if(obj instanceof Member) {
            Member other = (Member)obj;
            return this.memberNumber.equals(other.getMemberNumber());
        }
        return false;
    }

    public static Member valueOf(String[] values)throws DataConvertingException {
        return Member.builder()
                .firstName(values[0])
                .lastName(values[1])
                .otherNames(values[2])
                .dateOfBirther(new Converter().strToDate(values[3]))
                .gender(Gender.valueOf(values[4]))
                .address(values[5])
                .email(values[6])
                .contactNumber(values[7])
                .memberNumber(new Converter().strToLong(values[8]))
                .tfn(new Converter().strToLong(values[9]))
                .payrollNumber(new Converter().strToLong(values[10]))
                .employmentstatus(EmploymentStatus.valueOf(values[11]))
                .memberFundRegistrationDate(new Converter().strToDate(values[12]))
                .build();
    }
}
