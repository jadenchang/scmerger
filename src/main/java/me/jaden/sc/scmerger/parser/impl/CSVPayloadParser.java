package me.jaden.sc.scmerger.parser.impl;

import com.opencsv.CSVReader;
import me.jaden.sc.scmerger.dto.Member;
import me.jaden.sc.scmerger.exception.DataConvertingException;
import me.jaden.sc.scmerger.parser.DataParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public class CSVPayloadParser implements DataParser{

    Logger logger = LoggerFactory.getLogger(CSVPayloadParser.class);

    public Set<Member> parse(File f) throws DataConvertingException {
        Set<Member> members = new HashSet<>();
        try (Reader reader = Files.newBufferedReader(f.toPath());CSVReader csv = new CSVReader(reader);){
            String[] record;

            while((record = csv.readNext())!=null){
                members.add(Member.valueOf(record));
            }
        } catch(Exception e){
            logger.error("Data in {} has converting error:{}", f.getName() );
            throw new DataConvertingException("data convert fail:" + e.getMessage());
        }

        return members;
    }

}
