<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Recettes Cookbox</title>
                <link rel="stylesheet" href="style.css"/>
            </head>
            <body>
                <h1>Liste des Recettes</h1>
                <xsl:for-each select="cookbox/recipes/recipe">
                    <div class="recipe">
                        <h2><xsl:value-of select="title"/></h2>
                        <p><b>Description:</b> <xsl:value-of select="description"/></p>
                        <p><b>Cuisine:</b> <xsl:value-of select="cuisine"/></p>
                        <p><b>Santé:</b> <xsl:value-of select="health"/></p>
                        <p><b>Budget:</b> <xsl:value-of select="budget"/></p>
                        <h3>Ingrédients:</h3>
                        <ul>
                            <xsl:for-each select="ingredients/ingredient">
                                <li><xsl:value-of select="quantity"/> <xsl:value-of select="unit"/> de <xsl:value-of select="name"/></li>
                            </xsl:for-each>
                        </ul>
                        <p><b>Étapes:</b> <xsl:value-of select="steps"/></p>
                    </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
