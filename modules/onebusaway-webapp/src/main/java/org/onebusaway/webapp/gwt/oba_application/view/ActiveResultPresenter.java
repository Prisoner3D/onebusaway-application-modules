package org.onebusaway.webapp.gwt.oba_application.view;

import org.onebusaway.transit_data.model.oba.LocalSearchResult;
import org.onebusaway.webapp.gwt.common.MapOverlayManager;
import org.onebusaway.webapp.gwt.common.widgets.DivPanel;
import org.onebusaway.webapp.gwt.common.widgets.DivWidget;
import org.onebusaway.webapp.gwt.oba_application.control.CommonControl;
import org.onebusaway.webapp.gwt.oba_application.control.StateEvent;
import org.onebusaway.webapp.gwt.oba_application.control.StateEventListener;
import org.onebusaway.webapp.gwt.oba_application.control.state.SearchStartedState;
import org.onebusaway.webapp.gwt.oba_application.control.state.SelectedPlaceChangedState;
import org.onebusaway.webapp.gwt.oba_application.control.state.State;
import org.onebusaway.webapp.gwt.oba_application.control.state.TripPlansState;
import org.onebusaway.webapp.gwt.oba_application.model.TimedLocalSearchResult;
import org.onebusaway.webapp.gwt.oba_application.resources.OneBusAwayStandardResources;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.List;

public class ActiveResultPresenter implements StateEventListener {

  private FlowPanel _widget = new FlowPanel();

  private CommonControl _control;

  private MapOverlayManager _mapOverlayManager;

  private List<Overlay> _currentOverlays = new ArrayList<Overlay>();

  private List<Widget> _currentDirectionWidgets = new ArrayList<Widget>();

  public ActiveResultPresenter() {
    _widget.addStyleName("ActiveResult");
  }

  public void setControl(CommonControl control) {
    _control = control;
  }

  public void setMapOverlayManager(MapOverlayManager manager) {
    _mapOverlayManager = manager;
  }

  public Widget getWidget() {
    return _widget;
  }

  public void handleUpdate(StateEvent evevent) {

    State state = evevent.getState();

    if (state instanceof SearchStartedState) {
      reset();
    } else if (state instanceof SelectedPlaceChangedState) {

      SelectedPlaceChangedState placeChange = (SelectedPlaceChangedState) state;
      reset();

      TimedLocalSearchResult result = placeChange.getSelectedResult();

      if (result != null) {

        LocalSearchResult entry = result.getLocalSearchResult();
        LatLng point = LatLng.newInstance(entry.getLat(), entry.getLon());

        updatWidgetForResult(result);
        openInfoWindowForResult(result);

        Marker m15 = new Marker(point,
            MarkerOptions.newInstance(getStar15Marker()));
        Marker m20 = new Marker(point,
            MarkerOptions.newInstance(getStar20Marker()));
        Marker m30 = new Marker(point,
            MarkerOptions.newInstance(getStar30Marker()));

        _mapOverlayManager.addOverlay(m15, 0, 14);
        _mapOverlayManager.addOverlay(m20, 14, 17);
        _mapOverlayManager.addOverlay(m30, 17, 20);
        _mapOverlayManager.setCenter(point);

        _currentOverlays.add(m15);
        _currentOverlays.add(m20);
        _currentOverlays.add(m30);
      }
    } else if (state instanceof TripPlansState) {
      hideInfoWindow();
      for (Widget widget : _currentDirectionWidgets)
        widget.setVisible(false);
    }
  }

  private void reset() {

    _currentDirectionWidgets.clear();

    _widget.clear();
    _widget.setVisible(false);

    hideInfoWindow();

    if (!_currentOverlays.isEmpty()) {
      for (Overlay overlay : _currentOverlays)
        _mapOverlayManager.removeOverlay(overlay);
      _currentOverlays.clear();
    }

  }

  private void hideInfoWindow() {
    // Hide that info window if need be
    MapWidget map = _mapOverlayManager.getMapWidget();
    InfoWindow window = map.getInfoWindow();
    window.close();
  }

  private void updatWidgetForResult(TimedLocalSearchResult result) {
    fillEntryPanel(result, _widget);
    _widget.setVisible(true);
  }

  private void openInfoWindowForResult(TimedLocalSearchResult result) {

    FlowPanel panel = new FlowPanel();
    fillEntryPanel(result, panel);

    LocalSearchResult entry = result.getLocalSearchResult();

    LatLng point = LatLng.newInstance(entry.getLat(), entry.getLon());
    MapWidget map = _mapOverlayManager.getMapWidget();
    InfoWindow window = map.getInfoWindow();
    window.open(point, new InfoWindowContent(panel));
  }

  private void fillEntryPanel(TimedLocalSearchResult result, FlowPanel panel) {

    LocalSearchResult entry = result.getLocalSearchResult();

    DivPanel namePanel = new DivPanel();
    namePanel.addStyleName("ActiveResult-NamePanel");
    panel.add(namePanel);

    if (entry.getUrl() == null) {
      DivWidget name = new DivWidget(entry.getName());
      name.addStyleName("ActiveResult-Name");
      namePanel.add(name);
    } else {
      Anchor name = new Anchor(entry.getName(), entry.getUrl());
      name.addStyleName("ActiveResult-Name");
      name.setTarget("_blank");
      namePanel.add(name);
    }

    DivWidget address = new DivWidget(getFormatedAddressForEntry(entry));
    address.addStyleName("ActiveResult-Address");
    panel.add(address);

    DivWidget phone = new DivWidget(getFormatedPhoneNumber(entry));
    phone.addStyleName("ActiveResult-Phone");
    panel.add(phone);

    DivPanel ratingPanel = new DivPanel();
    ratingPanel.addStyleName("ActiveResult-RatingPanel");
    panel.add(ratingPanel);

    if (entry.getRatingUrl() != null) {
      Image image = new Image(entry.getRatingUrl());
      image.setSize("84px", "17px");
      image.addStyleName("ActiveResult-RatingImage");
      ratingPanel.add(image);
    } else if (entry.getRatingUrlSmall() != null) {
      Image image = new Image(entry.getRatingUrlSmall());
      image.addStyleName("ActiveResult-RatingImageSmall");
      ratingPanel.add(image);
    } else {
      DivWidget rating = new DivWidget(entry.getRating() + "/"
          + entry.getMaxRating());
      ratingPanel.add(rating);
    }

    DivWidget time = new DivWidget("Travel time: ~" + (result.getTime() / 60)
        + " mins");
    time.addStyleName("ActiveResult-TravelTime");
    panel.add(time);

    _currentDirectionWidgets.add(time);

    DivPanel directionsPanel = new DivPanel();
    directionsPanel.addStyleName("ActiveResult-DirectionsPanel");
    panel.add(directionsPanel);

    Anchor directions = new Anchor("Get Directions Using Public Transit");
    directions.addStyleName("ActiveResult-Directions");
    directions.addClickHandler(new DirectionsHandler(entry));
    directionsPanel.add(directions);

    _currentDirectionWidgets.add(directions);
  }

  private String getFormatedAddressForEntry(LocalSearchResult entry) {
    return entry.getAddress() + " " + entry.getCity() + ", "
        + entry.getRegion() + " " + entry.getZip();
  }

  private String getFormatedPhoneNumber(LocalSearchResult entry) {

    String number = entry.getPhoneNumber();

    if (number.length() == 10) {
      return "(" + number.substring(0, 3) + ") " + number.substring(3, 6) + "-"
          + number.substring(6);
    } else if (number.length() == 7) {
      return number.substring(0, 3) + "-" + number.substring(3);
    } else {
      return number;
    }
  }

  private Icon getStar30Marker() {
    OneBusAwayStandardResources resources = OneBusAwayStandardResources.INSTANCE;
    DataResource r = resources.getStar30();
    Icon icon = Icon.newInstance(r.getUrl());
    icon.setIconSize(Size.newInstance(30, 28));
    icon.setIconAnchor(Point.newInstance(15, 14));
    return icon;
  }

  private Icon getStar20Marker() {
    OneBusAwayStandardResources resources = OneBusAwayStandardResources.INSTANCE;
    DataResource r = resources.getStar20();
    Icon icon = Icon.newInstance(r.getUrl());
    icon.setIconSize(Size.newInstance(20, 19));
    icon.setIconAnchor(Point.newInstance(10, 10));
    return icon;
  }

  private Icon getStar15Marker() {
    OneBusAwayStandardResources resources = OneBusAwayStandardResources.INSTANCE;
    DataResource r = resources.getStar15();
    Icon icon = Icon.newInstance(r.getUrl());
    icon.setIconSize(Size.newInstance(15, 14));
    icon.setIconAnchor(Point.newInstance(8, 7));
    return icon;
  }

  private final class DirectionsHandler implements ClickHandler {

    private LocalSearchResult entry;

    private DirectionsHandler(LocalSearchResult entry) {
      this.entry = entry;
    }

    public void onClick(ClickEvent arg0) {
      _control.getDirectionsToPlace(entry);
    }
  }
}
