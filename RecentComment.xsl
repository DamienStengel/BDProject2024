<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Commentaires récents</title>
                <link rel="stylesheet" type="text/css" href="style.css"/>
            </head>
            <body>
                <h1>Commentaires récents sur les recettes</h1>
                <xsl:for-each select="cookbox/comments/comment">
                    <xsl:sort select="date" order="descending"/>
                    <div class="comment">
                        <p><b>Date:</b> <xsl:value-of select="date"/></p>
                        <p><b>Auteur:</b> <xsl:value-of select="author"/></p>
                        <p><b>Commentaire:</b> <xsl:value-of select="content"/></p>
                        <p><b>Recette ID:</b> <xsl:value-of select="recipeId"/></p>
                    </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
