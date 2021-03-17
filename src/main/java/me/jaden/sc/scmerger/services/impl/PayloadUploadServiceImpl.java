package me.jaden.sc.scmerger.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jaden.sc.scmerger.dto.Member;
import me.jaden.sc.scmerger.dto.Transaction;
import me.jaden.sc.scmerger.dto.MemberRegistrationTxn;
import me.jaden.sc.scmerger.parser.impl.CSVPayloadParser;
import me.jaden.sc.scmerger.parser.impl.XMLPayloadParser;
import me.jaden.sc.scmerger.services.PayloadUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PayloadUploadServiceImpl implements PayloadUploadService {
    Logger logger = LoggerFactory.getLogger(PayloadUploadServiceImpl.class);

    @Value("${repository.path}")
    private String repositoryPath;              //the repository path can be configured in application.properties.

    @Override
    public void mergeData(File csvFile, File xmlFile) {
        try {
            //1.parse data
            XMLPayloadParser xmlParser = new XMLPayloadParser();
            Set<MemberRegistrationTxn> transactions  = xmlParser.parse(xmlFile);

            CSVPayloadParser csvParser = new CSVPayloadParser();
            Set<Member> memberProfiles = csvParser.parse(csvFile);

            //2. prepare repository
            checkFolder(transactions);

            //3.merge
            Set<Transaction> payrolls = mergeMemberFieldsIntoTransaction(transactions, memberProfiles);

            //4.write-in data
            writeToJson(payrolls);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<Transaction> mergeMemberFieldsIntoTransaction(Set<MemberRegistrationTxn> transactions, Set<Member> memberProfiles){
        Set<Transaction> returnSet = new HashSet<>();

        transactions.stream().forEach(txn->{
            Set<Member> txnMmebers = txn.getMembers();
            if(txnMmebers==null) return;            //no members defined.
            txnMmebers.stream().forEach( txnMember -> {
                Transaction p = new Transaction(txn.getTransactionIdentifier());
                p.importFrom(txn.getFund());
                p.importFrom(txn.getEmployer());

                Member mym = memberProfiles.stream().filter(profile -> profile.getMemberNumber().equals(txnMember.getMemberNumber())).collect(Collectors.toList()).get(0);
                p.importFrom(mym);
                returnSet.add(p);
            });
        });

        return returnSet;
    }

    private void writeToJson(Set<Transaction> transactions){
        transactions.stream().forEach(t->{
            Path wPath = Path.of(repositoryPath, t.getFundIdentifier().toString(), t.getEmployerABN() +".json");

            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map = mapper.convertValue(t, Map.class);
                mapper.writeValue(wPath.toFile(), map);

            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }


    private void checkFolder(Set<MemberRegistrationTxn> transactions)throws IOException {
        //prepare the configured directory folder.
        Path reposPath = Path.of(repositoryPath);
        mkdirs(reposPath.toFile());
        if(!reposPath.toFile().isDirectory() || !reposPath.toFile().canWrite())
            throw new IOException("Internal Repository can not be accessed");

        logger.info("System initialize the configure folder:{}", reposPath);

        //Prepare folder for each fund
        transactions.stream().forEach(t-> {
            String fundName = t.getFund().getFundIdentifier().toString();
            Path fundPath = Path.of(reposPath.toString(), fundName);
            mkdirs(fundPath.toFile());

            logger.info("System initialize the fund folder:{}", fundPath);
        });
    }

    private void mkdirs(File f){
        if(!f.exists()) f.mkdirs();
    }

    public String getRepositoryPath(){ return this.repositoryPath; }
}


