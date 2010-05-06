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
package edu.washington.cs.rse.transit.web.oba.common.client.model;

public class ServicePatternPathBean extends ApplicationBean {

    private static final long serialVersionUID = 1L;

    private ServicePatternBean _servicePattern;

    private double[] _lat;

    private double[] _lon;

    public ServicePatternPathBean() {

    }

    public ServicePatternPathBean(ServicePatternBean servicePattern, double[] lat, double[] lon) {
        _servicePattern = servicePattern;
        _lat = lat;
        _lon = lon;
    }

    public ServicePatternBean getServicePattern() {
        return _servicePattern;
    }

    public void setServicePattern(ServicePatternBean servicePattern) {
        this._servicePattern = servicePattern;
    }

    public double[] getLat() {
        return _lat;
    }

    public void setLat(double[] lat) {
        _lat = lat;
    }

    public double[] getLon() {
        return _lon;
    }

    public void setLon(double[] lon) {
        _lon = lon;
    }
}