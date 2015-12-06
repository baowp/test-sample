package com.iteye.baowp.jaxb;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by baowp on 2015/7/12.
 */
public class JaxbTest {

    @Test
    public void testByFile() throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(APIResponce.class);

        File xml = new File("src/test/resources/com/iteye/baowp/jaxb/input.xml");
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        APIResponce api = (APIResponce) unmarshaller.unmarshal(xml);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(api, System.out);
        assertNotNull(api.getUserid());
    }

    @Test
    public void testByInputStream() throws JAXBException {
        InputStream ins = getClass().getResourceAsStream("input.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(APIResponce.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        APIResponce api = (APIResponce) jaxbUnmarshaller.unmarshal(ins);
        assertNotNull(api.getUserid());
        assertEquals("Fast Furious", api.getFavour().getMovie());
    }

    @Test
    public void testByString() throws JAXBException, IOException {
        InputStream ins = getClass().getResourceAsStream("input.xml");
        String exampleString = IOUtils.toString(ins);
        InputStream stream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
        JAXBContext jaxbContext = JAXBContext.newInstance(APIResponce.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        APIResponce api = (APIResponce) jaxbUnmarshaller.unmarshal(stream);
        assertNotNull(api.getUserid());
        assertEquals("Fast Furious", api.getFavour().getMovie());
    }

    @Test
    public void testInputStreamClose() throws Exception {
        InputStream is = new FileInputStream("src/test/resources/com/iteye/baowp/jaxb/input.xml") {
            @Override
            public void close() throws IOException {
                System.out.println("close");
                super.close();
            }
        };
        System.out.println(is.available());
        String content = IOUtils.toString(is);
        System.out.println(is.available());
    }

    @Test
    public void testInputStreamGC() throws Exception {
        testInputStreamClose();
        System.gc();
    }
}
