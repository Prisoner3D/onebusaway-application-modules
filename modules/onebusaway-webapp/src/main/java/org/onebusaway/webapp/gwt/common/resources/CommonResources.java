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
package org.onebusaway.webapp.gwt.common.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;

public interface CommonResources extends ClientBundle {

  public static CommonResources INSTANCE = GWT.create(CommonResources.class);

  @Source("Bus.png")
  public DataResource getImageBus();

  @Source("HiddenPixel.png")
  public DataResource getHiddenPixel();
}
