/*
 * Copyright 2008 Brian Ferris
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.onebusaway.transit_data.model;

import java.util.List;

public class StopWithArrivalsAndDeparturesBean extends ApplicationBean {

  private static final long serialVersionUID = 1L;

  private StopBean stop;

  private List<ArrivalAndDepartureBean> arrivalsAndDepartures;

  private List<StopBean> nearbyStops;

  public StopWithArrivalsAndDeparturesBean() {

  }

  public StopWithArrivalsAndDeparturesBean(StopBean stop,
      List<ArrivalAndDepartureBean> arrivalsAndDepartures,
      List<StopBean> nearbyStops) {
    this.stop = stop;
    this.arrivalsAndDepartures = arrivalsAndDepartures;
    this.nearbyStops = nearbyStops;
  }

  public StopBean getStop() {
    return stop;
  }

  public List<ArrivalAndDepartureBean> getArrivalsAndDepartures() {
    return arrivalsAndDepartures;
  }

  public List<StopBean> getNearbyStops() {
    return nearbyStops;
  }
}
