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
                <h1>Commandes</h1>
                <xsl:for-each select="cookbox/orders/order">
                    <div class="order">
                        <h2>Commande <xsl:value-of select="@id"/></h2>
                        <p><b>Client:</b> <xsl:value-of select="customerName"/></p>
                        <p><b>Adresse de livraison:</b> <xsl:value-of select="deliveryAddress"/></p>
                        <p><b>Recettes commandées:</b>
                            <xsl:for-each select="recipeIds/recipeId">
                                <span><xsl:value-of select="."/>, </span>
                            </xsl:for-each>
                        </p>
                        <p><b>Date de commande:</b> <xsl:value-of select="orderDate"/></p>
                        <p><b>Date de livraison:</b> <xsl:value-of select="deliveryDate"/></p>
                        <p><b>Status:</b> <xsl:value-of select="status"/></p>
                    </div>
                </xsl:for-each>
                <h1>Commentaires</h1>
                <xsl:for-each select="cookbox/comments/comment">
                    <div class="comment">
                        <p><b>Auteur:</b> <xsl:value-of select="author"/></p>
                        <p><xsl:value-of select="content"/></p>
                        <p><b>Date:</b> <xsl:value-of select="date"/></p>
                    </div>
                </xsl:for-each>
                <h1>Livreurs</h1>
                <xsl:for-each select="cookbox/deliveryPersons/deliveryPerson">
                    <div class="deliveryPerson">
                        <p><b>Nom:</b> <xsl:value-of select="name"/></p>
                        <p><b>Contact:</b> <xsl:value-of select="contact"/></p>
                        <p><b>Livraisons effectuées:</b> <xsl:value-of select="deliveriesMade"/></p>
                    </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
