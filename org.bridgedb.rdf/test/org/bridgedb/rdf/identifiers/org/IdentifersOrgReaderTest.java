// BridgeDb,
// An abstraction layer for identifier mapping services, both local and online.
//
// Copyright 2006-2009  BridgeDb developers
// Copyright 2012-2013  Christian Y. A. Brenninkmeijer
// Copyright 2012-2013  OpenPhacts
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
package org.bridgedb.rdf.identifiers.org;

import org.bridgedb.rdf.UriPattern;
import org.bridgedb.utils.BridgeDBException;
import org.bridgedb.utils.Reporter;
import org.junit.Test;

/**
 *
 * @author Christian
 */
public class IdentifersOrgReaderTest {
    
    /** Test of init method, of class IdentifersOrgReader.
     */
    @Test
    public void testInit() throws Exception {
        Reporter.println("init");
        try { 
            UriPattern.refreshUriPatterns();
        } catch (BridgeDBException ex) {
            if (ex.getMessage().equals(IdentifersOrgReader.UNABLE_TO_CONNECT)){
                Reporter.error("**** ERROR CONNECTING TO MIRIAM ****");
                Reporter.error("IdentifersOrgReader Test abanndoned.");
                Reporter.error("Check Miriam RDF URI is still correct.");
            } else {
                throw ex;
            }
        }
    }

}
