import xunittest # 1:7-1:14
import xxdrlib # 3:7-3:12
e101 = xunittest.xTestCase # 5:14-5:30
class xXDRTest(e101): # 6:0-53:-1
    def xtest_xdr(xself): # 7:4-53:-1
        e0 = xxdrlib.xPacker # 8:12-8:24
        e1 = e0() # 8:12-8:26
        xp = e1 # 8:8-8:26
        xs = "hello world" # 10:8-10:25
        xa = ["what", "is", "hapnin", "doctor"] # 11:8-11:49
        e2 = xp.xpack_int # 13:8-13:17
        lhs1 = 42  # 13:19-13:20
        e3 = e2(lhs1) # 13:8-13:21
        e4 = xp.xpack_int # 14:8-14:17
        e5 = (-17 ) # 14:19-14:21
        e6 = e4(e5) # 14:8-14:22
        e7 = xp.xpack_uint # 15:8-15:18
        lhs4 = 9  # 15:20-15:20
        e8 = e7(lhs4) # 15:8-15:21
        e9 = xp.xpack_bool # 16:8-16:18
        lhs6 = True # 16:20-16:23
        e10 = e9(lhs6) # 16:8-16:24
        e11 = xp.xpack_bool # 17:8-17:18
        lhs8 = False # 17:20-17:24
        e12 = e11(lhs8) # 17:8-17:25
        e13 = xp.xpack_uhyper # 18:8-18:20
        lhs10 = 45  # 18:22-18:23
        e14 = e13(lhs10) # 18:8-18:24
        e15 = xp.xpack_float # 19:8-19:19
        lhs12 = 1.9 # 19:21-19:23
        e16 = e15(lhs12) # 19:8-19:24
        e17 = xp.xpack_double # 20:8-20:20
        lhs14 = 1.9 # 20:22-20:24
        e18 = e17(lhs14) # 20:8-20:25
        e19 = xp.xpack_string # 21:8-21:20
        lhs16 = xs # 21:22-21:22
        e20 = e19(lhs16) # 21:8-21:23
        e21 = xp.xpack_list # 22:8-22:18
        e22 = xrange(5 ) # 22:20-22:27
        e23 = xp.xpack_uint # 22:30-22:40
        e24 = e21(e22, e23) # 22:8-22:41
        e25 = xp.xpack_array # 23:8-23:19
        lhs19 = xa # 23:21-23:21
        e26 = xp.xpack_string # 23:24-23:36
        e27 = e25(lhs19, e26) # 23:8-23:37
        e28 = xp.xget_buffer # 26:15-26:26
        e29 = e28() # 26:15-26:28
        xdata = e29 # 26:8-26:28
        e30 = xxdrlib.xUnpacker # 27:13-27:27
        lhs22 = xdata # 27:29-27:32
        e31 = e30(lhs22) # 27:13-27:33
        xup = e31 # 27:8-27:33
        e32 = xself.xassertEqual # 29:8-29:23
        e33 = xup.xget_position # 29:25-29:39
        e34 = e33() # 29:25-29:41
        lhs25 = 0  # 29:44-29:44
        e35 = e32(e34, lhs25) # 29:8-29:45
        e36 = xself.xassertEqual # 31:8-31:23
        e37 = xup.xunpack_int # 31:25-31:37
        e38 = e37() # 31:25-31:39
        lhs28 = 42  # 31:42-31:43
        e39 = e36(e38, lhs28) # 31:8-31:44
        e40 = xself.xassertEqual # 32:8-32:23
        e41 = xup.xunpack_int # 32:25-32:37
        e42 = e41() # 32:25-32:39
        e43 = (-17 ) # 32:42-32:44
        e44 = e40(e42, e43) # 32:8-32:45
        e45 = xself.xassertEqual # 33:8-33:23
        e46 = xup.xunpack_uint # 33:25-33:38
        e47 = e46() # 33:25-33:40
        lhs33 = 9  # 33:43-33:43
        e48 = e45(e47, lhs33) # 33:8-33:44
        e49 = xself.xassertTrue # 34:8-34:22
        e50 = xup.xunpack_bool # 34:24-34:37
        e51 = e50() # 34:24-34:39
        lhs36 = True # 34:44-34:47
        e52 = (e51 is lhs36) # 34:24-34:47
        e53 = e49(e52) # 34:8-34:48
        e54 = xup.xget_position # 37:14-37:28
        e55 = e54() # 37:14-37:30
        xpos = e55 # 37:8-37:30
        e56 = xself.xassertTrue # 38:8-38:22
        e57 = xup.xunpack_bool # 38:24-38:37
        e58 = e57() # 38:24-38:39
        lhs41 = False # 38:44-38:48
        e59 = (e58 is lhs41) # 38:24-38:48
        e60 = e56(e59) # 38:8-38:49
        e61 = xup.xset_position # 41:8-41:22
        lhs44 = xpos # 41:24-41:26
        e62 = e61(lhs44) # 41:8-41:27
        e63 = xself.xassertTrue # 42:8-42:22
        e64 = xup.xunpack_bool # 42:24-42:37
        e65 = e64() # 42:24-42:39
        lhs47 = False # 42:44-42:48
        e66 = (e65 is lhs47) # 42:24-42:48
        e67 = e63(e66) # 42:8-42:49
        e68 = xself.xassertEqual # 44:8-44:23
        e69 = xup.xunpack_uhyper # 44:25-44:40
        e70 = e69() # 44:25-44:42
        lhs51 = 45  # 44:45-44:46
        e71 = e68(e70, lhs51) # 44:8-44:47
        e72 = xself.xassertAlmostEqual # 45:8-45:29
        e73 = xup.xunpack_float # 45:31-45:45
        e74 = e73() # 45:31-45:47
        lhs54 = 1.9 # 45:50-45:52
        e75 = e72(e74, lhs54) # 45:8-45:53
        e76 = xself.xassertAlmostEqual # 46:8-46:29
        e77 = xup.xunpack_double # 46:31-46:46
        e78 = e77() # 46:31-46:48
        lhs57 = 1.9 # 46:51-46:53
        e79 = e76(e78, lhs57) # 46:8-46:54
        e80 = xself.xassertEqual # 47:8-47:23
        e81 = xup.xunpack_string # 47:25-47:40
        e82 = e81() # 47:25-47:42
        lhs60 = xs # 47:45-47:45
        e83 = e80(e82, lhs60) # 47:8-47:46
        e84 = xself.xassertEqual # 48:8-48:23
        e85 = xup.xunpack_list # 48:25-48:38
        e86 = xup.xunpack_uint # 48:40-48:53
        e87 = e85(e86) # 48:25-48:54
        lhs63 = xlist # 48:57-48:60
        e88 = xrange(5 ) # 48:62-48:69
        e89 = lhs63(e88) # 48:57-48:70
        e90 = e84(e87, e89) # 48:8-48:71
        e91 = xself.xassertEqual # 49:8-49:23
        e92 = xup.xunpack_array # 49:25-49:39
        e93 = xup.xunpack_string # 49:41-49:56
        e94 = e92(e93) # 49:25-49:57
        lhs67 = xa # 49:60-49:60
        e95 = e91(e94, lhs67) # 49:8-49:61
        e96 = xup.xdone # 50:8-50:14
        e97 = e96() # 50:8-50:16
        e98 = xself.xassertRaises # 51:8-51:24
        lhs70 = xEOFError # 51:26-51:33
        e99 = xup.xunpack_uint # 51:36-51:49
        e100 = e98(lhs70, e99) # 51:8-51:50
e128 = xunittest.xTestCase # 53:26-53:42
class xConversionErrorTest(e128): # 54:0-76:-1
    def xsetUp(xself): # 55:4-58:3
        e102 = xxdrlib.xPacker # 56:22-56:34
        e103 = e102() # 56:22-56:36
        xself.xpacker = e103 # 56:8-56:36
    def xassertRaisesConversion(xself, *xargs): # 58:4-61:3
        e104 = xself.xassertRaises # 59:8-59:24
        e105 = xxdrlib.xConversionError # 59:26-59:47
        e106 = *xargs # 59:50-59:54
        e107 = e104(e105, e106) # 59:8-59:55
    def xtest_pack_int(xself): # 61:4-64:3
        e108 = xself.xassertRaisesConversion # 62:8-62:34
        e109 = xself.xpacker # 62:36-62:46
        e110 = e109.xpack_int # 62:36-62:55
        lhs75 = "string" # 62:58-62:65
        e111 = e108(e110, lhs75) # 62:8-62:66
    def xtest_pack_uint(xself): # 64:4-67:3
        e112 = xself.xassertRaisesConversion # 65:8-65:34
        e113 = xself.xpacker # 65:36-65:46
        e114 = e113.xpack_uint # 65:36-65:56
        lhs78 = "string" # 65:59-65:66
        e115 = e112(e114, lhs78) # 65:8-65:67
    def xtest_float(xself): # 67:4-70:3
        e116 = xself.xassertRaisesConversion # 68:8-68:34
        e117 = xself.xpacker # 68:36-68:46
        e118 = e117.xpack_float # 68:36-68:57
        lhs81 = "string" # 68:60-68:67
        e119 = e116(e118, lhs81) # 68:8-68:68
    def xtest_double(xself): # 70:4-73:3
        e120 = xself.xassertRaisesConversion # 71:8-71:34
        e121 = xself.xpacker # 71:36-71:46
        e122 = e121.xpack_double # 71:36-71:58
        lhs84 = "string" # 71:61-71:68
        e123 = e120(e122, lhs84) # 71:8-71:69
    def xtest_uhyper(xself): # 73:4-76:-1
        e124 = xself.xassertRaisesConversion # 74:8-74:34
        e125 = xself.xpacker # 74:36-74:46
        e126 = e125.xpack_uhyper # 74:36-74:58
        lhs87 = "string" # 74:61-74:68
        e127 = e124(e126, lhs87) # 74:8-74:69
e131 = (x__name__ == "__main__") # 76:3-76:24
if (e131): # 76:26-78:-1 
    e129 = xunittest.xmain # 77:4-77:16
    e130 = e129() # 77:4-77:18
else: # 76:0-78:-1
    pass # 76:0-78:-1