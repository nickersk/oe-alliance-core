SUMMARY = "Modern password hashing for your software and your servers."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8f7bb094c7232b058c7e9f2e431f389c"
HOMEPAGE = "https://pypi.org/project/bcrypt/"

DEPENDS += "python3-cffi-native"
LDFLAGS:append = "${@bb.utils.contains('DISTRO_FEATURES', 'ptest', ' -fuse-ld=bfd', '', d)}"

SRC_URI[sha256sum] = "33313a1200a3ae90b75587ceac502b048b840fc69e7f7a0905b5f87fac7a1258"

inherit pypi python_setuptools3_rust ptest-cargo cargo-update-recipe-crates

SRC_URI += " \
	file://0001-Bump-pyo3-from-0.20.0-to-0.20.3-in-src-_bcrypt-746.patch \
	file://run-ptest \
"

CARGO_SRC_DIR = "src/_bcrypt"

require ${BPN}-crates-${PV}.inc

RDEPENDS:${PN}-ptest += " \
	python3-pytest \
	python3-unittest-automake-output \
"

do_install_ptest() {
	install -d ${D}${PTEST_PATH}/tests
	cp -rf ${S}/tests/* ${D}${PTEST_PATH}/tests/
}

RDEPENDS:${PN}:class-target += "\
    python3-cffi \
    python3-ctypes \
    python3-shell \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI:append:mips32el = " \
    crate://crates.io/portable-atomic/1.6.0 \
    file://0001-Use-portable-atomic-for-targets-which-lack-64-bit-at.patch;patchdir=${UNPACKDIR}/cargo_home/bitbake/pyo3-0.20.3/ \
"

SRC_URI[portable-atomic-1.6.0.sha256sum] = "7170ef9988bc169ba16dd36a7fa041e5c4cbeb6a35b76d4c03daded371eae7c0"
