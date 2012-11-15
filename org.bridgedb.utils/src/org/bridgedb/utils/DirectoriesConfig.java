// BridgeDb,
// An abstraction layer for identifier mapping services, both local and online.
//
// Copyright      2012  Christian Y. A. Brenninkmeijer
// Copyright      2012  OpenPhacts
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.bridgedb.utils;
import org.bridgedb.rdf.*;

import java.io.File;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.bridgedb.IDMapperException;

/**
 *
 * @author Christian
 */
public class DirectoriesConfig {
    public static final String VOID_DIRECTORY_PROPERTY = "VoidDirectory";
    public static final String LINKSET_DIRECTORY_PROPERTY = "LinksetDirectory";
    public static final String TRANSATIVE_DIRECTORY_PROPERTY = "TransativeDirectory";
    public static final String EXPORT_DIRECTORY_PROPERTY = "ExportDirectory";
    public static final String CONFIG_FILE_NAME = "DirectoriesConfig.txt";

    private static Properties properties;

    static final Logger logger = Logger.getLogger(DirectoriesConfig.class);

    public static File getVoidDirectory() throws IDMapperException {
        return getDirectory(VOID_DIRECTORY_PROPERTY, "Void");
    }
    
    public static File getLinksetDirectory() throws IDMapperException {
        return getDirectory(LINKSET_DIRECTORY_PROPERTY, "Linkset");
    }
    
    public static File getTransativeDirectory() throws IDMapperException {
        return getDirectory(TRANSATIVE_DIRECTORY_PROPERTY, "Transative");
    }
    
    public static File getExportDirectory() throws IDMapperException {
        return getDirectory(EXPORT_DIRECTORY_PROPERTY, "Export");
    }
    
    private static File getDirectory(String property, String type) throws IDMapperException {
        String fileName = getProperties().getProperty(property);
        if (fileName == null || fileName.isEmpty()){
            logger.warn("No directory property found for " + type + " so just using " + type + " as a relative file name");
            fileName = type;
        }
        File file = new File(fileName);
        if (!file.exists()){
            File parent = file.getParentFile();
            if (parent != null && parent.isDirectory()){
                boolean made = file.mkdir();
                if (!made){
                    throw new BridgeDBException("Unable to create " + type + " directory " + file.getAbsolutePath());
                }
            } else {
                throw new BridgeDBException("No parent found for " + type + " directory " + file.getAbsolutePath());
            }
        }
        if (!file.isDirectory()){
            throw new BridgeDBException(type + " directory " + file.getAbsolutePath() + " is not a directory");            
        }
        return file;
    }
     
    private static Properties getProperties() throws IDMapperException{
        if (properties == null){
            properties = ConfigReader.getProperties(CONFIG_FILE_NAME);
        }
        return properties;
    }

}