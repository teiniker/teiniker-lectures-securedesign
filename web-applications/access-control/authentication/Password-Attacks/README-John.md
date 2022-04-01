# Password Cracking with John the Ripper

John the Ripper is an Open Source password security auditing and password recovery tool available for many 
operating systems.

We use the version shipped with the **Kali Linux** distribution.

_Tip_: Remove the `.john` directory before you run these examples.
```
$ sudo rm -r /root/.john
```

## Cracking Raw Hash-Values
``` 
$ cat passwords-sha512.txt
bb3340cfb96337e142cdd810678c0207be932bd8e6cd2890fbff2304491258efb07e6a51738ffd57dada2475b45f65650a5a2e2132a491766c8d7d7c67a9c85b
a107d0cea0eac4378978f053faf87023c4b1fe5ff440cda0b49e9504ab111463b276573bf664691b65a93e7e26a6fa5bf68b9791be096344f6d423c93deb0aea
bf0b3611c9e8fb148709a5188cee5b913bf36ad416c899adf63c701deaa71693ab897141d63b4d3dec7b60bf07e794fcb152f2cdf8a93d8bb15baf850c4b0631

$ sudo john --format=raw-sha512 passwords-sha512.txt
    Using default input encoding: UTF-8
    Loaded 3 password hashes with no different salts (Raw-SHA512 [SHA512 128/128 AVX 2x])
    Press 'q' or Ctrl-C to abort, almost any other key for status
    bono             (?)
    abba             (?)
    neon             (?)
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

  
*Egon Teiniker, 2019-2021, GPL v3.0*	