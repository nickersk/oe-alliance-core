EXTRA_OECONF += " --disable-udev"

DEPENDS := "${@' '.join(x for x in d.getVar('DEPENDS').split() if not x.startswith('udev'))}"
