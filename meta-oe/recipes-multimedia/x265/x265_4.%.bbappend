FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://set-multilib-paths.patch \
            file://fix-compile-with-gcc-15.patch"

OECMAKE_GENERATOR = "Unix Makefiles"

EXTRA_OECMAKE += "-DCMAKE_POSITION_INDEPENDENT_CODE=1"

COMPATIBLE_HOST = ""
