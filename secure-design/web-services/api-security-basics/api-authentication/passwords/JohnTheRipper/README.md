# Password Cracking with John the Ripper

John the Ripper is an Open Source password security auditing and password recovery tool available for many 
operating systems.

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

## Cracking SHA-256 Hashes

```bash
cat sha-256-passwords.txt 
d482ba4b7d3218f3e841038c407ed1f94e9846a4dd68e56bab7718903962aa98
5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8

$ ./run/john --format=raw-sha256 sha-256-passwords.txt 
```

## Cracking Linux Passwords
```
$ cat debian13-passwords.txt
student:$y$j9T$dCDbCiiBA.WjzbkPqEP4t0$0WQv9GiN57wAKXuWyrvGTD.okhL2m/w1CjZ7Gob.XxB:20326:0:99999:7:::
root:$y$j9T$6F5vcCT6edWN2NSRYO4bM/$dBgAtXLDeQPYRFZjl46JevPpjaGrs4fClTW4KybUER.:20326:0:99999:7:::

$ ./run/john cat debian13-passwords.txt
```

## References
* [John The Ripper Hash Formats](https://pentestmonkey.net/cheat-sheet/john-the-ripper-hash-formats)

* [Youtube: Password Cracking With John The Ripper](https://youtu.be/XjVYl1Ts6XI)

* [John the Ripper Password Cracker](https://www.openwall.com/john/) 
	
* [10k Most Common Passwords](https://github.com/danielmiessler/SecLists/blob/master/Passwords/Common-Credentials/10k-most-common.txt)	

* [crunch - Wordlist Generator](https://sourceforge.net/p/crunch-wordlist/code/ci/master/tree/)

  
*Egon Teiniker, 2017-2026, GPL v3.0*	
