# National Institute of Security Technology (NIST) Password Policy Recommendations

The **NIST Special Publication (SP)** 800-63-3, Digital Identity Guidelines
introduces a new protocol designed to improve password security.

This new Digital Identity guideline drastically changes the recommendations
for password best practices, suggesting memorized secrets should be random,
and therefore impractical for an attacker to guess.

Since a memorized secret (something you know) can be stolen, it also requires
multi-factor authentication as a second layer of security. Multi-Factor
Authentication requires an authenticator app to be downloaded to your smartphone,
(something you have) to verify you are who you say you are instead of accepting
the password alone.

The new guidelines demonstrate an understanding of the human element, and
introduces a policy that is more realistic for users to follow:

* Require **Multi-factor Authentication**

* **Password Length** should to be a minimum of 8 characters or longer

* All **special characters** (including space) should be allowed, but not required

* **Eliminate knowledge-based authentication** (e.g. what is your mother's maiden name?)

* **Avoid Personal Information** including name, important dates, pets, etc.

* Compare the prospective secrets against a list that contains values known to be **commonly-used, expected, or compromised**. For example, the list MAY include, but is not limited to:
    * Passwords obtained from previous breach corpuses
    * Dictionary words
    * Repetitive or sequential characters (e.g. ‘aaaaaa’, ‘1234abcd’)
    * Context-specific words, such as the name of the service, the username, and derivatives thereof

* **Eliminate Mandatory Password Changes** unless there is evidence of compromise of the password

* Use **approved encryption and an authenticated protected channel** when requesting memorized secrets

* **Limit the number of failed login attempts**

* Enable copy and paste functionality in password fields to promote the use of **Password Managers**

## References
* [Authenticator and Verifier Requirements](https://pages.nist.gov/800-63-3/sp800-63b.html#sec5)

* [Password Policy Best Practices](https://www.intellisuite.com/blog/2019-password-policy-best-practices)

* [Common-Credentials](https://github.com/danielmiessler/SecLists/tree/master/Passwords/Common-Credentials)


*Egon Teiniker, 2019-2022, GPL v3.0*