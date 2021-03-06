<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!--
This XSLT 2 stylesheet can be used to transform a iRed2 xml file to an .rem file.
-->
<xsl:transform
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="2.0">

    <xsl:output method="text"/>
    <xsl:template match="@*|node()"/>

    <xsl:template match="/">
    <xsl:text xml:space="preserve">[REMOTE]
 [NAME]unnamed_remote

[TIMING]
</xsl:text>
        <xsl:apply-templates select="//string[preceding-sibling::key[1][text()='irData']]" mode="timing"/>
        <xsl:text>

[COMMANDS]
</xsl:text>
        <xsl:apply-templates select="//dict[string[preceding-sibling::key[1][text()='irData']]]" mode="command"/>
    </xsl:template>

    <xsl:template match="string" mode="timing">
        <xsl:text> [</xsl:text>
        <xsl:value-of select="position() - 1"/>
        <xsl:text>]</xsl:text>
        <xsl:value-of select="replace(.,'\[D\].*$','')"/>
        <xsl:text xml:space="preserve">
</xsl:text>
    </xsl:template>

    <xsl:template match="dict" mode="command">
        <xsl:text> [</xsl:text>
        <xsl:value-of select="string[preceding-sibling::key[1][text()='title']]"/>
        <xsl:text>][T]</xsl:text>
        <xsl:value-of select="position() - 1"/>
        <xsl:text>[D]</xsl:text>
        <xsl:value-of select="replace(string[preceding-sibling::key[1][text()='irData']],'^.*\[D\]','')"/>
        <xsl:text xml:space="preserve">
</xsl:text>
    </xsl:template>
</xsl:transform>
