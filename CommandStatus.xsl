<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Liste des Commandes</title>
                <link rel="stylesheet" type="text/css" href="style.css"/>
            </head>
            <body>
                <h1>Liste des Commandes</h1>
                <xsl:for-each select="cookbox/orders/order">
                    <div class="order">
                        <h2>Commande ID: <xsl:value-of select="@id"/></h2>
                        <p><b>Client:</b> <xsl:value-of select="customerName"/></p>
                        <p><b>Adresse de livraison:</b> <xsl:value-of select="deliveryAddress"/></p>
                        <p><b>Recettes commandées:</b>
                            <ul>
                                <xsl:for-each select="recipeIds/recipeId">
                                    <li><xsl:value-of select="."/></li>
                                </xsl:for-each>
                            </ul>
                        </p>
                        <p><b>Total Prix:</b> <xsl:value-of select="totalPrice"/></p>
                        <p><b>Date de commande:</b> <xsl:value-of select="orderDate"/></p>
                        <p><b>Date de livraison:</b> <xsl:value-of select="deliveryDate"/></p>
                        <p><b>Status:</b> <xsl:value-of select="status"/></p>
                    </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
