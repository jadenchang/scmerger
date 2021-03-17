package me.jaden.sc.scmerger.parser.impl;

import me.jaden.sc.scmerger.dto.MemberRegistrationTxn;
import me.jaden.sc.scmerger.exception.DataConvertingException;
import me.jaden.sc.scmerger.parser.DataParser;
import me.jaden.sc.scmerger.parser.impl.handler.PayloadHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Set;

public class XMLPayloadParser implements DataParser {

    Logger logger = LoggerFactory.getLogger(XMLPayloadParser.class);

    public Set<MemberRegistrationTxn> parse(File f) throws DataConvertingException {
        Set<MemberRegistrationTxn> returnVal = null;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            PayloadHandler handler = new PayloadHandler();
            saxParser.parse(f, handler);
            returnVal = handler.getPayload();

        }catch(Exception e){
            logger.error("xml parse exception in file{}", f.getName());
            throw new DataConvertingException("XML parse error");
        }

        return returnVal;
    }


}
