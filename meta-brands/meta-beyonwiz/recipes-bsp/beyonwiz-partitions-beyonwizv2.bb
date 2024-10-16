SUMMARY = "beyonwizv2 partitions files"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRCDATE = "20201218"
PR = "${SRCDATE}"

S = "${WORKDIR}/sources-unpack"
UNPACKDIR = "${S}"

SRC_URI = "https://source.mynonpublic.com/beyonwiz/${MACHINE}-partitions-${SRCDATE}.zip \
  file://flash-apploader \
"

inherit update-rc.d

INITSCRIPT_NAME = "flash-apploader"
INITSCRIPT_PARAMS = "start 90 S ."

ALLOW_EMPTY:${PN} = "1"
do_configure[nostamp] = "1"

do_install() {
    install -d ${D}/usr/share
    install -m 0644 ${UNPACKDIR}/patitions/bootargs.bin ${D}/usr/share/bootargs.bin
    install -m 0644 ${UNPACKDIR}/patitions/fastboot.bin ${D}/usr/share/fastboot.bin
    install -m 0644 ${UNPACKDIR}/patitions/apploader.bin ${D}/usr/share/apploader.bin
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/flash-apploader ${D}${sysconfdir}/init.d/flash-apploader
}

FILES:${PN} = "/usr/share ${sysconfdir}"

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/bootargs.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/boot.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/fastboot.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/apploader.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/pq_param.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/emmc_partitions.xml ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/baseparam.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/logo.img ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
    install -m 0755 ${UNPACKDIR}/patitions/deviceinfo.bin ${DEPLOY_DIR_IMAGE}/${MACHINE}-partitions
}

addtask deploy before do_package after do_install

SRC_URI[md5sum] = "a843de0d42cbb237119df2f30a7a6dd1"
SRC_URI[sha256sum] = "609430fdb06ac63f005a5544db52adfa9cad1bf6fe454b01069ff35f0a899417"

INSANE_SKIP:${PN} += "already-stripped"

pkg_postinst:${PN}() {
    if [ "x$D" == "x" ]; then
        if [ -f /usr/share/apploader.bin -a -b /dev/block/by-name/loader ] ; then
            dd if=/usr/share/apploader.bin of=/dev/block/by-name/loader
        fi
    fi
    true
}
