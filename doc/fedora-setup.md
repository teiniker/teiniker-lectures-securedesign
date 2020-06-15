# Fedora Settings

Here, some additional settings are described which are needed by some examples but
were not made in the original version of the Virtual Lab.

## Hostname
In order to be able to use the Interception Proxy via Firefox for local web applications, we have to set the host name:
```
    $ sudo hostnamectl set-hostname lab.se.org
    $ hostname
    lab.se.org
```
Also, we have to configure Wildfly to accept requests from other sources than 127.0.0.1.
In the **standalone.xml** file, we change the **interfaces** bind addresses from 127.0.0.1 to 0.0.0.0:

```
    <interfaces>
        <interface name="management">
            <inet-address value="${jboss.bind.address.management:0.0.0.0}"/>
        </interface>
        <interface name="public">
            <inet-address value="${jboss.bind.address:0.0.0.0}"/>
        </interface>
    </interfaces>
```

## Firewall
In order to be able to address the web applications via the NAT network from Kali Linux (own VM), we have to 
enable port 8080.

```
Fedora:
    $ sudo firewall-cmd --zone=public --add-port=8080/tcp --permanent
    $ sudo firewall-cmd --zone=public --add-port=8443/tcp --permanent
    $ sudo firewall-cmd --reload
    $ sudo firewall-cmd --zone=public --list-ports
```

