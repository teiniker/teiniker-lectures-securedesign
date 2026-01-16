# National Institute of Security Technology (NIST) Password Policy Recommendations

The National Institute of Standards and Technology (NIST) has provided refreshed 
guidance designed to simplify password management while improving security. 
These updates, outlined in NIST Special Publication 800-63B, reflect a 
fundamental shift from outdated, counterproductive practices to user-friendly, 
research-backed solutions.


**NIST's 2025 password guidelines** (SP 800-63B Rev. 4) shift focus from complexity 
to length and context, recommending 15+ characters, banning mandatory resets 
(only on compromise), disallowing security questions, and rejecting complexity 
rules (uppercase, numbers, symbols). 

Key Guidelines: 

* **Length**: Minimum 15 characters (or 8 if Multi-Factor Authentication (MFA) used), max 64+.

* **Complexity**: No mix-of-character rules (no mandatory uppercase, numbers, symbols).

* **Password Resets**: No periodic resets; only after a breach.

* **Security Questions**: Eliminate them (e.g., "First Pet's Name?").

* **Blocklists**: Block passwords found in breached lists (e.g., "Password123").

* **Multi-Factor Authentication (MFA)**: Strongly recommended, often pairing with 
    passwordless options or password managers.

* **Password Managers**: Recommended to generate and store long, unique passphrases.

* **Acceptance**: Accept all printable ASCII, spaces, and Unicode characters. 



## References
* [A complete guide to the new 2025 NIST password guidelines](https://proton.me/blog/nist-password-guidelines)

* [Password Policy Best Practices](https://www.intellisuite.com/blog/2019-password-policy-best-practices)

* [Common-Credentials](https://github.com/danielmiessler/SecLists/tree/master/Passwords/Common-Credentials)


*Egon Teiniker, 2017-2025, GPL v3.0*