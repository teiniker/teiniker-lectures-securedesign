# Secure Design Principles

Saltzer and Schroeder describe principles for the design and implementation 
of security mechanisms. These principles are build on the ideas of 
**simplicity and restriction**.

Good guiding principles tend to improve the security outlook even in 
the face of **unknown future attacks**.

The goal of these principles is to identify and highlight the most important 
objectives we should keep in mind when designing and building a secure system. 
Following these principles should help us to avoid lots of the common 
security problems.

## Secure the Weakest Link

> **Security is a chain** – and just as a chain is only as strong as the 
> weakest link, a system is only as secure as its weakest component.

Attackers will attack the weakest parts of a software system because they 
are the parts most likely to be easily broken.

Make sure to consider the weakest link in a system and ensure that it is 
secure enough!


## Practice Defense in Depth
> The idea is to **manage risk with diverse defensive strategies**, so that 
> if one layer of defense turns out to be inadequate, another layer of defense 
> will hopefully prevent a full breach.

It is pretty obvious that the sum of all these defenses result in a far more 
effective security system than any one defense alone.


## Fail Securely
> Any sufficient complex system will have failure modes. Failure is unavoidable. 
> What is avoidable are security problems related to failure. The problem is that 
> when many systems fail in any way, they exhibit insecure behavior.

In such systems, attackers only need to cause the right kind of failure to happen. 
Then they can go down.


## Grant Least Privilege 
> The principle of least privilege states that only the minimum access necessary 
> to perform an operation should be granted, and that access should be granted 
> only for the minimum amount of time necessary.

When we give out access to parts of a system, there is always some risk that 
the privileges associated with that access will be abused.

Laziness often works against the principles of least privilege. Don’t let this 
happen in your code!


## Compartmentalize

> The basic idea behind compartmentalization is to minimize the amount of damage 
> that can be done to a system by breaking up the system into units while still 
> isolating code that has security privileges.

The compartmentalize principle must be used in moderation. If we simple segregate 
each little bit of functionality, then your system will become completely 
unmanageable.


## Keep it Simple

> The KISS mantra: Keep it Simple, Stupid!

Complexity increase the risk of problems:
* Complex design is never easy to understand, and it is therefore more likely 
    to include subtle problems will be missed during analysis.
* Complex code tends to be harder to maintain as well.
* Complex software tends to be far more buggy.


## Promote Privacy
> Try not to do anything that may compromise the privacy of the user. Be as 
> careful as possible in protecting any personal information a user does 
> give your program.

One of the things privacy most often trades off against is usability.

Attackers also tend to launch attacks based on information easy collected 
from a target system. There is no reason for give out any more information 
than necessary.

Promote privacy for your users, for your systems, and for your code!


## Remember That Hiding Secrets is Hard
> Users don’t want their personal data leaked. It turns out that storing 
> secrets to be fare more difficult to meet than users may suspect. 
> Especially when it comes to secrets stored in code.

Many people make an implicit assumption that secrets in a binary are likely 
to stay secret, because binaries are complex.

Unfortunately, some attackers are surprisingly good at reverse engineering 
binaries using decompilers, disassemblers, and any number of analysis tools – 
**binary is just a language!**


## Be Reluctant to Trust
> Assume that the environment where your system operates is hostile. 
> Servers should not trust clients, and vice versa, because both clients 
> and servers get hacked.

Don’t let just anyone call your API, and don’t let just anyone gain access 
to your secrets!

Trusted programs should not invoke untrusted programs. It is also good to 
be careful when determining whether a program should be trusted or not.

## Use Your Community Resources

> Repeat use without failure promotes trust.
> Public review does as well.

For example, in the cryptography field it is considered a bad idea to trust 
any algorithm that isn’t public knowledge and hasn’t been widely reviewed.

There is no real solid proof of the security of most cryptographic algorithm
implementations. They are trusted only when a large enough number of smart 
people have spent a lot of time trying to break them, and all fail to make 
substantial progress. 


## References

* John Viega, Gary McGraw. **Building Secure Software**: Addison-Wesley, 2002
* Matt Bishop. **Computer Security**. Addison-Wesley, 2003

*Egon Teiniker, 2017-2024, GPL v3.0*