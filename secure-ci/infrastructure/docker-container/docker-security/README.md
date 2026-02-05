# Securing Docker

## Introduction
Docker containers provide additional protection over a native runtime by **isolating applications** and by **reducing the 
attack surface** of the application.
The complete runtime environment (code and configuration) can be inspected, copied and deployed.
We know exactly what is running and can set it up again as needed for testing.

Docker also makes it easier and faster to push out **patches**.

And Docker increases **consistency in configuration**, which makes hardening, auditing, and testing easier.

Docker allows us to build **lean images** that only contain what we put into it.
However, many containers are build on base OS images which are quite fat. We have to work from **thin base images**.

## Container Security Issues

There are a number of **security issues with Docker containers** that we need to manage:
* **Docker Daemon Attack Surface**:
    While using containers properly can reduce the system's attack surface, the Docker deamonitself exposes its own 
    attack surface.
    Anyone with access to the Docker deamon control socket or API effectively has **root access to the container**.
    Therefore:
    * Only trusted users should be allowed to control a Docker deamon.
    * Use traditional UNIX permission checks to limit access to the control socket
    * If you run Docker on a server, it is recommended to run exclusively Docker on a server and to move all other 
    services within containers controlled by Docker.

* **Image Poisoning**:
    Docker makes it easy for developers to **download and work with pre-build images**.
    This makes it easy for them to introduce vulnerabilities by accident.
    The risks of using pre-built Docker images are similar to the risk of developers using open source software libraries and other compnents.
     
    While the Docker company is taking active steps to improve the security of images hosted in **Docker Hub**, we need to
    understand and manage the risk of building on public images.
    We need to check images downloaded from repositories:
    * Verify the **provenance of images**.
    * Ensuring that the image has been **scanned or reviewed for vulnerabilities**.
    * **Testing and reviewingimages before allowing them to be checked in our own registry**  - this is a natural gate 
    point for adding security checks.
    * Use [Official Images on Docker Hub](https://docs.docker.com/docker-hub/official_images/) whenever possible.
    * Use **automated tools** to check images for known vulnerabilities: [Ancore](https://github.com/anchore/anchore-engine), Banyan Ops Collector, CoreOS Clair, Dagda, etc.  
         
* **Limit Size of Images**:
    Many standard **images are fat** by default (in particular base OS images).
    They contain packages that we don't need and won't use. Cutting these packages out will:
    * Reduce the disk and memory footprint of the container. 
    * Shorten container load time.
    * Reduce the attack surface of the runtime container 
    
    We can reduce this problem by:
    * **Using Minimal Base Images**: [Alpine](https://hub.docker.com/_/alpine), [Windows Nano Server](https://hub.docker.com/_/microsoft-windows-nanoserver)  
    * **Building Images from Scratch**: Docker provides an **empty default image** called [scratch](https://hub.docker.com/_/scratch) which can be use
        to build up base images or other minimalist images.    
    * **Using Tools to Strip Down Standard Images**: There are some free tools available which can be used to strip down standard images: 
        [Strip Docker Image](https://github.com/mvanholsteijn/strip-docker-image), [Docker Slim](https://github.com/docker-slim/docker-slim)
    * **Microcontainer Images**: [Iron.io](https://blog.iron.io/microcontainers-tiny-portable-containers/) 
        has created a set of stripped-down images for major programming languages, which contain only the OS libraries and
        language dependencies required to run an application.
        These images are all built up from the Docker scratch image.
    
* **Managing Secrets**:
    Docker did not originally include any support for managing secrets.
    As a result, people hacked their own solutions:
    * **Passing secrets in environment variables**: Environment variables are discouraged because they are acessible by any
        process in the container, thus easily leaked.
    * **Build-time environment variables**: The Docker build-time environment variables where not designed to handle secrets.    
    
    It is recommended to use a **general-purpose secret keeper** such as 
    [Keywhiz](https://square.github.io/keywhiz/), Vault or Sneaker to handle secrets.
    
* **Lightweight Isolation**:
    It is important to understand that container isolation is **not as strong as VM isolation**.
    We should assume that it is possible to break out of a container, therefore:
    * **Make sure that you don't mix tiers on the same pysical box**: Public network-facing applications should not be
        physically isolated from business logic services and database services.
    * **Isolate containers in separate VMs or on separate physical hosts**: Some firms actually choose to implement only 
        one container per VM, to provide two layers of containment and isolation. 
        This improves security at the cost of hardware.    
    
* **User Namespace**:
    Docker implements five namespaces in Linux: PID process, NET netwirk interfaces, MNT filesystem mount points, 
    UTS kernel and version identifiers, IPC access to interprocess communication resources.
    Other important namespaces are outside of the container, including all devices and filesystems under /sys, and user 
    namespaces.
    **Users are not fully namespaced in Docker**. If a user or application has root access within the container, they could 
    compromise the underlying OS.
    
    It is possible to remap a user id inside the container to a different user id outside of the container through a 
    runtime option: [Isolate containers with a user namespace](https://docs.docker.com/engine/security/userns-remap/).
    
## References
* [Docker Homepage](https://www.docker.com/)

* [Docker Security](https://github.com/docker/labs/blob/master/security/README.md)

* **YouTube: Docker Security Essentials**:  
    * [How To Secure Docker Containers](https://youtu.be/KINjI1tlo2w)
    * [Auditing Docker Security](https://youtu.be/mQkVB6KMHCg)
    * [Securing The Docker Host](https://youtu.be/egqSNqNISz0)
    * [Securing The Docker Daemon](https://youtu.be/70QOBVwLyC0)
	* [How To Secure & Harden Docker Containers](https://youtu.be/CQLtT_qeB40)
	

*Egon Teiniker, 2021-2022, GPL v3.0*
