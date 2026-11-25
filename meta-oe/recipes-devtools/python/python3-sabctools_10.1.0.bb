SUMMARY = "SABCTools - C implementations of functions for use within SABnzbd"
DESCRIPTION = "This module implements three main sets of C implementations that are used within SABnzbd"
HOMEPAGE = "https://pypi.org/project/sabctools/"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=892f569a555ba9c07a568a7c0c4fa63a"

DEPENDS = "cmake-native ninja-native python3-scikit-build-core-native"

inherit pypi python_setuptools_build_meta

SRC_URI:append = " file://0001-detect-target-arch-from-compiler.patch"

SRC_URI[md5sum] = "de27c5fb24b062b4fb5db15a67914007"
SRC_URI[sha256sum] = "4836a45c09b20a7ee89d0f1d18558fc854f3afbf4aca373b5744fe1b52323c84"

include python3-package-split.inc

INSANE_SKIP:${PN} = "already-stripped"
