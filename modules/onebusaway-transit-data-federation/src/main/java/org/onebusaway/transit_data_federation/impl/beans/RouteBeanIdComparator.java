package org.onebusaway.transit_data_federation.impl.beans;

import org.onebusaway.transit_data.model.RouteBean;

import java.util.Comparator;

public class RouteBeanIdComparator implements Comparator<RouteBean> {

  public int compare(RouteBean o1, RouteBean o2) {
    return o1.getId().compareTo(o2.getId());
  }
}
