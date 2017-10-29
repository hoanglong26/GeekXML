<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" cdata-section-elements="shortdescription"/>
    <xsl:template match="/">
        <div class="article-title">
            <xsl:value-of select="//*[local-name()='title']" />
        </div>
        <div class="article-author">            
            <b>
                Theo <a style="color: blue; font-weight: 700;">
                    <xsl:attribute name="title">
                        <xsl:value-of select="//*[local-name()='title']" />
                    </xsl:attribute>
                    <xsl:attribute name="href">
                        <xsl:value-of select="//*[local-name()='link']" />
                    </xsl:attribute>
                    <xsl:value-of select="substring-before(substring(//*[local-name()='link'],8),'.vn')" />
                </a> | <xsl:value-of select="//*[local-name()='pubDate']" />
            </b>
        </div>
        <div class="article-body">
            <h3>
                <xsl:value-of select="//*[local-name()='overview']" />
            </h3>
            <xsl:value-of disable-output-escaping="yes" select="//*[local-name()='description']" />
            <div style="text-align:right;">
                <h3>
                    <xsl:value-of select="//*[local-name()='author']" />
                </h3>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>

