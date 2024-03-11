<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>

    <xsl:template match="/">
        <xsl:text>{"deliveryPersons": [</xsl:text>
        <xsl:for-each select="cookbox/deliveryPersons/deliveryPerson">
            <xsl:if test="position() != 1">, </xsl:if>
            <xsl:text>{"name": "</xsl:text>
            <xsl:value-of select="name"/>
            <xsl:text>", "contact": "</xsl:text>
            <xsl:value-of select="contact"/>
            <xsl:text>", "deliveriesMade": </xsl:text>
            <xsl:value-of select="deliveriesMade"/>
            <xsl:text>}</xsl:text>
        </xsl:for-each>
        <xsl:text>]}</xsl:text>
    </xsl:template>
</xsl:stylesheet>
