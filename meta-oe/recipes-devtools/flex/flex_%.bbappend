FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fix-malloc-with-gcc-15.patch"

PACKAGE_NO_LOCALE = "1"
