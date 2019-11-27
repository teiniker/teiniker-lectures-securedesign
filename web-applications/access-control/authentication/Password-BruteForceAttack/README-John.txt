Password Cracking With John The Ripper
-------------------------------------------------------------------------------
(Using Kali Linux)

Tip: Remove the ~/.john directory before you run these examples.

$ cat reverseengineering-passwords
bb3340cfb96337e142cdd810678c0207be932bd8e6cd2890fbff2304491258efb07e6a51738ffd57dada2475b45f65650a5a2e2132a491766c8d7d7c67a9c85b
a107d0cea0eac4378978f053faf87023c4b1fe5ff440cda0b49e9504ab111463b276573bf664691b65a93e7e26a6fa5bf68b9791be096344f6d423c93deb0aea
bf0b3611c9e8fb148709a5188cee5b913bf36ad416c899adf63c701deaa71693ab897141d63b4d3dec7b60bf07e794fcb152f2cdf8a93d8bb15baf850c4b0631

$ john --format=Raw-SHA512 reverseengineering-passwords
    Using default input encoding: UTF-8
    Loaded 3 password hashes with no different salts (Raw-SHA512 [SHA512 128/128 AVX 2x])
    Press 'q' or Ctrl-C to abort, almost any other key for status
    bono             (?)
    abba             (?)
    neon             (?)

$ cat shadow.fedora28
student:$6$wRUypFSMS1P/TZ37$SmzPx5guncYyVOd368wi/YvTvWDPlzWtG1kEuVIrImp6tw502oPyOYNivBR/6QBeK18P9t.FG6QlEC2M9N.m01::0:99999:7:::

$ john shadow.fedora28
  Created directory: /root/.john
  Warning: detected hash type "sha512crypt", but the string is also recognized as "crypt"
  Use the "--format=crypt" option to force loading these as that type instead
  Using default input encoding: UTF-8
  Loaded 1 password hash (sha512crypt, crypt(3) $6$ [SHA512 128/128 AVX 2x])
  Press 'q' or Ctrl-C to abort, almost any other key for status
  student          (student)

$ zip2john SoSe2018-SoftwareSecurity-Outline-KW16.zip > zip-passwd.txt
$ john --format=zip zip-passwd.txt
    Using default input encoding: UTF-8
    Loaded 1 password hash (ZIP, WinZip [PBKDF2-SHA1 128/128 AVX 4x])
    Press 'q' or Ctrl-C to abort, almost any other key for status
    admin            (SoSe2018-SoftwareSecurity-Outline-KW16.zip)


References:
    https://youtu.be/XjVYl1Ts6XI
	https://www.youtube.com/channel/UC0ZTPkdxlAKf-V33tqXwi3Q