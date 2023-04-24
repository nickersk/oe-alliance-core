SUMMARY = "libmatroska is a C++ libary to parse Matroska files (.mkv and .mka)"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE.LGPL;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = "libebml"

SRC_URI = "https://dl.matroska.org/downloads/${BPN}/${BPN}-${PV}.tar.xz"
SRC_URI[md5sum] = "576ca1b6d2661f328a42192124135e86"
SRC_URI[sha256sum] = "572a3033b8d93d48a6a858e514abce4b2f7a946fe1f02cbfeca39bfd703018b3"

inherit cmake

EXTRA_OECMAKE += "-DBUILD_SHARED_LIBS=YES"
