SUMMARY = "Python 2 and 3 compatibility library"
HOMEPAGE = "https://pypi.org/project/six/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e50fab7e9501f3dfc920eafeb24b46ea"

inherit pypi setuptools3 ptest-python-pytest

RDEPENDS:${PN} = "python3-io"

BBCLASSEXTEND = "native nativesdk"

SRC_URI[sha256sum] = "ff70335d468e7eb6ec65b95b99d3a2836546063f63acc5171de367e834932a81"

PTEST_PYTEST_DIR = "."

# test_six.py exercises the six.moves mappings, which import these stdlib
# modules that are packaged separately in oe-core.
RDEPENDS:${PN}-ptest += " \
    python3-html \
    python3-netclient \
    python3-xmlrpc \
"

do_install_ptest:append() {
    # six ships its test at the sdist root; keep only test_six.py plus the
    # generated run-ptest, and drop the packaged library sources / bytecode.
    rm -f  ${D}${PTEST_PATH}/six.py
    rm -f  ${D}${PTEST_PATH}/setup.py ${D}${PTEST_PATH}/setup.cfg ${D}${PTEST_PATH}/MANIFEST.in
    rm -f  ${D}${PTEST_PATH}/CHANGES ${D}${PTEST_PATH}/README.rst ${D}${PTEST_PATH}/PKG-INFO ${D}${PTEST_PATH}/LICENSE
    rm -rf ${D}${PTEST_PATH}/build ${D}${PTEST_PATH}/documentation ${D}${PTEST_PATH}/six.egg-info ${D}${PTEST_PATH}/__pycache__
}

include python3-package-split.inc
