# Password Cracking With John the Ripper

John the Ripper (often just John) is a password-cracking and auditing tool. 
It’s widely used by security professionals to **test password strength** or **recover passwords** 
you’re authorized to access.

## Setup
```bash
GitHub: John the Ripper
https://github.com/openwall/john

$ sudo apt update
$ sudo apt install build-essential git libssl-dev zlib1g-dev libgomp1

$ git clone https://github.com/openwall/john.git
$ cd john/src
$ ./configure && make

$ ./run/john --help
```

_Tip_: Remove the `john.pot` file before you run examples.

```
$ rm ./run/john.pot
```

## How John Works

John doesn’t "reverse" a password. Instead, it guesses passwords, hashes each guess the same way 
the system does, and compares the result to the stored hash. If the hashes match, the password 
is found.

The **cracking workflow**:

* **Hash ingestion**: We give John one or more password hashes (from a file, database dump, etc.).

* **Hash identification**: John figures out which algorithm was used (e.g., MD5, SHA-1, bcrypt, NTLM).
	This matters because each algorithm is handled differently.

* **Password guessing strategies**: John tries passwords using multiple methods:
	- **Dictionary attacks**: Real words from wordlists
	- **Rules-based mutations**: Change `password` to `P@ssw0rd!`
	- **Brute force / incremental**: Tries all combinations (slow but thorough)
  	- **Hybrid attacks**: Dictionary + brute force patterns
	- **Mask attacks**: e.g., "8 characters, ends with numbers"

* **Hash comparison**: Each guess is hashed using the same algorithm + salt and compared to the target hash.

* **Success or continue**: 
	- Match found: password recovered
	- No match: keep guessing until exhausted or stopped


## Example: Cracking SHA-256 Hashes

```bash
cat sha-256-passwords.txt 
d482ba4b7d3218f3e841038c407ed1f94e9846a4dd68e56bab7718903962aa98
5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8

$ ./run/john --format=raw-sha256 sha-256-passwords.txt 
```

## Example: Cracking Linux Passwords
```
$ cat debian13-passwords.txt
student:$y$j9T$dCDbCiiBA.WjzbkPqEP4t0$0WQv9GiN57wAKXuWyrvGTD.okhL2m/w1CjZ7Gob.XxB:20326:0:99999:7:::
root:$y$j9T$6F5vcCT6edWN2NSRYO4bM/$dBgAtXLDeQPYRFZjl46JevPpjaGrs4fClTW4KybUER.:20326:0:99999:7:::

$ ./run/john cat debian13-passwords.txt
```

## Command Line Parameters

John’s CLI flags are falling into six big categories.


### Input and Hash Handling

These options tell John what data it’s working on and how to interpret it.

* **--format=FORMAT**
	- Specifies **which hash algorithm** John should use (e.g., NTLM, bcrypt, SHA variants).
	- Needed when auto-detection is ambiguous or slow.
	- Choosing the wrong format = zero results.

* **--encoding=ENC**
	- Character encoding for passwords (UTF-8, ISO-8859-1, etc.).
	- Important for non-ASCII passwords.

* **--field-separator=CHAR**
	- Defines how fields are separated in the input file.
	- Useful when hashes are embedded in custom data formats.

### Cracking Modes (How John Guesses Passwords)

These are the most important conceptual switches.

* **--single**
	- Uses user-related info (usernames, names, metadata).
	- Fast, targeted, surprisingly effective on weak passwords.  

* **--wordlist[=FILE]**
	- Dictionary-based guessing.
	- Often combined with rules.

* **--incremental[=MODE]**
	- Brute-force style guessing.
	- Tries all combinations according to a charset and length rules.

* **--mask=MASK**
	- Pattern-based guessing.
	- Example concept: "8 chars, ends in digits".
	- John only runs one cracking mode at a time unless explicitly chained.

### Rules and Mutations

These options control how guesses are modified.

* **--rules**
	- Applies transformation rules to wordlists.
	- Example concepts:
		- Capitalization
		- Character substitution
		- Appending numbers/symbols

* **--rules=NAME**
	- Use a specific ruleset from the config file.
	- Rules are why John cracks passwords that "aren’t in the wordlist".

### Performance & hardware control

These flags tune speed vs resource usage.

* **--fork=N**
	- Runs John in parallel using multiple CPU processes.
	- Mostly for CPU-only systems.

* **--session=NAME**
	- Names the cracking session.
	- Allows pausing and resuming later.

* **--status**
	- Shows current progress without stopping the session.

### Session Control and Recovery

These options make John practical for long runs.

* **--restore[=SESSION]**
	- Resumes a paused or interrupted session.

* **--pause**
	- Safely pauses the current run.

* **--show**
	- Displays already cracked passwords from the input hashes.
	- Does not perform cracking.

* **--pot=FILE**
	- Specifies where cracked passwords are stored.

### Output and Reporting

These control what John shows you.

* **--verbose**
	- More detailed output.
	- Useful for debugging formats and modes.

* **--stdout**
	- Prints candidate passwords instead of cracking.
	- Often used for testing rules and masks.
	
* **--log=FILE**
	- Writes session output to a log file.




## References
* [John The Ripper Hash Formats](https://pentestmonkey.net/cheat-sheet/john-the-ripper-hash-formats)

* [Youtube: Password Cracking With John The Ripper](https://youtu.be/XjVYl1Ts6XI)

* [John the Ripper Password Cracker](https://www.openwall.com/john/) 
	
* [10k Most Common Passwords](https://github.com/danielmiessler/SecLists/blob/master/Passwords/Common-Credentials/10k-most-common.txt)	

* [crunch - Wordlist Generator](https://sourceforge.net/p/crunch-wordlist/code/ci/master/tree/)

  
*Egon Teiniker, 2017-2026, GPL v3.0*	
