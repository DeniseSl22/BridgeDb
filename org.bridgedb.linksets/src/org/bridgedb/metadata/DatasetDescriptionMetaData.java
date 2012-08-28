/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bridgedb.metadata;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.bridgedb.linkset.constants.DctermsConstants;
import org.bridgedb.linkset.constants.FoafConstants;
import org.bridgedb.linkset.constants.PavConstants;
import org.bridgedb.linkset.constants.RdfConstants;
import org.bridgedb.linkset.constants.VoidConstants;
import org.openrdf.model.Resource;
import org.openrdf.model.URI;

/**
 *
 * @author Christian
 */
public class DatasetDescriptionMetaData extends MetaData{

    Set<DataSetMetaData> dataSets;
    
    public DatasetDescriptionMetaData(Resource id, RDFData input){
        super(id, input);
    }
    
    public DatasetDescriptionMetaData(RDFData input){
        super(input);
    }

    @Override
    void setupValues() {
        values.add(new SingletonValue("Title", DctermsConstants.TITLE, String.class, RequirementLevel.MAY));
        values.add(new SingletonValue("Description", DctermsConstants.DESCRIPTION, String.class, RequirementLevel.MAY));
        values.add(new SingletonValue("Created By", PavConstants.CREATED_BY, String.class, RequirementLevel.MAY));
        values.add(new SingletonValue("Created On", PavConstants.CREATED_ON, Date.class, RequirementLevel.MAY));
        values.add(new SingletonValue("Primary Topic", FoafConstants.PRIMARY_TOPIC, String.class, RequirementLevel.MAY));
    }

    @Override
    URI getResourceType() {
        return VoidConstants.DATASET_DESCRIPTION;
    }

    @Override
    void readFromInput(RDFData input) {
        super.readFromInput(input);
        dataSets = new HashSet<DataSetMetaData>(); 
        Resource datasetId = getByPredicateObject(input,  RdfConstants.TYPE_URI, DataSetMetaData.RESOURCE_TYPE);
        while (datasetId != null){
            dataSets.add(new DataSetMetaData(datasetId, input));
            datasetId = getByPredicateObject(input,  RdfConstants.TYPE_URI, DataSetMetaData.RESOURCE_TYPE);
        }
    }
    
    @Override
    void addChildren(StringBuilder builder, RequirementLevel forceLevel) {
        for (DataSetMetaData dataset:dataSets){
            dataset.addInfo(builder, forceLevel);
        }
        //No children here
    }

    public boolean hasRequiredValues(RequirementLevel forceLevel, boolean exceptAlternatives){
        if (!super.hasRequiredValues(forceLevel, exceptAlternatives)) { return false; }
        for (DataSetMetaData dataset:dataSets){
            if (!dataset.hasRequiredValues(forceLevel, exceptAlternatives)) { return false; }
        }
        return true;
    }

    public boolean hasCorrectTypes(){
        if (!super.hasCorrectTypes()) { return false; }
        for (DataSetMetaData dataset:dataSets){
            if (!dataset.hasCorrectTypes()) { return false; }
        }
        return true;
    }

    void validityReport(StringBuilder builder, RequirementLevel forceLevel, boolean exceptAlternatives, 
            boolean includeWarnings){
        super.validityReport(builder, forceLevel, exceptAlternatives, includeWarnings);
        for (DataSetMetaData dataset:dataSets){
            dataset.validityReport(builder, forceLevel, exceptAlternatives, includeWarnings);
        }
    }
}