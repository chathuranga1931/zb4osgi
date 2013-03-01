/*
   Copyright 2008-2010 CNR-ISTI, http://isti.cnr.it
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

package it.cnr.isti.zigbee.ha.cluster.impl.event;

import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;
import it.cnr.isti.zigbee.ha.cluster.glue.general.event.PresentValueEvent;
import it.cnr.isti.zigbee.ha.cluster.glue.general.event.PresentValueListener;
import it.cnr.isti.zigbee.ha.driver.core.ReportingConfiguration;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.ReportListener;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
*
* @author <a href="mailto:h.alink1@chello.nl">Han Alink</a>
* @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
* @version $LastChangedRevision$ ($LastChangedDate$)
* @since 0.7.0
*
*/
public class PresentValueBridgeListeners implements ReportListener {

    private class PresentValueEventImpl implements PresentValueEvent{

        private final Cluster source;
        private final boolean event;

        public PresentValueEventImpl(Cluster cluster, Boolean value) {
            source = cluster;
            event = value;
        }

        public boolean getEvent() {
            return event;
        }

        public Cluster getSource() {
            return source;
        }
    }

    private final Attribute bridged;
    private final ArrayList<PresentValueListener> listeners = new ArrayList<PresentValueListener>();
    private final Cluster cluster;
    private final ReportingConfiguration configuration;

    public PresentValueBridgeListeners(final ReportingConfiguration conf, final Attribute attribute, final Cluster c) {
        bridged = attribute;
        cluster = c;
        configuration = conf;
    }

    public void receivedReport(Dictionary<Attribute, Object> reports) {
        if (reports.get(bridged) == null) {
            return;
        }
        synchronized (listeners) {
            for (PresentValueListener listener : listeners) {
                listener.changedPresentValue(new PresentValueEventImpl(cluster, (Boolean) reports.get(bridged)));
            }
        }
    }

    public List<PresentValueListener> getListeners(){
        return listeners;
    }

    public boolean subscribe(PresentValueListener listener) {
        synchronized (listeners) {
            if ( listeners.size() == 0 ) {
                Subscription subscription = bridged.getSubscription();
                if ( configuration.getReportingOverwrite() || subscription.isActive() == false ) {
                    subscription.setMaximumReportingInterval(configuration.getReportingMaximum());
                    subscription.setMinimumReportingInterval(configuration.getReportingMinimum());
                    subscription.updateConfiguration();
                }
                if ( subscription.addReportListner(this) == false ) {
                    return false;
                }
            }
            return listeners.add(listener);
        }
    }

    public boolean unsubscribe(PresentValueListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
            if ( listeners.size() == 0 ) {
                Subscription subscription = bridged.getSubscription();
                if ( subscription.getReportListenersCount() == 1 ){
                    subscription.clear();
                }
            }
        }
        return true;
    }
}
