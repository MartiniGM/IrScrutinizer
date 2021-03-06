(This text has been extracted from the manual in XML format `IrScrutinizer.xml`. Do not edit this file.)

# Building from sources

"IrScrutinizer" is one subproject (corresponding to a Java _package_) within [harctoolbox.org](http://harctoolbox.org).
    It depends on several other subprojects within harctoolbox. The repository `IrScrutinizer` consists
    of this subproject.

The released versions are found on the [download page](https://github.com/bengtmartensson/IrScrutinizer/releases).
    The development sources are maintained on [my GitHub repository](https://github.com/bengtmartensson/IrScrutinizer).
    Forking and pull requests are welcome!

I go to great lengths ensuring that the program runs equally well on all supported platforms.
        I do not care too much that all aspects of the build runs equally well on all platforms.
        I build with Linux (Fedora), the continuous integration build runs on Travis (Ubunto).
        Other platforms are treated stepmotherly.

## Dependencies

As any program of substantial size, IrScrutinizer uses a number of third-party components.
    All of these are also free software, carrying compatible licenses.
The dependent packages need to be installed also in
    maven in order for the build to work. With the dependencies available, the script `tools/install-deps.sh`
can be used to install  them in the local maven repository before building.

### DevSlashLirc
This library is used to access `/dev/lirc`-hardware. It is used by the Linux version only.
        It is a Java JNI library, written in Java and C++. It is written by myself,
        and available [here](https://github.com/bengtmartensson/DevSlashLirc).
        

The subdirectories `native/Linux-amd64`,
        `native/Linux-i386`, and
        `native/Linux-arm` contain compiled versions for the x86_64, x86_32, and ARM processors respectively.

The package can be downloaded, and the Java part built, by the script `tools/build-DevSlashLirc.sh`.

### IrpTransmogrifier, Girr, HarcHardwareBundle, Jirc
These are all pure Java packages that are required to build IrScrutinizer.
            They can be downloaded and built by the scripts
            `tools/build-IrpTransmogrifier.sh`,
            `tools/build-Girr.sh`,
            `tools/build-HarcHardwareBundle.sh`, and
            `tools/build-Jirc.sh`.
        

### RXTX
The serial communication packate RXTX is also included in the source package. This builds a shared library and a jar file.
    On Linux, if there is a system supported RXTX (`librxtxSerial` really), it should be preferred,
    in particular since it knows the preferred lock direcory for the present operating system.
    The distribution contains pre-compiled binaries for Linux, Windows, and Mac OS X, both in 32- and 64-bit versions.
    To compile the C sources, see the sub-directory `rxtx-pre2h` and the instructions therein.

Note that the system supplied RXTX jar on many system (e.g. Fedora 21) has some issues
        (version number incompatible with the shared library, does not recognize
        the `/dev/ttyACM*`-ports required by IrToy and many Arduinos, unflexible library loading),
        so using our RXTX jar together with the system supplied shared library can be sensible.

### JCommander, minimal-json
Normally, these components are downloaded and installed automatically by maven.

### Tonto
If the system supports Tonto, use the system version. (On recent Fedora, use `sudo dnf install tonto`.)
        Otherwise, it can be downloaded and installed by the script `tools/build-tonto.sh`.

Note that the shared library `libjnijcomm`,
        which is required by the program Tonto for communicating with a Pronto remote through a serial interface,
        is not required for use with IrScrutinizer, and can therefore be left out.

## Building
As of version 1.1.2, the [Maven](http://maven.apache.org/index.html) "software
project management and comprehension tool" is used as building system.
Modern IDEs like Netbeans and Eclips integrate Maven, so build etc can be initiated from the IDE.
Of course, the shell command `mvn install` can also be used. It creates some artifacts which can
be used to run IrScrutinizer in the `IrScrutinizer/target` directory.

To prepare the Windows version, some shell tools are needed. These are:



* The `unix2dos` and `dos2unix` utilities, typically in the `dos2unix` package.

* The `icotool` utility, typically in the `icoutils` package


## Windows setup.exe creation
For building the Windows setup.exe, the [Inno Installer version 6](http://www.jrsoftware.org/download.php/is.exe)
    is needed. To build the Windows `setup.exe` file, preferably the work area should
be mounted on a Windows computer. Then, on the Windows computer, open
        the generated file `IrScrutinizer/target/IrScrutinizer_inno.iss` with
        the Inno installer, and start the compile. This will generate the desired file
    `IrScutinizer-`_version_`.exe`.

Alternatively, the "compatibility layer capable of running
    Windows applications" software application [Wine](https://www.winehq.org) (included in most Linux
    distributions) can run the ISCC compiler of Inno. The Maven file
    `IrScrutinizer/pom.xml` contains an
    invocation along these lines, conditional upon the existence of the file `../Inno Setup 6/ISCC.exe`.

## Mac OS X app creation
The Maven build creates a file
`IrScrutinizer-`_version_`-macOS.zip`.
This file can be directly distributed to the users, to be installed according to
[these instructions](http://harctoolbox.org/IrScrutinizer.html#Mac+OS+X+app).

The icon file `IrScrutinizer.icns` was produced from the Crystal-Clear
icon `babelfish.png` in 128x128 resolution, using the procedure
described
[here](http://stackoverflow.com/questions/11770806/why-doesnt-icon-composer-2-4-support-the-1024x1024-size-icon-any-more).
        

## AppImage creation
To build the x86_64 AppImage, define `bundledjdk_url_sans_file`
    and `bundledjdk` in `pom.xml` to point to a suitable JDK distrubution file.
    If a file with name given by `bundledjdk`
    is not present in the top level directory, it can be downloaded by the script `tools/get-jdk-tar.sh`.
    Then the maven goal `make-appimage` (which will be invoked during a normal build) will build an appimage for x86_64.
    

## Test invocation
For testing purposes, the programs can be invoked from their different target directories.
        IrScrutinizer can be invoked as


    
    $ java -jar target/IrScrutinizer-jar-with-dependencies.jar

or, if the shared libraries are required, with _path-to-shared-libraries_ denoting the path to a directory containing
the shared libraries.


    
    $ java -Djava.library.path=_path-to-shared-libraries_ -jar target/IrScrutinizer-jar-with-dependencies.jar

IrScrutinizer can also be started by double clicking the mentioned jar file,
    provided that the desktop has been configured to start executable jar with "java".

## Installation
Maven does not support something like `make install` for deployment installing a
recently build program on the local host.
Instead, the supplied `tools/Makefile` can
install the program to normal Linux locations (in the Makefile `INSTALLDIR`),


    
        sudo make -f tools/Makefile install
    
Equivalently, the just created generic-binary package
`IrScrutinizer/target/IrScrutinizer-*-bin.zip`) can be installed using [these instructions](http://harctoolbox.org/IrScrutinizer.html#Generic+Binary).

