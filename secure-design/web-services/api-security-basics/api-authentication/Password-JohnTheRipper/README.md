# Password Cracking with John the Ripper

John the Ripper is an Open Source password security auditing and password recovery tool available for many 
operating systems.

## Setup
```
$ sudo apt install john
```

_Tip_: Remove the `.john` directory before you run these examples.
```
$ sudo rm -r /root/.john
```

## Cracking Linux Passwords
```
$ cat passwords-shadow.txt
student:$6$wRUypFSMS1P/TZ37$SmzPx5guncYyVOd368wi/YvTvWDPlzWtG1kEuVIrImp6tw502oPyOYNivBR/6QBeK18P9t.FG6QlEC2M9N.m01::0:99999:7:::

$ sudo john passwords-shadow.txt
  Created directory: /root/.john
  Warning: detected hash type "sha512crypt", but the string is also recognized as "crypt"
  Use the "--format=crypt" option to force loading these as that type instead
  Using default input encoding: UTF-8
  Loaded 1 password hash (sha512crypt, crypt(3) $6$ [SHA512 128/128 AVX 2x])
  Press 'q' or Ctrl-C to abort, almost any other key for status
  student          (student)
```


## References
* [John The Ripper Hash Formats](https://pentestmonkey.net/cheat-sheet/john-the-ripper-hash-formats)

* [Youtube: Password Cracking With John The Ripper](https://youtu.be/XjVYl1Ts6XI)

* [John the Ripper Password Cracker](https://www.openwall.com/john/) 
	
* [10k Most Common Passwords](https://github.com/danielmiessler/SecLists/blob/master/Passwords/Common-Credentials/10k-most-common.txt)	

* [crunch - Wordlist Generator](https://sourceforge.net/p/crunch-wordlist/code/ci/master/tree/)

  
*Egon Teiniker, 2017-2025, GPL v3.0*	