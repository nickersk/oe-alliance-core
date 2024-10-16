SUMMARY = "Valis hd fonts"
MAINTAINER = "valis"
require conf/license/license-gplv2.inc
inherit allarch

PV = "2010.05.14"
PR = "r0"

PACKAGES = "font-valis-hd"
PROVIDES = "font-valis-hd"

SRC_URI="file://hd.ttf file://hdi.ttf"

FILES:font-valis-hd = "/usr/*"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -d ${D}/usr/share/fonts
    install -m 0644 ${S}/*.ttf ${D}/usr/share/fonts
}
