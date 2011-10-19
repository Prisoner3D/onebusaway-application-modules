Introduction

  OneBusAway has the concept of a <<transit data bundle>>, which is a collection of all the data artifacts for a transit agency (or group of transit agencies) in the internal format needed to power OneBusAway.  These transit data bundles are typically created from external data such as GTFS feeds for transit data and [OpenStreetMap](./open-street-map.html) data for the street network.  This document will walk you through the steps in creating a new transit data bundle.

Creating the Bundle

  To create the bundle, you'll need to download the `onebusaway-transit-data-federation` module:
  
  * [}onebusaway-transit-data-federation-${currentVersion}.jar](http://nexus.onebusaway.org/service/local/artifact/maven/content?r=public&g=org.onebusaway&a=onebusaway-transit-data-federation&c=withAllDependencies&e=jar&v=${currentVersion)

  []
  
  The jar file is automatically configured to run the main class for building transit data bundles:

~~~
org.onebusaway.transit_data_federation.bundle.FederatedTransitDataBundleCreatorMain
~~~

  Thus, you can simply run the builder with:
  
~~~
java -jar onebusaway-transit-data-federation.jar ...
~~~

  <<Note:>> Depending on the size of your transit network, you may need to increase the amount of memory available to the Java VM with an argument like `java -Xmx1G -jar ...`.

Quick Config

  By default, the builder accepts two command line options:

  * path/to/your/gtfs.zip - path to your GTFS feed
  
  * bundle_output_path - the output directory where bundle artifacts will be written
  
  []

  This quick start mode can be used to quickly build a transit data bundle for a single transit agency, but without out much configuration flexibility.  If you need more flexibility, see Advanced Config below.

Advanced Config

  The bundle also accepts an xml file command line argument for more advanced configuration options:

  * bundle.xml - path to your bundle config xml file
  
  * bundle_output_path - the output directory where bundle artifacts will be written
  
  []

  The configuration details for the bundle are captured in an xml file.  The `bundle.xml` xml config file gives you a lot of control of how your bundle is built:

  * change the target install database - see [Database Setup and Configuration](./database-setup-and-configuration.html) for more details
  
  * combine multiple GTFS feeds
  
  * override and remap GTFS agency ids
  
  * add additional build phases
  
  []

  Let's look at a quick example:

~~~
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd">

    <!-- Provides support for specifying "${some.java.system.property}" for bean values -->
    <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer" />

    <!-- Database Connection Configuration -->
    <!-- Note that ${bundlePath} will automatically be expanded to the bundle output directory you specify -->
    <!-- as the second argument to org.onebusaway.transit_data_federation.bundle.FederatedTransitDataBundleCreatorMain -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="org.hsqldb.jdbcDriver" />
        <property name="url" value="jdbc:hsqldb:file:${bundlePath}/org_onebusaway_database" />
        <property name="username" value="sa" />
        <property name="password" value="" />
    </bean>

    <bean id="webappHibernateProperties" class="org.onebusaway.container.spring.PropertiesBeanPostProcessor">
        <property name="target" value="hibernateProperties" />
        <property name="properties">
            <props>
                <prop key="hibernate.dialect">org.hibernate.dialect.HSQLDialect</prop>
            </props>
        </property>
    </bean>

    <bean id="gtfs-bundles" class="org.onebusaway.transit_data_federation.bundle.model.GtfsBundles">
        <property name="bundles">
            <list>
                <ref bean="gtfsA" />
                <!-- References to other GTFS feeds could go here -->
            </list>
        </property>
    </bean>

    <bean id="gtfsA" class="org.onebusaway.transit_data_federation.bundle.model.GtfsBundle">
        <property name="path" value="path/to/your/gtfs.zip" />
        <property name="defaultAgencyId" value="1" />
        <property name="agencyIdMappings">
            <map>
                <!-- Map GTFS Agency IDs to their APTA Agency Id -->
                <entry key="KCM" value="1" />
                <entry key="EOS" value="23" />
                <entry key="ST" value="40" />
            </map>
        </property>
    </bean>

    <!-- Need a mechanism to combine stops from different feeds? -->
    <bean id="entityReplacementStrategyFactory" class="org.onebusaway.transit_data_federation.bundle.tasks.EntityReplacementStrategyFactory">
        <property name="entityMappings">
            <map>
                <entry key="org.onebusaway.gtfs.model.Stop" value="${onebusaway_prefix}/onebusaway-wiki/PugetSoundStopConsolidation.wiki" />
            </map>
        </property>
    </bean>
    <bean id="entityReplacementStrategy" factory-bean="entityReplacementStrategyFactory" factory-method="create"/>
 
</beans>
~~~

  This configuration file is just a [Spring bean configuration file](http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/beans.html), so you can perform arbitrarily complex configuration here.  Documentation on specific advanced configuration features can be found below:

* Open Street Map Configuration

  By default, we use [OpenStreetMap](http://www.openstreetmap.org/) data to provide the street / pedestrian network layer for OneBusAway.  Also, by default we automatically download OSM data from the OSM api service for the area covered by your transit data.  This usually works just fine, but you may wish to tweak or override that process to suite your needs.

  For example, you can change the place OSM data is cached:

~~~
<bean class="org.onebusaway.container.spring.PropertyOverrideConfigurer">
    <property name="properties">
        <props>
            <prop key="osmProvider.cacheDirectory">path/to/osm/cache/directory</prop>
        </props>
    </property>
</bean>
~~~

  If you are having trouble with the OSM api service failing because there is too much OSM data to be downloaded in your region, you can specify a specific OSM file of your choosing instead:

~~~
<bean id="osmProvider" class="org.opentripplanner.graph_builder.impl.osm.FileBasedOpenStreetMapProviderImpl">
    <!-- Can be compressed or uncompressed -->   
    <property name="path" value="path/to/your/data.osm.gz" />
</bean>
~~~

  For more details on crafting a custom OSM file for your region, see [OpenStreetMap](./open-street-map.html).

Customizing the Build Phase

  The transit data bundle building process is broken up into a number of phases.  The default build phases are:

  * `start` - the first phase, responsible for cleaning up the db if run
  
  * `gtfs` - load the GTFS into the database
  
  * `route_collections` - compute aggregate route collections and load them into the database
  
  * `route_search_index` - compute the route name search index
  
  * `stop_search_index` - compute the stop name search index
  
  * `calendar_service` - compute service calendar information
  
  * `otp_graph` - compute the OTP street graph
  
  * `transit_graph` - compute the transit network graph
  
  * `block_indices` - compute block indices for fast time-based indexing
  
  * `narratives` - compute various narrative objects
  
  * `stop_transfers` - compute an optimized set of stop transfers
  
  * `shape_geospatial_index` - create a geospatial index from route shape information
  
  * `pre_cache` - prime the cache by requesting a number of common data structures
  
  []
  
  There are also a number of optional tasks not enabled by default:

  * `transfer_patterns` - transfer pattern calculation for advanced trip planning functionality
  
  * `transfer_patterns_hub_analysis` - identify hub stops from local transfer patterns
  
  * `serialized_transfer_patterns` - construct optimized serialized transfer pattern data structure from raw patterns
  
  * `block_location_history` - combine historical real-time data into an optimized form
  
  []

* Command-Line Options

  The bundle builder accepts a number of command-line options that can control the build process, allowing you to control which phases of the build are run.

  * `-skip phase_name` - skips the specified build phase.  Can be repeated.
  
  * `-only phase_name` - only runs the specified build phase.  Can be repeated.
  
  * `-skipTo phase_name` - jump ahead to the specified build phase.  Only specify once.
  
  * `-include phase_name` - include the specified build phase, useful when the phase is not enabled by default.  Can be repeated.
  
  []

* Adding a Custom Build Phase

  You can add your own custom build phases into the build process.  To do so, you specify a task definition in your `bundle.xml` config:

~~~
    <bean class="org.onebusaway.transit_data_federation.bundle.model.TaskDefinition">
        <!-- Required -->
        <property name="taskName" value="TASK_NAME_GOES_HERE" />
        <!-- Optional -->
        <property name="afterTaskName" value="SOME_EXISTING_TASK_NAME" />
        <!-- Optional -->
        <property name="beforeTaskName" value="SOME_EXISTING_TASK_NAME" />
        <!-- Optional -->
        <property name="enabled" value="true" />
        <!-- Required -->
        <property name="task" ref="taskBeanName" />
    </bean>

    <bean id="taskBeanName" class="your.task.Definition" />
~~~

  As you can see, the TaskDefinition bean allows you to define custom tasks and optionally control where they appear in the build order.  The only requirement is that your task instance implement `Runnable`.

  In addition to adding your task definition to your `bundle.xml`, you'll also need to add the jar or class files with your task implementation to the classpath when you run the build process.