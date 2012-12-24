Theme Builder for ExtJS framework

----------------------------------------------------------------------------
| LEGAL                                                                    |
----------------------------------------------------------------------------

This software is licensed under the LGPL v3.

Copyright(c) 2009-2011 Sergey Chentsov (sergchentsov@gmail.com) All
rights reserved.
This material cannot be distributed without written
permission from Sergey Chentsov. Permission to use, copy, and modify
this material for internal use is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.
SERGEY CHENTSOV MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
EVENT SHALL SERGEY CHENTSOV BE LIABLE FOR
ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
DAMAGES RELATING TO THE SOFTWARE.


----------------------------------------------------------------------------
| INTRODUCTION                                                             |
----------------------------------------------------------------------------

Welcome to Theme Builder for ExtJS!

The installation and configuration of Theme Builder for ExtJS is intended to be as
simple as possible.

PLEASE READ ALL INSTRUCTIONS TO AVOID ANY PROBLEMS.

If you are going to be developing Theme Builder for ExtJS, please send letter to
sergchentsov@gmail.com and ask committers permission for Theme Builder Project
on http://code.google.com/p/extthemebuilder/.
you can also find information about installing, configuring, and using
Theme Builder for ExtJS there.

----------------------------------------------------------------------------
| REQUIREMENTS                                                             |
----------------------------------------------------------------------------

You will need to have the following software installed in order to compile:

  Sun JAVA SE DEVELOPMENT KIT (JDK), VERSION 6
  http://www.oracle.com/technetwork/java/index.html

  Apache Ant 2.0
  http://ant.apache.org

  Apache Tomcat 6.0 JavaSE 6
  http://jakarta.apache.org/tomcat

You will need the required Theme Builder for ExtJS 3rd party libraries, these are
distributed separately and include:

ExtJS v3.3
Ext.ux.form.ColorPickerField
Ext.ux.ColorPicker
Ext.ux.form.SuperBoxSelect

ant-1.6.5.jar
commons-beanutils.jar
commons-codec-1.3.jar
commons-collections.jar
commons-io.jar
commons-logging.jar
jstl.jar
spring-webmvc.jar
spring.jar
standard.jar
xml-apis-ext.jar

  See below for the specific license information.


----------------------------------------------------------------------------
| SYSTEM SETTINGS                                                          |
----------------------------------------------------------------------------

* You must have the JAVA_HOME environment variable properly set to the
location of your Java SDK installation directory. For example on Windows Vista

  JAVA_HOME=c:\java\jdk6

* You must have Ant installed and ANT_HOME defined in your environment as
well as ANT_HOME/bin in your PATH.  You must also increase the JVM memory for
Ant by defining ANT_OPTS.

  ANT_HOME=c:\java\ant20

* You must have Tomcat installed and CATALINA_HOME defined in your envionment
which points to the tomcat directory.  Also specify the environment variables.

  CATALINA_HOME=C:\java\apache-tomcat-6.0
  JAVA_OPTS=%JAVA_OPTS% -server -Xms256m -Xmx512m

----------------------------------------------------------------------------
| BUILD PROCESS                                                            |
----------------------------------------------------------------------------

Setup in  build.properties  file path to your server 
appserver.home=C:/java/apache-tomcat-6.0.18_2

Begin by executing "ant" from the command line.  You will be presented with
the available ant commands.

To verify that you have all settings in place, and to attempt to create
a .war from the source code, execute:

  ant deploywar

A new file will be created in the current directory, which must be edited
with your custom settings.

This time you should end up with a .war file.


----------------------------------------------------------------------------
| ABOUT THIS SOFTWARE                                                      |
----------------------------------------------------------------------------

Theme Builder for ExtJS licenses libraries and code from the following projects, some
are proprietary in which Sergey Chentsov has been granted a license
to redistribute, some are Open Source and are used according to the project
license:

/-Library name------------/----License---/
/ ant-1.6.5.jar           / Apache 2.0   /
/ commons-beanutils.jar   / Apache 2.0   /
/ commons-codec-1.3.jar   / Apache 2.0   /
/ commons-collections.jar / Apache 2.0   /
/ commons-io.jar          / Apache 2.0   /
/ commons-logging.jar     / Apache 2.0   /
/ jstl.jar                / Apache 2.0   /
/ spring-webmvc.jar       / Apache 2.0   /
/ spring.jar              / Apache 2.0   /
/ standard.jar            / Apache 2.0   /
/ xml-apis-ext.jar        / W3C SOFTWARE /

Client side components or libraries usage
/-Components or library name---/--License---/
/ ExtJS v3.3                   / GNU GPL v3 /
/ Ext.ux.form.ColorPickerField / BSD        /
/ Ext.ux.ColorPicker           / LGPLv3     /
/ Ext.ux.form.SuperBoxSelect   / TBA        /

*** TBA (To be announced)


