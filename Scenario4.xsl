<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>

    <xsl:key name="recipes-by-budget" match="recipe" use="budget"/>

    <xsl:template match="/">
        <recipesByBudget>
            <xsl:for-each select="cookbox/recipes/recipe[count(. | key('recipes-by-budget', budget)[1]) = 1]">
                <xsl:sort select="budget"/>
                <budgetGroup>
                    <budget><xsl:value-of select="budget"/></budget>
                    <recipes>
                        <xsl:for-each select="key('recipes-by-budget', budget)">
                            <recipe>
                                <id><xsl:value-of select="@id"/></id>
                                <title><xsl:value-of select="title"/></title>
                                <description><xsl:value-of select="description"/></description>
                                <cuisine><xsl:value-of select="cuisine"/></cuisine>
                                <health><xsl:value-of select="health"/></health>
                                <ingredients>
                                    <xsl:copy-of select="ingredients/*"/>
                                </ingredients>
                                <steps><xsl:value-of select="steps"/></steps>
                            </recipe>
                        </xsl:for-each>
                    </recipes>
                </budgetGroup>
            </xsl:for-each>
        </recipesByBudget>
    </xsl:template>
</xsl:stylesheet>
