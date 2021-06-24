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

## NAT-Network
To combine the Fedora VM with another virtual machine, e.g. **Kali Linux**, both
VMs should be configured in the following way:
```
File / Preferences / Network => Nat-Networks [+] Add
Enter a name: MyNetwork
```

In each VM go to the network settings and configure:
```
Adapter 1: NAT-Network
Name: MyNetwork
```
Finally, use **ifconfig** to get the IP address of each VM.
```
$ ifconfig
eth0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
inet 10.0.2.15  netmask 255.255.255.0  broadcast 10.0.2.255
inet6 fe80::3870:971d:ad63:e691  prefixlen 64  scopeid 0x20<link>
...
```

## Fedora Firewall
In order to be able to address the web applications via the NAT network from Kali Linux (own VM), we have to 
enable port 8080.

```
Fedora:
    $ sudo firewall-cmd --zone=public --add-port=8080/tcp --permanent
    $ sudo firewall-cmd --zone=public --add-port=8443/tcp --permanent
    $ sudo firewall-cmd --reload
    $ sudo firewall-cmd --zone=public --list-ports
```

