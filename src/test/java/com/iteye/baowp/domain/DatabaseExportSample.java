package com.iteye.baowp.domain;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created with IntelliJ IDEA.
 * User: baowp
 * Date: 11/27/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseExportSample {

    private static Logger logger = LoggerFactory.getLogger(DatabaseExportSample.class);

    private static String module = "spring-test-dbunit-sample";

    public static void main(String[] args) throws Exception {
        logger.info(System.getProperty("user.dir"));
        System.out.println("user.dir: " + System.getProperty("user.dir"));

        // database connection
        Class driverClass = Class.forName("com.mysql.jdbc.Driver");
        Connection jdbcConnection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/test?characterEncoding=utf8", "root", "root");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("book", "SELECT * FROM book ");
        //partialDataSet.addTable("BAR");

        String path = module + "/src/test/resources/com/github/springtestdbunit/mybatis/service/" + "book.xml";
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream(path));

    }
}
