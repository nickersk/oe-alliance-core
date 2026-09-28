FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://set-multilib-paths.patch \
    file://0001-mips32-use-c11-atomics-for-64-bit-atomic-add.patch \
"

OECMAKE_GENERATOR = "Unix Makefiles"

EXTRA_OECMAKE += "-DCMAKE_POSITION_INDEPENDENT_CODE=1"

COMPATIBLE_HOST = ""
