package com.iteye.baowp.domain;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 11/28/13
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrepareDataGenerator {

    private static Logger logger = LoggerFactory.getLogger(DatabaseExportSample.class);

    public static void main(String[] args) throws Exception {
        logger.info("user.dir: " + System.getProperty("user.dir"));

        Map<String, String> export = new HashMap<String, String>();
        export.put("book", "SELECT * FROM book where id=2");

        String path = System.getProperty("user.dir") + "/book.xml";
        generate(export, path);
    }

    private static void generate(Map<String, String> export, String createFile) throws Exception {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream in = loader.getResourceAsStream("conf.properties");
        Properties properties = new Properties();
        properties.load(in);
        in.close();

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        String driverClass = properties.getProperty("db.driverClass");
        // database connection
        Class.forName(driverClass);
        Connection jdbcConnection = DriverManager.getConnection(url, user, password);
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        for (Map.Entry<String, String> entry : export.entrySet())
            partialDataSet.addTable(entry.getKey(), entry.getValue());

        FlatXmlDataSet.write(partialDataSet, new FileOutputStream(createFile));
    }
}
