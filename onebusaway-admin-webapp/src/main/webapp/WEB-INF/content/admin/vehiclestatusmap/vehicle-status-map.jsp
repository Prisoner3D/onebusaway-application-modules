<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright (C) 2017 Cambridge Systematics, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

-->
<html xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:c="http://java.sun.com/jsp/jstl/core" xmlns:s="/struts-tags"
      xmlns:wiki="/oba-wiki-tags">
<jsp:directive.page contentType="text/html" />
<head>

    <s:url var="url" value="/css/map/map.css">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <link rel="stylesheet" href="${url}" type="text/css" media="screen,print"/>

    <s:url var="url" value="/css/map/popup.css">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <link rel="stylesheet" href="${url}" type="text/css" media="screen,print"/>

    <s:url var="url" value="/css/map/wizard.css">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <link rel="stylesheet" href="${url}" type="text/css" media="screen,print"/>

    <jsp:text>
        &lt;!--[if IE 6]&gt;
    </jsp:text>
    <s:url var="url" value="/css/ie6.css">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <link rel="stylesheet" href="${url}" type="text/css" media="screen,print"/>
    <jsp:text>
        &lt;![endif]--&gt;
    </jsp:text>

    <s:url var="url" value="https://maps.googleapis.com/maps/api/js">
        <s:param name="v">3</s:param>
        <s:param name="client"><s:property value="googleMapsClientId" /></s:param>
        <s:param name="channel"><s:property value="googleMapsChannelId" /></s:param>
        <s:param name="sensor">false</s:param>
    </s:url>
    <script src="${url}" type="text/javascript"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/lib/jquery.ui.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/lib/jquery.history.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/lib/markerManager.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/lib/rgbcolor.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/lib/dateFormat.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/lib/popover.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/oba/map/Wizard.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/api/config">
        <s:param name="v"><s:property value="cacheBreaker" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/oba/Util.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/oba/map/GoogleMapWrapper.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/oba/map/Popups.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/oba/map/RouteMap.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <s:url var="url" value="/js/oba/map/Sidebar.js">
        <s:param name="v"><s:property value="frontEndVersion" /></s:param>
    </s:url>
    <script type="text/javascript" src="${url}"><!-- //prevent jspx minimization --></script>

    <script type="text/javascript" src="https://translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"><!-- jspx --></script>

</head>
<body>
<div id="content" class="map">
    <div id="searchbar">
        <div class="sidebar">
            <div id="map_header">
                <div class="title">
                    <s:url var="url" value="/"></s:url>
                    <h1><a href="${url}"><s:property value="getText('main.title')" /></a></h1>
                </div>

                <ul>
                    <s:url var="url" namespace="/m" action="index"/>
                    <li id="link_mobile" aria-label="Text only version"><a href="${url}">Text / Mobile</a></li>

                    <s:url var="url" namespace="/wiki" action="About"/>
                    <li id="link_about"><a href="${url}">About</a></li>

                    <s:url var="url" namespace="/wiki" action="ContactUs"/>
                    <li id="link_contact"><a href="${url}">Contact</a></li>

                    <s:url var="url" namespace="/wiki/Developers" action="Index"/>
                    <li id="link_developers"><a href="${url}">Developers</a></li>

                    <s:url var="url" namespace="/wiki/Help" action="Index"/>
                    <li id="link_help"><a href="${url}">Help</a></li>
                </ul>
            </div>

            <form action="search.action">
                <input type="text" name="q" value=""/>
                <input type="submit" value="Find" class="submit"/>
                <p id="tip">TIP: Enter an intersection, bus route or bus stop code.</p>
            </form>

            <div id="matches">
                <h2>Results:</h2>
                <ul><!-- //prevent jspx minimization --></ul>
            </div>

            <div id="filtered-matches">
                <h2>Other routes here:</h2>
                <ul><!-- //prevent jspx minimization --></ul>
            </div>

            <div id="suggestions">
                <h2>Did You Mean?</h2>
                <ul></ul>
            </div>

            <div id="no-results">&amp;nbsp;</div>

            <div id="loading">
                <div class="loading">
                    <span>Loading...</span>
                </div>
            </div>

            <!-- Include the welcome div with example searches -->
            <c:import url="includes/welcome.jspx" />

            <s:url var="url" namespace="/routes" action="index"/>
            <div id="available-routes"><a href="${url}">Click here</a> for a list of available routes.</div>

            <div id="cant-find">
                <h2>Can't find your bus route?</h2>
                <s:url var="url" namespace="/routes" action="index" />
                <p><s:property value="getText('main.cantfindroute.text')" /> <a href="${url}">Click here</a> to see which routes are currently available.</p>
            </div>

            <s:if test="globalServiceAlerts!=null">
                <div id="global-alerts" class="global-alerts">
                    <s:iterator value="globalServiceAlerts">
                        <s:iterator value="descriptions">
                            <p class="global-alert-content"><strong>Service Notice: </strong><s:property value="value" /></p>
                        </s:iterator>
                    </s:iterator>
                </div>
            </s:if>

            <!-- Include the Google ad -->
            <c:import url="includes/ad.jspx" />

        </div>
        <div class="container clear"><!-- jspx --></div>
        <s:url var="url" value="/css/img/google_logo.png"></s:url>
        <div id="google_translate_element">Google Translate <img id="icon" src="${url}" /></div>
    </div>

    <div id="map"><!-- //prevent jspx minimization --></div>

    <div id="wizard">
        <div id="wizard_start">
            <ul>
                <li id="wizard-col1">
                    <a data-placement="right" class="wizard_panel" rel="popover">
                        <div id="inner-col1">
                            <h2>New to <s:property value="getText('main.application.name')" />?</h2>
                            <p>Click here for instructions:</p>
                            <p>Just a few easy steps!</p>
                        </div>
                    </a>
                </li>
                <li id="wizard-col2">
                    <p>Also</p>
                    <p>available</p>
                    <p>using:</p>
                </li>
                <li id="wizard-col3">
                    <s:url var="url" namespace="/wiki/Help" action="MobileOverview"/>
                    <a href="${url}" target="_new">
                        <p>Mobile Web</p>
                        <a rel="popover"><div id="smartphone_img">&amp;nbsp;</div></a>
                    </a>
                </li>
                <li id="wizard-col4">
                    <s:url var="url" namespace="/wiki/Help" action="SMSOverview"/>
                    <a href="${url}" target="_new">
                        <p>SMS</p>
                        <a rel="popover"><div id="sms_img">&amp;nbsp;</div></a>
                    </a>
                </li>
            </ul>
            <a class="close"><span class="close_txt">Close</span> ×</a>
        </div>
        <div id="wizard_didyoumean">
            <a data-placement="right" class="wizard_panel" rel="popover">&amp;nbsp;</a>
            <a class="close"><span class="close_txt">Close</span> ×</a>
        </div>
        <div id="wizard_inuse">
            <a data-placement="right" class="wizard_panel" rel="popover">&amp;nbsp;</a>
            <span class="text_span">Follow the instructions above . . .</span>
            <a class="close"><span class="close_txt">Close</span> ×</a>
        </div>
        <div id="wizard_finaltip">
            <div>
                <p><span style="font-weight: bold;">Tips:</span> <a data-placement="right" class="wizard_panel" rel="popover">&amp;nbsp;</a> <s:property value="getText('main.application.name')" /> is also available via <a id="wizard_mobile" rel="popover">Mobile Web</a> or <a id="wizard_sms" rel="popover">SMS/Text Message</a>.</p>
                <p>Remember your Stopcode from the pop-ups or find it on a <a id="tips_code_popup" rel="popover" data-placement="above">bus stop pole box</a>.</p>
                <p id="sharelinks"><a id="wizard_share" rel="popover">Share</a> this link and tell others about <s:property value="getText('main.application.name')" />!
                    <s:set var="link" value="getText('main.agency.twitter.link')" />
                    <a href="${link}" target="_new"><span class="ui-icon ui-icon-twitter"> &amp;nbsp; </span></a>
                    <s:set var="link" value="getText('main.agency.facebook.link')" />
                    <a href="${link}" target="_new"><span class="ui-icon ui-icon-facebook"> &amp;nbsp; </span></a>
                    <s:set var="link" value="getText('main.agency.youtube.link')" />
                    <a href="${link}" target="_new"><span class="ui-icon ui-icon-youtube"> &amp;nbsp; </span></a>
                    <s:set var="link" value="getText('main.agency.flickr.link')" />
                    <a href="${link}" target="_new"><span class="ui-icon ui-icon-flickr"> &amp;nbsp; </span></a>
                </p>
            </div>
            <a class="close"><span class="close_txt">Close</span> ×</a>
        </div>
    </div>

    <div id="map-global-alerts" class="global-alerts">
        <div id="close-map-global-alerts"><a href="#">[close]</a></div>
        <div class="global-alerts-content">
            <s:iterator value="globalServiceAlerts">
                <s:iterator value="descriptions">
                    <p class="global-alert-content"><strong>Service Notice: </strong><s:property value="value" /></p>
                </s:iterator>
            </s:iterator>
        </div>
    </div>

</div>
</body>
</html>

