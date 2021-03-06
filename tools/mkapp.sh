#!/bin/sh
#
# Create a MacOS app in the form of a zip and dsg (uncompressed) file
#
#

APPNAME=$1
VERSION=$2

TOPDIR=`pwd`
TARGETDIR=${TOPDIR}/target
JRE_DIR=${TARGETDIR}/jre-x64-macOS
WORKDIR=${TARGETDIR}
APPDIR=${WORKDIR}/${APPNAME}-${VERSION}
REPODIR=${APPDIR}/${APPNAME}.app/Contents/Resources/Java/repo

rm -rf ${APPDIR}/${APPNAME}.app
mkdir -p ${REPODIR}
mkdir -p ${APPDIR}/${APPNAME}.app/Contents/MacOS
cp ${TARGETDIR}/doc/README* ${APPDIR}
cp ${TARGETDIR}/doc/LICENSE* ${APPDIR}
cp ${TARGETDIR}/doc/${APPNAME}* ${APPDIR}

cp ${TARGETDIR}/Info.plist ${APPDIR}/${APPNAME}.app/Contents
cp ${TARGETDIR}/Launcher ${APPDIR}/${APPNAME}.app/Contents/MacOS
chmod +x ${APPDIR}/${APPNAME}.app/Contents/MacOS/Launcher
cp ${TOPDIR}/src/main/resources/${APPNAME}.icns ${APPDIR}/${APPNAME}.app/Contents/Resources

( cd ${APPDIR}/${APPNAME}.app/Contents/Resources/Java/repo; \
  unzip -q ${TARGETDIR}/${APPNAME}-${VERSION}-bin.zip )

# These may have been copied already by the unzip. But who cares? :-).
cp -r "${TOPDIR}/native/Mac OS X-x86_64" ${REPODIR}
cp -r "${TOPDIR}/native/Mac OS X-i386"   ${REPODIR}

if [ -f ${TOPDIR}/jre-x64-macOS.tar.gz ] ; then
    (cd ${TARGETDIR} ; tar zxf ${TOPDIR}/jre-x64-macOS.tar.gz )
fi

if [ -d ${JRE_DIR} ] ; then
    cp -a ${JRE_DIR} ${REPODIR}
fi

# Delete some files that are not relevant in the MacOs environment
rm -f ${REPODIR}/doc/INSTALL-binary* ${REPODIR}/INSTALL-binary*
rm -f ${REPODIR}/irscrutinizer.bat ${REPODIR}/irscrutinizer.desktop ${REPODIR}/setup-irscrutinizer.sh
rm -f ${REPODIR}/*_install.txt
rm -rf ${REPODIR}/Linux* ${REPODIR}/Windows*

# Generate zipped version of directory with app.
(cd ${WORKDIR}; zip -q -r ${TARGETDIR}/${APPNAME}-${VERSION}-macOS.zip ${APPNAME}-${VERSION})

# Generate dmg image, uncompressed
genisoimage  -V  ${APPNAME}-${VERSION} -D -R -apple -no-pad -o ${TARGETDIR}/${APPNAME}-${VERSION}-macOS.dmg ${TARGETDIR}/${APPNAME}-${VERSION}
