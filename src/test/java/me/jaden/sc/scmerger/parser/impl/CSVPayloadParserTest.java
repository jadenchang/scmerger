package me.jaden.sc.scmerger.parser.impl;

import com.opencsv.CSVReader;
import me.jaden.sc.scmerger.dto.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CSVPayloadParserTest {

    Path path;

    @BeforeAll
    public void setUp()throws Exception{
        path = Paths.get(this.getClass().getResource("/AB-5014.csv").toURI());
    }

    @Test
    public void testCSVPayloadParser(){
        List<Member> members = new ArrayList<>();
        try {
            Reader reader = Files.newBufferedReader(path);
            CSVReader csv = new CSVReader(reader);
            String[] record;
            while((record = csv.readNext())!=null){
                Member m = Member.valueOf(record);
                members.add(m);
            }

            members.stream().forEach(System.out::println);
        } catch(Exception e){
            e.printStackTrace();;
        }

        Assertions.assertNotNull(members);
        Assertions.assertEquals(members.size(), 2);

        Member m = members.get(0);
        Assertions.assertEquals(m.getFirstName(), "chia-hao");
        Assertions.assertEquals(m.getLastName(), "chang");
        Assertions.assertEquals(m.getOtherNames(), "jaden");
        Assertions.assertEquals(m.getGender().name(), "MALE");
        Assertions.assertEquals(m.getEmail(), "jaden.chang.tw@gmail.com");
        Assertions.assertEquals(m.getContactNumber(), "0481107041");

    }
}