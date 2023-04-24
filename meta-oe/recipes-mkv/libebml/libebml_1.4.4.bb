SUMMARY = "libebml is a C++ libary to parse EBML files"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE.LGPL;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "https://dl.matroska.org/downloads/${BPN}/${BPN}-${PV}.tar.xz"
SRC_URI[md5sum] = "d521691cad7200850a78ddc1264e9ef9"
SRC_URI[sha256sum] = "82dc5f83356cc9340aee76ed7512210b3a4edf5f346bc9c2c7044f55052687a7"

inherit cmake

EXTRA_OECMAKE += "-DBUILD_SHARED_LIBS=YES"
