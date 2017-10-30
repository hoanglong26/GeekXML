<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <div id="searchList">
            <xsl:for-each select="//*[local-name()='article']">
                <li class="topper topper-search-result" style="width: 90%;">
                    <div class="maintitle mainbody-search-result">
                        <a>                            
                            <xsl:attribute name="title">
                                <xsl:value-of select="./*[local-name()='title']" />
                            </xsl:attribute>
                            <xsl:attribute name="href">ArticleDetail?articleId=<xsl:value-of select="./*[local-name()='id']" /></xsl:attribute>
                            <xsl:value-of select="./*[local-name()='title']" />
                        </a>
                    </div>
                    <div class="maincontent mainbody-search-result">
                        <xsl:value-of select="./*[local-name()='overview']" />
                    </div>
                </li>
            </xsl:for-each>
        </div>
    </xsl:template>
</xsl:stylesheet>

