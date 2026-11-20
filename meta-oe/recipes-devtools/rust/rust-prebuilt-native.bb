SUMMARY = "Upstream prebuilt Rust toolchain components for the build host"
DESCRIPTION = "Fetches rustc, cargo and the host standard library from \
static.rust-lang.org and stages them so rust-native can install them instead \
of bootstrapping a compiler from source. Only the component trees are staged; \
rust-native remains the recipe that provides rustc and cargo."
HOMEPAGE = "https://www.rust-lang.org"
LICENSE = "Apache-2.0 OR MIT"
LIC_FILES_CHKSUM = "file://LICENSE-APACHE;md5=71b224ca933f0676e26d5c2e2271331c"

# Track whatever rust version oe-core carries, so a bump there needs no change here.
def rust_recipe_version(d):
    import glob, os, re
    core = d.getVar('COREBASE') or ''
    best = ''
    pattern = os.path.join(core, 'meta', 'recipes-devtools', 'rust', 'rust_*.bb')
    for f in glob.glob(pattern):
        m = re.match(r'rust_(\d[\d.]*)\.bb$', os.path.basename(f))
        if m:
            cur = [int(x) for x in m.group(1).split('.')]
            if not best or cur > [int(x) for x in best.split('.')]:
                best = m.group(1)
    return best

PV = "${@rust_recipe_version(d)}"

RUST_PREBUILT_SYS = "${BUILD_ARCH}-unknown-linux-gnu"
RUST_PREBUILT_BASE = "https://static.rust-lang.org/dist"

# sha256 of the upstream dist tarballs, per build host arch
RUST_PREBUILT_SHA256[x86_64-rustc]  = "f1d0b1dec81dab6f523cd098507763487b0d4bd7fd2c87582f154128719be03b"
RUST_PREBUILT_SHA256[x86_64-cargo]  = "7e29ce07d7c6e92d1826870575ba27d57fff6cb3e754aa57d99099cb88fab35d"
RUST_PREBUILT_SHA256[x86_64-std]    = "4591b6486594ded52ecd6135db95bf9355bbd3ca7a380888b141528bbcf9a5d4"
RUST_PREBUILT_SHA256[aarch64-rustc] = "fcbfa5b8a0cb28f0c4edfb6dda6910ee910993db31f05258613f5cd5b851263a"
RUST_PREBUILT_SHA256[aarch64-cargo] = "d07ee80eb6ba3c5845c554ac88086e1ff5d63f83d99c3c09cb32504cbedd6844"
RUST_PREBUILT_SHA256[aarch64-std]   = "c73f62fbe5d837f9807ed9e0076575dd8d3475c79af2b30e3570d328e6713f8f"

SRC_URI[rustc.sha256sum] = "${@d.getVarFlag('RUST_PREBUILT_SHA256', d.getVar('BUILD_ARCH') + '-rustc') or ''}"
SRC_URI[cargo.sha256sum] = "${@d.getVarFlag('RUST_PREBUILT_SHA256', d.getVar('BUILD_ARCH') + '-cargo') or ''}"
SRC_URI[std.sha256sum] = "${@d.getVarFlag('RUST_PREBUILT_SHA256', d.getVar('BUILD_ARCH') + '-std') or ''}"

SRC_URI = "\
    ${RUST_PREBUILT_BASE}/rustc-${PV}-${RUST_PREBUILT_SYS}.tar.xz;name=rustc \
    ${RUST_PREBUILT_BASE}/cargo-${PV}-${RUST_PREBUILT_SYS}.tar.xz;name=cargo \
    ${RUST_PREBUILT_BASE}/rust-std-${PV}-${RUST_PREBUILT_SYS}.tar.xz;name=std \
"

S = "${UNPACKDIR}/cargo-${PV}-${RUST_PREBUILT_SYS}"

RUST_PREBUILT_STAGE = "${datadir}/rust-prebuilt"

inherit native

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}${RUST_PREBUILT_STAGE}
    for comp in ${UNPACKDIR}/*-${PV}-${RUST_PREBUILT_SYS}; do
        [ -f "$comp/install.sh" ] || continue
        cp -a "$comp" ${D}${RUST_PREBUILT_STAGE}/
    done
}

SYSROOT_DIRS += "${RUST_PREBUILT_STAGE}"

INHIBIT_SYSROOT_STRIP = "1"

# Upstream binaries: stripped already, and not built by us.
INSANE_SKIP:${PN} += "already-stripped arch staticdev ldflags textrel file-rdeps"
EXCLUDE_FROM_SHLIBS = "1"
