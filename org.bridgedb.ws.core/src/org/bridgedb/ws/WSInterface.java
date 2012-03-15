/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bridgedb.ws;

import java.util.List;
import org.bridgedb.IDMapperException;
import org.bridgedb.ws.bean.CapabilitiesBean;
import org.bridgedb.ws.bean.DataSourceBean;
import org.bridgedb.ws.bean.FreeSearchSupportedBean;
import org.bridgedb.ws.bean.MappingSupportedBean;
import org.bridgedb.ws.bean.PropertyBean;
import org.bridgedb.ws.bean.XRefMapBean;
import org.bridgedb.ws.bean.XrefBean;
import org.bridgedb.ws.bean.XrefExistsBean;

/**
 *
 * @author Christian
 */
public interface WSInterface {

    List<XrefBean> freeSearch(String text, Integer limit) throws IDMapperException;

    DataSourceBean getDataSoucre(String code) throws IDMapperException;

    List<PropertyBean> getKeys();

    PropertyBean getProperty(String key);

    List<DataSourceBean> getSupportedSrcDataSources() throws IDMapperException;

    List<DataSourceBean> getSupportedTgtDataSources() throws IDMapperException;

    FreeSearchSupportedBean isFreeSearchSupported();

    MappingSupportedBean isMappingSupported( String srcCode, String tgtCode) throws IDMapperException;

    List<XrefBean> mapByXref(String id, String scrCode, List<String> targetCodes) throws IDMapperException;

    List<XRefMapBean> mapByXrefs(List<String> id, List<String> scrCode, List<String> targetCodes) throws IDMapperException;

    XrefExistsBean xrefExists(String id, String scrCode) throws IDMapperException;
 
    CapabilitiesBean getCapabilities();

}
