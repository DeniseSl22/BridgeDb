package org.bridgedb.virtuoso;

import org.bridgedb.IDMapperException;
import org.bridgedb.sql.SQLAccess;
import org.bridgedb.sql.TestSqlFactory;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 * 
 * @author Christian
 */
public class IDMapperTest extends org.bridgedb.IDMapperTest {
    
    @BeforeClass
    public static void setupIDMapper() throws IDMapperException{
        connectionOk = false;
        SQLAccess sqlAccess = TestSqlFactory.createTestVirtuosoAccess();
        connectionOk = true;
        VirtuosoMapper urlMapperVirtuoso = new VirtuosoMapper(sqlAccess);
        idMapper = urlMapperVirtuoso;
    }
            
}