package me.jaden.sc.scmerger.parser.impl;

import me.jaden.sc.scmerger.dto.MemberRegistrationTxn;
import me.jaden.sc.scmerger.parser.impl.handler.PayloadHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.net.URL;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class XMLPayloadParserTest {

    File xmlFile;

    @BeforeAll
    public void setUp(){
        URL path = this.getClass().getResource("/AB-5014.xml");
        xmlFile = new File(path.getPath());
    }

    @Test
    public void testXMLPayloadParser(){
        Set<MemberRegistrationTxn> txns = null;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            PayloadHandler handler = new PayloadHandler();
            saxParser.parse(xmlFile, handler);
            txns = handler.getPayload();

        }catch(Exception e){
            e.printStackTrace();
        }

        Assertions.assertNotNull(txns);
        Assertions.assertEquals(txns.size(), 2);

    }
}