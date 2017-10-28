<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <ul>
            <xsl:for-each select="//*[local-name()='article']">
                <li>
                    <div class="image_title">
                        <a>
                            <xsl:attribute name="title">
                                <xsl:value-of select="./*[local-name()='title']" />
                            </xsl:attribute>
                            <xsl:attribute name="href">ArticleDetail?articleId=<xsl:value-of select="./*[local-name()='id']" /></xsl:attribute>
                            <xsl:value-of select="./*[local-name()='title']" />
                        </a>
                        <div class="img-content">
                            <xsl:value-of select="./*[local-name()='overview']" />
                            <br/> 
                        </div>
                    </div>
                    <a>
                        <xsl:attribute name="title">
                            <xsl:value-of select="./*[local-name()='title']" />
                        </xsl:attribute>
                        <xsl:attribute name="href">ArticleDetail?articleId=<xsl:value-of select="./*[local-name()='id']" /></xsl:attribute>
                        <img height="320" width="560">
                            <xsl:attribute name="src">
                                <xsl:value-of select="./*[local-name()='thumbnail']" />
                            </xsl:attribute>
                            <xsl:attribute name="alt">
                                <xsl:value-of select="./*[local-name()='title']" />
                            </xsl:attribute>
                        </img>
                    </a>
                </li>
            </xsl:for-each>
        </ul>
    </xsl:template>
</xsl:stylesheet>

