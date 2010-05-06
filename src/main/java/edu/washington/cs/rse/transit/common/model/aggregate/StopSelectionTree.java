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
package edu.washington.cs.rse.transit.common.model.aggregate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.washington.cs.rse.transit.common.model.StopLocation;

public class StopSelectionTree implements Serializable {

  private static final long serialVersionUID = 1L;

  private StopLocation _stop = null;

  private Map<SelectionName, StopSelectionTree> _branches = new LinkedHashMap<SelectionName, StopSelectionTree>();

  public void setStop(StopLocation stop) {
    if (_stop != null && !_stop.equals(stop))
      throw new IllegalStateException("Stop bean already set!");
    _stop = stop;
  }

  public Set<SelectionName> getNames() {
    return _branches.keySet();
  }

  public boolean hasStop() {
    return _stop != null;
  }

  public StopLocation getStop() {
    return _stop;
  }

  public StopSelectionTree getSubTree(SelectionName name) {
    StopSelectionTree tree = _branches.get(name);
    if (tree == null) {
      tree = new StopSelectionTree();
      _branches.put(name, tree);
    }
    return tree;
  }

  public void moveSubTreeToBack(SelectionName name) {
    if (_branches.containsKey(name)) {
      StopSelectionTree branch = _branches.remove(name);
      _branches.put(name, branch);
    }
  }

  public List<StopLocation> getAllStops() {
    List<StopLocation> stops = new ArrayList<StopLocation>();
    getAllStopsRecursive(stops);
    return stops;
  }

  private void getAllStopsRecursive(List<StopLocation> stops) {
    if( _stop != null)
      stops.add(_stop);
    for(StopSelectionTree tree : _branches.values() )
      tree.getAllStopsRecursive(stops);
  }
}