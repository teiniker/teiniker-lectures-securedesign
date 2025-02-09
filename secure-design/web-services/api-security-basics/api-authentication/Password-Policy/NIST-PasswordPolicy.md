# National Institute of Security Technology (NIST) Password Policy Recommendations

The National Institute of Standards and Technology (NIST) has provided refreshed guidance designed to simplify password management while improving security. These updates, outlined in NIST Special Publication 800-63B, reflect a fundamental shift from outdated, counterproductive practices to user-friendly, research-backed solutions.

The guidelines are evolving again with the release of the Second Public Draft of NIST SP 800-63B-4 in August 2024. While primarily targeted at federal agencies, these standards have become the de facto benchmark for password security thanks to their comprehensive research, rigorous review process, and universal applicability.

The new guidelines demonstrate an understanding of the human element, and
introduces a policy that is more realistic for users to follow:

**What to Do**:

* **Password Length**: A password should be at least 8 characters 
    long and preferably 15 characters. This is because passwords 
    can be cracked, and the longer the password, the longer it 
    takes to crack the code.

* **Allow Flexibility in Length**: You can go up to 64 characters 
    for passwords thus providing users with the opportunity to 
    create a more complex password.

* **Character Options**: More password entropy, meaning the passwords 
    should also be able to accept space and all printable ASCII 
    characters.

* **Unicode Support**: Passwords should also allow Unicode characters, 
    which helps users include characters from different languages. 
    Each Unicode character counts as one towards the total password 
    length.

**What to Stop**:

* **Arbitrary Complexity Requirements**: Eliminate unproductive, 
    rigid guidelines that currently include having to type both lower 
    and upper case, numbers, and symbols. Most of the time they don’t 
    offer protection and are incredibly annoying to the users.

* **Periodic Password Resets**: Don’t force users to change passwords 
    at regular intervals unless there is evidence of a security breach. 
    Requiring constant resets can lead to weaker passwords over time.

* **Password Hints**: Stop allowing users to store password hints that 
    could potentially be exposed to unauthorized users.

* **Security Questions**: Stop relying on outdated security questions 
    like “What was the name of your first pet?” These are easy for 
    attackers to guess or find out through social engineering.

* **Truncating Passwords**: Always verify the entire password, not 
    just part of it. This ensures that users’ full password strength 
    is considered during authentication.

## References
* [NIST Special Publication 800-63B](https://nvlpubs.nist.gov/nistpubs/specialpublications/nist.sp.800-63b.pdf)

* [NIST Special Publication NIST SP 800-63B-4](https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-63B-4.2pd.pdf)

* [Password Policy Best Practices](https://www.intellisuite.com/blog/2019-password-policy-best-practices)

* [Common-Credentials](https://github.com/danielmiessler/SecLists/tree/master/Passwords/Common-Credentials)


*Egon Teiniker, 2017-2025, GPL v3.0*