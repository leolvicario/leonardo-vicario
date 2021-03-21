##Automation Framework Solution

###Prerequisites
In order to run the project there are some necessary steps required:
1. Install Java (version 8 or above)
2. Install Maven (version 3.6)
3. Install NodeJs
4. Install Android SDK
5. Define and export JAVA_HOME, M2_HOME (Maven), ANDROID_HOME. Add them to the system's path. In the case of ANDROID_HOME, add the path not to it itself but to build-tools/xx.xx.xx, cmdline-tools/latest, emulator, platform-tools and tools.   
6. Install Appium
7. Install Lombok plugin to IDE (used IntelliJ)
8. Create an emulator and replace values from settings.yml (mobile), there's a line to launch emulator through Runtime if desired
9. As an additional step and for a particular usage, install and define "opencv4nodejs" for image recognition if desired (not tested due to incompatibilities)

###Structure
The solution provided for the challenge consist on a Maven project written in Java, splitted into three different modules, 
each one with a specific purpose to ensure code reusability and maintenance.<br/>
<br/>
The foundations of the project can be found in the "common" module. Here is were all the shared dependencies required for 
the upcoming tests will live and be implemented in some extension.<br/>
Inside the pom.xml file we are able to see: TestNG, our test runner by choice, 
Extent Reports to act as a report provider (using version 4 which still contains .html option), Hamcrest used for detailed 
assertions, Lombok for handling required boilerplate code, Jackson for binding Java classes to different file extensions, Apache 
for utilities purposes and Selenium Java client.<br/>
Since this is only a mobile testing framework for UI layer, the Selenium Java client could be placed into the "mobile" pom.xml file,
but it is kept there in case of framework growth to include a web ui module.<br/>
To keep this document brief, the main objective of this module is to implement the "reporting" and "logging" for the future tests and
configuration; Also, to provide some ground rules for the upcoming "contexts" through the interfaces, and, to generate some utilities
that will come handy, such as the "Assertion" class which will log into the reports the passing assertions and "Lazy" which will
prevent us from using more machine resources than needed during the instantiation of small page objects known as "components".<br/>
All this work is combined finally in the "AbstractTest" class which will act as the angular stone for the mobile and web-service base test class.<br/>
<br/>
The following module to be mentioned is "mobile" which has dependecies to the "common" module and "Appium" Java Client.<br/>
This last dependency will be our main tool to develop the tests for the Android Monefy application. Appium uses Selenium to send 
requests to the mobile devices. What Appium is essentially is a NodeJs Server which uses HTTP requests coming from Selenium's WebDriver;
Everytime a Appium Driver is created, a temporarily bridge is opened in the desired device to set some configuration that will enable the hearing of this requests,
after setting this initial configuration, the bridge is closed but the device remains hearing until the teardown process.<br/>
Once again, to ensure code efficiency, the project follows the Page Object Pattern in which a Screen is represented by a Java class to be instantiated to reflect on
device current screen. This pattern is taken a step further to include page objects inside page objects. These are called "components".
They will be defined as separate classes but will be present inside bigger page objects, but only instantiated when needed 
(here is when the Lazy class comes handy in combination with Java's Supplier class).<br/>
The pages have a custom condition when being instantiated which basically waits a given amount of time for the app activity to be as expected. 
By doing this we don't rely on elements presence or visibility since screen xml structure might change more frequently than an activity.<br/>
The setup process can be found in the "MobileContext" enum (created as enum to ensure only one instance of server and driver per thread).
The class is responsible for reading a "settings.yml" file in which we define our ip address, port and desired capabilities. The file is read and converted into Java classes 
for easy manipulation within the code. It is to be mentioned that the apk file path is defined here too, to be installed each time a test in run.
The process will end up being slower due to this but results will not be conditioned by previous data or failures.<br/>
After reading the file, a server will programmatically start and then an Appium Driver will be instantiated, in this case, an Android Driver.
While the driver is created, it will determined (through a value that is being passed) what device should be used. Since no real device is available,
the framework will trigger an android emulator (created beforehand through AVD) through a Runtime execution, the same emulator will quit after each test.
The result of this process will be a device with the application already installed and a server listening for requests.
As a side note, this emulator triggering process can be omitted by removing the call to the function triggerEmulator() and stopEmulator(), 
but it would need to have the emulator already running.<br/>
As mentioned before we will need a basic test class to handle all this and not declare it every single time, this class was called "AbstractMobileTest".
All our future tests will inherit from it, getting rid in the process of any configuration required.<br/>
<br/>
Finally, we have our "web-services" module. As the mobile module, it has dependencies to "common" and it adds
"RestAssured" for our HTTP requests.
This module is far more short due to the testing demanded, but, as mobile, it has the base test which was called "AbstractWebServicesTest" whoich takes care 
of all the configuration of RestAssured such as setting the base url and port.<br/>
By defining the base url, we only need to secure the endpoints in a location not to be easily manipulated (during test writing) but yet reachable to add, modify or delete future endpoints.
The solution was a simple Java class with inner static classes defining string as final to act as constants.<br/>
This module will rely heavily in Lombok for creating Java classes with given attributes that can be converted to JSON.
This classes will be placed under the "models" package.

###Running the tests
To run the tests there are several options which will be listed below:
1. Run by clicking the "play" button next to a test.
2. Run by clicking the "play" button next to the definition of a test class.
3. Run through maven command by passing the variable "groups" and he name of the desired group.  
4. Run by doing a right click and selecting "Run" into a ".xml" file placed in the "suites directory". (all.xml will run all the suites)
5. Run through maven command by passing the variable "suiteXmlFile" and the name of the desired suite.

###Reporting
After each execution, a "reports" directory will be generated with a suitable name to identify the recent execution. Please, open using browser to see step by step details of the test.


