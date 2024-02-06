FROM jboss/wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh admin root66 --silent
COPY ./com/ /opt/jboss/wildfly/modules/com/
COPY mgmt-users.properties /opt/jboss/wildfly/standalone/configuration/mgmt-users.properties
COPY standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
