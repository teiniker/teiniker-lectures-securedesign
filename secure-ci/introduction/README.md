# Secure Continuous Integration 

> **Continuous Integration (CI)** is a software development practice where 
> members of a team integrate their work frequently, usually each person 
> integrates at least daily – leading to multiple integrations per day.

In traditional development, teams worked on features in isolation for weeks 
or months, then merged everything at the end (**integration hell**).
CI solves this by encouraging **small, frequent integrations**, making 
problems easier to find and fix.

![CD Process](figures/CI-Process.png)


## Security Aspects in the Pre-Commit Stage

In the pre-commit stage, developers create the design, the implementation,
and test cases in **cross functional teams**:

Each developer works on his own computer and checks the changes into the code 
repository at least **once a day**.


### Risk Analysis

It is important to get involved in **up-front reviews of design and code** 
to identify risks and mistakes, thus, security problems can be caught
and corrected early.

We start with a high-level risk assessment for new systems and services:

* **Data classification**: What data is sensitive, restricted or confidential, 
    and need to be protected?

* **Focus on platform, language, and framework risks**: Is the team using 
    well-understood tools, or something new?

As incremental changes are made, **iterative threat modeling** should be 
done by the team by asking a few basic questions:

* **Are we changing the attack surface of the system?**

    Adding a new API or opening a new port, adding a new user type or role 
    or changing a data store, calling out a new service.

* **Are we changing the technology stack?**

    Adding or significantly upgrading a framework or important library, 
    or a language, or a runtime component.

* **Are we changing security controls?**

    Authentication or session management code, cryptographic algorithms, 
    access control code or rules, audit logic. 


### Peer Code Reviews

Peer code reviews play an important part in disciplined Agile practices 
to make code more maintainable and consistent.
Code reviews are also used to find bugs, reinforce defensive programming practices, 
and to ensure that developers use secure coding libraries and frameworks properly.

An effective code review process should:

* Ensure that **high-risk code** (security features and security libraries, 
    networkfacing APIs, application logic that handle sensitive data, etc.) is 
    reviewed by experienced developers and a security expert.

* Provide developers with **training on secure coding** so that they know what to 
    look for.

* Code reviews still need to be done for maintainability, clarity, and consistency. 
    But they also need to **focus on correctness and on defensive coding**: Data 
    validation and API contracts, error and exception handling, logging.

* Reviewers should also ensure that the code takes advantage of **security
    features in frameworks** and functions provided in **security libraries**. 

* Watch out for **hardcoded secrets**, **self made cryptography**, and 
    **suspect code** (code that is unnecessarily confusing).


### Static Code Analysis

Offer developers a self-service scanning (static code analysis) 
instead of running a centralized Scanning Factory.

A developer self-service model provides developers with **immediate feedback** 
on coding mistakes and security risks in their IDE as they are making code changes.

**Plugins are available for most IDEs**, but we cannot enforce developers to use 
them. Therefore, we still need to **scan code in the CD pipeline as well**.


### Security Tests

In teams that follow **Test-Driven Development (TDD)** a developer will 
write a test, run the test to prove that the test fails, write the code, 
and then run the test again to prove that the code works as intended.

Guidelines for writing effective unit tests for security:

* **Write comprehensive unit tests for security features and services**: 
    Insist on high levels of test coverage for: authentication, access control,
    auditing & logging, data validation, and cryptographic algorithms and
    wrapper logic.

* **Make sure that complex code is carefully unit tested**: 
    Complex logic tends to have more coding bugs. Use these unit tests 
    to safely refactor and simplify this code. 

* **Encourage developers to include negative testing, especially in 
    high-risk code**: Use abuse cases to come up with meaningful negative 
    test cases and **attack scenarios**.

* **Follow Test-First Development when fixing a bug or security 
    vulnerability**: Write a test to reproduce the bug, and make sure 
    that the code fails. Then improve the code and run the test again 
    to make sure that the bug is fixed.


## Security Aspects in the Commit Stage

In the commit stage, the **Continuous Integration (CI) server** detects c
hanges in the repository and starts the build script. At the end, a release 
candidate (RC) is generated:

All tasks are performed **automatically** and provide **feedback within minutes**.

This stage is about adding **security checks** into the team’s **automated 
build pipeline**. 

The focus here is on **unit testing** and **incremental static code analysis** 
to catch mistakes quickly (in a few minutes at most):

*  If a **test or check fails**, the CD pipeline will fail.

* If all **tests and checks pass**, sign the build candidate and store it in a 
    secure artifact repository to ensure that it cannot be tampered with.


### Static Code Analysis 

The focus of Static Code Analysis is not about finding and reviewing all of
the potential security vulnerabilities existing in the application.

It is about **identifying any new vulnerabilities that were introduced in the 
latest set of changes** and getting this information back to developers as quick 
as possible so that they can fix vulnerabilities right away.

Because **static code analysis takes time**, we may try to schedule scanning to 
run in **parallel with automated testing**, in a distributed build and test grid.

#### Deep Scanning vs. Incremental Scanning:

* **Full scans** on a large code base can take several hours, which will not 
    fit into the time windows for Continuous Integration.

* While **incrementing scanning** may miss some problems, the risk should be 
    small in incremental development and delivery, because the amount
    of code changed at any point in time will usually be small.

Deeper and more complete scanning of the code base should also be done to check 
for more complex inter-procedural bugs. This could be scheduled overnight or on demand.

#### Minimizing False Positives:

* **Review and tune scanners and rules up front** to identify which ones
    provide high confidence results. Don’t waste the developer team’s time
    investigating and rejecting findings that are not important.

* **Turn off noisy, low confidence checks** from the pipeline.

*  **Focus on high-risk, high-confidence checks** that can be run quickly.

* **Fail the CI pipeline** if any of these checks fail.

#### Different Tools Find Different Problems: 

Static analysis engines look for different kinds of problems:

* Tools that check **coding styles and coding best practice** 
    (like Checkstyle, PMD, and many lint tools).
    They help code easier to understand and change which leads to code with 
    fewer bugs and vulnerabilities.

* Tools that check for **common bug patterns and mistakes** (like Spotbugs). 
    They make code more reliable and will find some security vulnerabilities.

* Tools that focus specifically on finding **security vulnerabilities**
    (like FindSecurityBugs). 

#### Analyzing Vulnerable Dependencies:

* Serious **vulnerabilities can be inherited from libraries and frameworks**. 
    Developers may download components with known vulnerabilities or fail 
    to update to new and safer versions.

* Static analysis tools (like OWASP Dependency Check) have been developed 
    to **identify dependencies** and compare the signature of these libraries 
    against public vulnerability databases to **identify out-of-date components**, 
    and **components with known problems**.

* These tools can be **integrated into the CI pipeline**. If a serious
    vulnerability is found in any of these libraries, the CD pipeline will 
    fail.

#### Fewer, Better Suppliers:

One way to control security risks is to try to standardize on fewer, better
suppliers following **Toyota’s lean supply chain management principles**:

* Review open-source components and ensure that they are up to date.

* Track and alert when new components are checked in.

* Encourage teams to standardize on common frameworks.

But beware, we will run into **resistance from developers**, especially in 
microservices environments, where the engineering culture and approach 
encourages developers to use the best tool for the job.

### Artifact Repositories:

The result of Continuous Integration, if the build is successful and the 
tests pass) is a **release candidate**.

We **store the binaries** build in this stage and use them in all subsequent 
steps of the CD pipeline - **Don’t rebuild the code in later stages**.

* An **artifact repository manager** (like Nexus) holds the binaries and other 
    files which need to be deployed.

* Repository managers help to **store, track and manage binaries** 
    and other artifacts, including shared, common dependencies. 
    They can proxy remote repositories (like Maven Central or Docker Hub), 
    caching artifacts to minimize download time.


## References

* Martin Fowler. **Continuous Integration**. http://martinfowler.com/articles

* Jez Humble, Davis Farley. **Continuous Delivery**. Addison-Wesley, 2010

* Davis Farley. **Continuous Delivery Pipelines**. Independently published, 2021

*Egon Teiniker, 2017-2026, GPL v3.0*