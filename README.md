## About
sstt stands for sip security test tool, a tool to test defects in sip implementations

## Usage
1. Open simple test mode, set "HelpClass.debug_flag = false".
2. Open advance test mode, set "HelpClass.debug_flag = true".
3. Generate multiple report file, set "HelpClass.single_log__flag = false".
4. Generate single report file, set "HelpClass.single_log__flag = true".
5. Use SSTT generated test file, set "HelpClass.messaeg_flag = true".
6. Use customised test file, set "HelpClass.messaeg_flag = false".
7. Change timeout for connection, set "HelpClass.time_out = timeout_value".
8. Change number of flooding messages, set "HelpClass.num_flood_invite = number", "HelpClass.num_flood_register = number" and "HelpClass.num_flood_spam = number".

## Versions
`v.3.0`

1. Finish suggestion file and security report 
2. Fix 4 spelling mistakes in console result
3. Fix a bug in REGISTER message in SIP_Auth_Reg.java
4. Change help.txt to Release.txt
5. Add Readme.txt
6. Change pass and failed notation in results
7. Fix 6 spelling mistakes in html report
8. Fix a bug in timeout setting

`v.2.9`

1. Creat IP address pool for the source of SIP spam
2. Finish SIP spam attack test
3. Rearrange the SIP spam and DoS attack, since DoS has possiblity to bring down proxy and UA.
4. Fix a bug in DoS:INVITE flooding message
5. Fix a bug on test number in SIP_Syntax class

`v.2.9 beta`

1. Clean and add date notation for each class
2. Change Cseq field generation in INVITE message
3. Finish DoS sub test cases (2)
4. Fix a bug in TCP authentication REGISTER
5. Fix a bug in UDP SIP_Syntax test 
6. Change path parameter for each Message class

`v.2.8` 

1. Fix a bug on TLS on TCP test and REGISTER message generator
2. Fix a bug on CANCEL/BYE message (have to use the same Cseq field)
3. Finish No.SIPA031/2 Tear Down Session Attack Test

`v.2.8 beta Demo`

1. Using .txt instead of .rtf to get rid of extra letter mark in rich text file
2. TestFile still using .rtf for good format
3. Finish No.SIPA021/2 INVITE request spoofring attack
4. In order to solve "500 out of order error" for REGISTER, Use time related plus static increase variable to make CSeq for REGISTER

`v.2.7`

1. Finish No.SIPA011/2 Replay Attack 
2. Fix a single report bug
3. Fix a print mistake
4. Change INVITE message generator (add 2 premeter: ContactAddress&ContactURL)

`v.2.7 beta`

1. Finish No.SIPA001/2 Registration Hijacking 
2. Add SIPI011-2 Test stateful or stateless proxy server
3. Change suggestion generator; suggestion can be customized. Local at /SuggestionFile

`v.2.6`

1. Generate HTML Report - rewrite logwriter                       - 
2. Fix sometime cannot receive message
3. Suppport BYE message
3. Finish SIPVXXX test case

`v.2.5`

1. Fix the problem of timeout some time
1. Add server connection test using OPTIONS
1. Finish CANCEL (don't work) - branchID, call-ID, tag must be same
