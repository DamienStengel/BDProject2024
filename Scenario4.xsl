<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:key name="recipes-by-budget" match="recipe" use="budget"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Recettes Regroupées par Budget</title>
                <link rel="stylesheet" type="text/css" href="style.css"/>
            </head>
            <body>
                <h1>Recettes Regroupées par Budget</h1>
                <xsl:for-each select="cookbox/recipes/recipe[count(. | key('recipes-by-budget', budget)[1]) = 1]">
                    <xsl:sort select="budget"/>
                    <div class="budgetGroup">
                        <h2>Budget: <xsl:value-of select="budget"/> €</h2>
                        <div class="recipes">
                            <xsl:for-each select="key('recipes-by-budget', budget)">
                                <div class="recipe">
                                    <h3><xsl:value-of select="title"/></h3>
                                    <p><b>Description:</b> <xsl:value-of select="description"/></p>
                                    <p><b>Cuisine:</b> <xsl:value-of select="cuisine"/></p>
                                    <p><b>Santé:</b> <xsl:value-of select="health"/></p>
                                    <h4>Ingrédients:</h4>
                                    <ul>
                                        <xsl:for-each select="ingredients/ingredient">
                                            <li><xsl:value-of select="quantity"/> <xsl:value-of select="unit"/> de <xsl:value-of select="name"/></li>
                                        </xsl:for-each>
                                    </ul>
                                    <p><b>Étapes:</b> <xsl:value-of select="steps"/></p>
                                </div>
                            </xsl:for-each>
                        </div>
                    </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
