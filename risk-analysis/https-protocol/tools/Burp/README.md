# Burp Suite

To get a first impression of the possibilities of Burp, visit the 
[Getting Started](https://portswigger.net/burp/documentation/desktop/getting-started) page.


## Download and Setup

First, we run Burp Suite by starting the downloaded JAR file: 
```
$ java -jar burpsuite_community_v2020.4.jar
```
Within the Proxy tab, we configure the following points:
* Intercept: **Intercept is off**
* Options: Change **port number** to **8010** and activate the **running checkbox**.
* Intercept Server Responses: check the **Intercept the responses based on the following rules** box.

Finally, go to the **HTTP history** tab and analyze the recorded HTTP messages.

## Firefox Settings
To configure the usage of an interception proxy, we go to the Firefox **Preferences**,
scroll down to **Network Settings** and select **Manual proxy configuration**.
```
Http Proxy: localhost      Port: 8010
[x] Also use this proxy for FTP and HTTPS

No proxy for:
.firefox.com,.mozilla.com,.mozilla.net,.mozilla.org
```

Note that the **proxy port** number is the same as we have used for the **Burp configuration**.

Currently requests to **localhost** will NOT be intercepted by the proxy, thus, we have to 
configure a workaround.

A possible solution is to use **about:config** which is a feature of Mozilla Firefox that allows 
the users to change and manipulate the preferences on Firefox:
```
URL: about:config
network.proxy.allow_hijacking_localhost	true
```

## Burp Tutorials

**Introduction**
* [Youtbe (PortSwigger): How to intercept HTTP requests and responses using Burp Suite](https://youtu.be/ouDe5sJ_uC8)
* [Youtbe (PortSwigger): How to resend individual requests with Burp Repeater](https://youtu.be/_Wifm2g9ugg)

**Vulnerability Scanning**


## References
* [Burp Suite Community Edition - Download](https://portswigger.net/burp/communitydownload)
* [Burp Video Tutorials](https://portswigger.net/burp/documentation/desktop/tutorials)

*Egon Teiniker, 2019-2021, GPL v3.0*
