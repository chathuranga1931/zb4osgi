package it.cnr.isti.zigbee.hc.driver;
/*
   Copyright 2013-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies
   of the Italian National Research Council


   See the NOTICE file distributed with this work for additional
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/



import org.osgi.framework.Constants;
import org.osgi.service.cm.ManagedService;

/**
 *
 * The interface of the service registered by the <b>Health Care Driver</b> architecture<br>
 * which is used to provides among all the bundles composing the <b>Health Care Driver</b>,<br>
 * (i.e.: core drivers and driver extension) the default configuration of the reporting<br>
 * <br>
 * Interfaces that contains the definition of all the properties key and default values,<br>
 * that affects the reporting behavior of the <b>Health Care Driver</b> architecture
 *
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public interface HCReportingConfiguration extends ManagedService {

    public final static String SERVICE_FILTER = "( " + Constants.OBJECTCLASS + "=" + HCReportingConfiguration.class.getName() + ")";

    /**
     * The key to {@link Integer} property that set the default value for the <b>Minimum Rerporting Interval</b> field
     * of the <br>
     * <b>Configure Reporting Command</b> command. By default it sets the reporting to up one message per minute
     */
    public static final String CONFIGURE_REPORTING_MIN_KEY = "it.cnr.isti.zigbee.hc.reporting.min";
    /**
     * The default value for the property {@link #CONFIGURE_REPORTING_MIN_KEY}, that is 60
     */
    public static final int DEFAULT_CONFIGURE_REPORTING_MIN = 60;


    /**
     * The key to {@link Integer} property that set the default value for the <b>Maximum Rerporting Interval</b> field
     * of the <br>
     * <b>Configure Reporting Command</b> command. By default it sets the reporting to send message only when value change
     */
    public static final String CONFIGURE_REPORTING_MAX_KEY = "it.cnr.isti.zigbee.hc.reporting.max";
    /**
     * The default value for the property {@link #CONFIGURE_REPORTING_MAX_KEY}, that is 0
     */
    public static final int DEFAULT_CONFIGURE_REPORTING_MAX = 0;


    /**
     * The key to {@link Double} property that set the default value for the <b>Rerportable Change</b> field of the <br>
     * <b>Configure Reporting Command</b> command. By default it sets the reporting to send message for any change
     */
    public static final String CONFIGURE_REPORTING_CHANGE_KEY = "it.cnr.isti.zigbee.hc.reporting.change";
    /**
     * The default value for the property {@link #CONFIGURE_REPORTING_CHANGE_KEY}, that is 0.0
     */
    public static final double DEFAULT_CONFIGURE_REPORTING_CHANGE = 0.0d;

    /**
     * The key to {@link Boolean} property that specify if the reporting should overwrite pre-existing configuration or not<br>
     * By default the system overwrite the configuration
     */
    public static final String CONFIGURE_REPORTING_OVERWRITE_KEY = "it.cnr.isti.zigbee.hc.reporting.overwrite";
    /**
     * The default value for the property {@link #CONFIGURE_REPORTING_OVERWRITE_KEY}, that is true
     */
    public static final boolean DEFAULT_CONFIGURE_REPORTING_OVERWRITE = true;

    public abstract int getReportingMinimum();

    public abstract int getReportingMaximum();

    public abstract double getReportingChange();

    public abstract boolean getReportingOverwrite();

}